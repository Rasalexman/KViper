package com.mincor.kviper.di.interfaces

import retrofit2.Call
import retrofit2.http.*

/**
 * Created by a.minkin on 25.10.2017.
 *
 *
# Все ответы приходят в json формате и оборачиваются в следующие generic объекты:
# При успешном ответе:
{
    "response": {
        ...
        }
}

# При ошибке
{
    "error": {
        "code": str,
        "message": str
    }
}
 *
 */
interface IWebServerApi {

    /**
     * Получаем список всех доступных тэгов
     * в параметр уходит обновление
     *
     * @param last_update: timestamp
     * Текущий timestamp для получения изменений
     * 0 для получения всех тегод
     */
    @GET("tags")
    @Headers("Content-type: application/json")
    fun getTags(@Query("last_update") lastUpdate:String = "0"):Call<TagResponse>

    /**
     * Получаем список постов на главной странице
     * GET - static
     */
    @GET("getMainJSON")
    @Headers("Content-type: application/json")
    fun getMainPage():Call<ArticleMainResponse>

    /**
     * Получаем список N-постов с подробной информацией с главной страницы
     * GET - static
     */
    @GET("getCachedArticles")
    @Headers("Content-type: application/json")
    fun getCachedArticles():Call<ArticlesCachedResponse>

    /**
     * Получаем полную ленту новостей с учетом пагинации
     *
     * @param nextGuid
     * GUID следующего поста для пагинации, "" - если первая страница
     */
    @GET("articlesAll")
    @Headers("Content-type: application/json")
    fun getAllArticles(@Query("next") nextGuid:String = ""):Call<ArticleFeedResponse>

    /**
     * Получение новости и следующей записи
     *
     * @param guid
     * GUID текущего поста по которому хотим получить полную инфу
     * response: {
     *      post: $ArticleMain,    # post json data
     *      next: str       # next ArticleMain guid
     *   }
     */
    @GET("articleGet")
    @Headers("Content-type: application/json")
    fun getArticleById(@Query("guid") guid:String):Call<ArticleGetResponse>

    /**
     * Поиск новостей с пагинацией
     *
     * @param query
     * Строка по которой ведется поиск
     *
     * @param next
     * GUID следующего для поиска (пагинация)
     */
    @GET("search")
    @Headers("Content-type: application/json")
    fun searchNews(@Query("query") query:String, @Query("next") next:String = ""):Call<SearchResponse>

    /**
     * Поиск новостей с пагинацией
     * @param guid
     * Guid текущего тега по которому будем вести фильтр
     *
     * @param next
     *  Следующий в пагинации
     */
    @GET("articlesFilter")
    @Headers("Content-type: application/json")
    fun filterArticles(@Query("tag_guid") guid:String, @Query("next") next:String = ""):Call<ArticleFilterResponse>


    /**
     * Сохранение текущей подписки на сервере
     */
    @FormUrlEncoded
    @POST("newSubscription")
    fun saveNewSubscription(@Field("receipt") receipt:String):Call<SubscriptionResponse>

    /**
     * Проверка текущей подписки на сервере
     */
    @GET("checkSubscription")
    @Headers("Content-type: application/json")
    fun checkSubscription():Call<SubscriptionCheckResponse>

    /**
     * Получаем новости в перебивке на главной
     */
    @GET("getLastNews")
    @Headers("Content-type: application/json")
    fun getLastNews():Call<ArticleNewsOfDayResponse>

    /**
     * Получаем новости в перебивке на главной
     */
    @GET("redaction")
    @Headers("Content-type: application/json")
    fun getRedaction():Call<RedactionResponse>


    /***
     *https://mtest.dp.ru/counter/MobileApp/addview/ab888bc6-3d50-44fc-a192-ec41e850fe4b
     */
    @POST("https://m.dp.ru/counter/MobileApp/addview/{guid}")
    @Headers("Content-type: application/json")
    fun getViewsByGuid(@Path("guid") guid:String):Call<ViewCountResponse>



}