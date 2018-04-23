package com.mincor.kviper.di.modules

import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.mincor.kviper.BuildConfig
import com.mincor.kviper.consts.Consts
import com.mincor.kviper.consts.Consts.API_KEY
import com.mincor.kviper.di.interfaces.IWeatherApi
import com.mincor.kviper.utils.log
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.kodein.di.generic.with
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


val netModule = Kodein.Module {
    constant(Consts.SERVER_URL) with "http://api.openweathermap.org/data/2.5/"
    bind<OkHttpClient>() with singleton { createOkHttpClient(instance("cache")) }
    bind<IWeatherApi>() with singleton { createWebServiceApi<IWeatherApi>(instance(), instance(Consts.SERVER_URL)) }
}

fun createOkHttpClient(cachedDir: File): OkHttpClient {
    val httpClient = OkHttpClient.Builder()

    try {
        httpClient.cache(Cache(cachedDir, 10L * 1024L * 1024L)) // 10 MB Cache
    } catch (e: Exception) {
        log{"createOkHttpClient Couldn't create http cache because of IO problem."}
    }

    val logging = LoggingInterceptor.Builder()
            .loggable(BuildConfig.DEBUG)
            .setLevel(Level.BODY)
            .log(Platform.INFO)
            .request("Request")
            .response("Response")
            .addHeader("version", BuildConfig.VERSION_NAME).build()

    // add logging as last interceptor
    httpClient.addInterceptor(logging)  // <-- this is the important line!

    httpClient.addInterceptor { chain ->
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
                .addQueryParameter("units", "metric")
                .addQueryParameter("lang", "ru")
                .addQueryParameter("appid", API_KEY)
                .build()

        val request = original.newBuilder().url(url).build()

        chain.proceed(request)
    }

    httpClient.addNetworkInterceptor { chain ->
        val originalResponse = chain.proceed(chain.request())

        val cacheControl = originalResponse.header("Cache-Control")

        return@addNetworkInterceptor if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
            originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .addHeader("Cache-Control", "public, max-age=60")
                    .build()
        } else {
            originalResponse
        }
    }

    return httpClient.build()
}

inline fun <reified F> createWebServiceApi(okHttpClient: OkHttpClient, url: String): F {
    val gsonInstance = GsonBuilder()
            //.registerTypeAdapter(Content::class.java, ContentDeserializer())
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

/*
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
}*/