package com.mincor.kviper.di.modules

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.provider.Settings
import com.google.gson.*
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import org.kodein.di.Kodein
import org.kodein.di.generic.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.fortgroup.dpru.BuildConfig
import ru.fortgroup.dpru.common.errors.ELogger
import ru.fortgroup.dpru.consts.Consts
import ru.fortgroup.dpru.consts.Consts.NETWORK_AVAILABLE_AGE
import ru.fortgroup.dpru.consts.Consts.NETWORK_DP_MOBILE_URL
import ru.fortgroup.dpru.consts.Consts.NETWORK_UNAVAILABLE_STALE
import ru.fortgroup.dpru.consts.Consts.TAG_DEVICE_ID
import ru.fortgroup.dpru.consts.Consts.TAG_SERVER_URL
import ru.fortgroup.dpru.database.DpDatabase
import com.mincor.kviper.di.interfaces.IWebServerApi
import ru.fortgroup.dpru.models.common.DpPreferense
import ru.fortgroup.dpru.models.database.content.Content
import ru.fortgroup.dpru.utils.InternetBroadcastReceiver
import java.io.File
import java.lang.reflect.Type


/**
 * Created by a.minkin on 25.10.2017.
 */

object NetModule {
    fun isNetworkAvailable(cm: ConnectivityManager?): Boolean {
        cm?.let {
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            return (activeNetwork?.let {
                it.isConnected && it.isConnectedOrConnecting && it.isAvailable
            } ?: false)
        }
        return false
    }
}

val netModule = Kodein.Module {
    constant(TAG_SERVER_URL) with "https://app.dp.ru/api/v1.0/"
    bind<String>(TAG_DEVICE_ID) with provider { takeDeviceID(instance()) }
    bind<InternetBroadcastReceiver>() with singleton { InternetBroadcastReceiver(instance()) }
    bind<OkHttpClient>() with singleton { createOkHttpClient(instance(), instance("cache"), instance(TAG_DEVICE_ID)) }
    bind<IWebServerApi>() with singleton { createWebServiceApi<IWebServerApi>(instance(), instance(TAG_SERVER_URL)) }
}

@SuppressLint("HardwareIds")
fun takeDeviceID(contentResolver: ContentResolver): String = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

fun createOkHttpClient(cm: ConnectivityManager?, cachedDir: File, deviceId: String): OkHttpClient {
    val httpClient = OkHttpClient.Builder()

    try {
        httpClient.cache(Cache(cachedDir, 10L * 1024L * 1024L)) // 10 MB Cache
    } catch (e: Exception) {
        ELogger.logErrorEvent("createOkHttpClient Couldn't create http cache because of IO problem.", e)
    }

    val logging = LoggingInterceptor.Builder()
            .loggable(BuildConfig.DEBUG)
            .setLevel(Level.HEADERS)
            .log(Platform.INFO)
            .request("Request")
            .response("Response")
            .addHeader("version", BuildConfig.VERSION_NAME).build()

    // add logging as last interceptor
    httpClient.addInterceptor(logging)  // <-- this is the important line!

    httpClient.addInterceptor { chain ->
        var request = chain.request()
        val url = request.url().toString()
        val etag = DpDatabase.etagsMap[url]?.etag ?: ""
        request = if (NetModule.isNetworkAvailable(cm)) {
            // if there is connectivity, we tell the request it can reuse the data for sixty seconds.
            request.newBuilder()
                    .removeHeader("Pragma")
                    .addHeader("Cache-Control", "public, max-age=$NETWORK_AVAILABLE_AGE")
                    .addHeader("Device-Id", deviceId)               //
                    .addHeader("Platform", "Android")         //
                    .addHeader("Connection", "close")
                    .addHeader("If-None-Match", etag)
                    .build()

        } else {
            // If there’s no connectivity, we ask to be given only (only-if-cached) ‘stale’ data upto 7 days ago
            request.newBuilder().removeHeader("Pragma").addHeader("Cache-Control", "public, only-if-cached, max-stale=$NETWORK_UNAVAILABLE_STALE").build()
        }
        chain.proceed(request)
    }

    httpClient.addNetworkInterceptor { chain ->
        val originalResponse = chain.proceed(chain.request())
        // если получили неизмененный ответ прост овыходим и не запариваемся
        if(originalResponse.code() == Consts.NETWORK_304_RESPONSE_CODE) return@addNetworkInterceptor originalResponse

        val cacheControl = originalResponse.header("Cache-Control")

        // берем урл без параметров
        val htppURL = chain.request().url()
        // только если мы не справшиваем данные на прямую с сайта ДП
        if (htppURL.host() != NETWORK_DP_MOBILE_URL) {
            // Здесь идет проверка на сохранение ЕТАГА
            val methodName = htppURL.pathSegments().last()
            // только для определенных запросов из списка @NETWORKS_METHODS_NAMES
            if(!Consts.NETWORKS_METHODS_NAMES.contains(methodName)) {
                // URL String
                val url = htppURL.toString()
                // получаем етаг из БД
                val etag = DpDatabase.etagsMap[url]
                // нужно ли сохранять статью
                val isLocalSave: Boolean = (methodName != Consts.NETWORK_ARTICLE_GET_NAME && DpPreferense.isOffline)
                // берем етаг
                val etagStr = originalResponse.header("etag")?.replace("\"", "")?.replace("W/", "")
                        ?: ""
                // если он есть сравниваем текущий ЕТАГ с уже имеющимся и если он изменился то меняем его
                etag?.let {
                    if (it.etag != etagStr) {
                        it.etag = etagStr
                        if (isLocalSave) it.update()
                    }
                    // если его нет то сохраняем новый етаг
                } ?: DpDatabase.saveEtag(url, etagStr, isLocalSave)
            }
        }

        return@addNetworkInterceptor if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
            originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .addHeader("Cache-Control", "public, max-age=$NETWORK_AVAILABLE_AGE")
                    .addHeader("Device-Id", deviceId)
                    .addHeader("Platform", "Android")
                    .addHeader("Connection", "close")
                    .build()
        } else {
            originalResponse
        }
    }

    return httpClient.build()
}

inline fun <reified F> createWebServiceApi(okHttpClient: OkHttpClient, url: String): F {
    val gsonInstance = GsonBuilder()
            .registerTypeAdapter(Content::class.java, ContentDeserializer())
            .create()

    /*val defVal = Content()
    val moshi = Moshi.Builder()
            .add(ContentAdapter.newFactory(Content::class.java, defVal))
            .build()*/

   // moshi.adapter<Content>(Types.newParameterizedType(Content::class.java, Content::class.java))

    val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            //.addConverterFactory(MoshiConverterFactory.create(moshi))
            .addConverterFactory(GsonConverterFactory.create(gsonInstance))
            .build()
    return retrofit.create(F::class.java)
}

class ContentDeserializer : JsonDeserializer<Content> {
    private val gson = Gson()
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): Content {
        val contentData = gson.fromJson<Content>(json, Content::class.java)
        when {
            contentData.content is String -> contentData.contentStr = contentData.content as String
            contentData.content is ArrayList<*> -> {
                val jsonContent = json.asJsonObject["content"]
                contentData.contentStr = gson.toJson(jsonContent).toString()
            }
            else -> {
                val jsonContent = json.asJsonObject["content"]
                contentData.contentStr = if(jsonContent.toString() == "null") "" else jsonContent.asJsonObject.toString()
            }
        }
        if (contentData.contentStr.contains("\r")) {
            contentData.contentStr = contentData.contentStr.replace("\r", "")
        }
        if (contentData.contentStr.contains("\n")) {
            contentData.contentStr = contentData.contentStr.replace("\n", "")
        }
        if (contentData.contentStr.contains("<span style=\"color:")) {
            contentData.contentStr = contentData.contentStr.replace("span style=\"color:", "font color='").replace(";\"","'").replace("</span>", "</font>")
        }
        if (contentData.contentStr.contains("<span style=\"font-weight:bold;\">")) {
            contentData.contentStr = contentData.contentStr.replace("<span style=\"font-weight:bold;\">", "<b>").replace("</span>", "</b>")
        }
        if (contentData.contentStr.contains("<span style=\"font-style:italic;\">")) {
            contentData.contentStr = contentData.contentStr.replace("<span style=\"font-style:italic;\">", "<i>").replace("</span>", "</i>")
        }

        return contentData
    }
}