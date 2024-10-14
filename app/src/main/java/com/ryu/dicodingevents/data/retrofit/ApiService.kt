package com.ryu.dicodingevents.data.retrofit

import com.ryu.dicodingevents.data.response.ResponseItem
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun getAllEvents(@Url url: String): Response<ResponseItem>
}