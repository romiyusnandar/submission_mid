package com.ryu.dicodingevents.data.retrofit

import com.ryu.dicodingevents.data.response.ResponseItem
import retrofit2.http.GET
import retrofit2.Response

interface ApiService {

    @GET(ApiClient.END_POINT)
    suspend fun getAllEvents(): Response<ResponseItem>
}