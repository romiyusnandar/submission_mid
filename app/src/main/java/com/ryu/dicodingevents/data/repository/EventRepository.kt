package com.ryu.dicodingevents.data.repository

import com.ryu.dicodingevents.data.retrofit.ApiService
import javax.inject.Inject

class EventRepository
@Inject

constructor(private val apiService: ApiService) {

    suspend fun getAllEvents() = apiService.getAllEvents()
}