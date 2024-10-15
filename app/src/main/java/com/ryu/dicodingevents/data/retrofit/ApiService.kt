package com.ryu.dicodingevents.data.retrofit

import com.ryu.dicodingevents.data.response.ListEventsItem
import com.ryu.dicodingevents.data.response.ResponseItem
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getAllEvents(@Url url: String): Response<ResponseItem>

    @GET("events/{id}")
    suspend fun getEventDetail(@Path("id") id: String): Response<ResponseItem>
}