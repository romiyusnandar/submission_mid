package com.ryu.dicodingevents.data.retrofit

import com.ryu.dicodingevents.data.response.ListEventsItem
import com.ryu.dicodingevents.data.response.ResponseItem
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getAllEvents(@Url url: String): Response<ResponseItem>

    // Contoh api endpoint untuk mendapatkan event
    @GET("events")
    suspend fun getEvents(@Query("active") active: Int): ResponseItem


//    @GET("events")
//    suspend fun searchEvents(@Query("active") active: Int, @Query("q") query: String): EventResponse

//    Note: Gunakan getEvents untuk mendapatkan event atau dipisah satu persatu

    @GET("events")
    suspend fun getActiveEvents(@Query("active") active: Int = 1, @Query("limit") limit: Int = 20): ResponseItem

    // Testing only
    @GET("events")
    suspend fun getPastEvents(@Query("active") active: Int = 0, @Query("limit") limit: Int = 20): ResponseItem

    @GET("events/{id}")
    suspend fun getEventDetail(@Path("id") id: String): Response<ResponseItem>
}