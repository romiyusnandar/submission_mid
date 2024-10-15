package com.ryu.dicodingevents.data.repository

import com.ryu.dicodingevents.data.response.ListEventsItem
import com.ryu.dicodingevents.data.retrofit.ApiService
import javax.inject.Inject


class EventRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getEvents(endpoint: String) = apiService.getAllEvents(endpoint)

    suspend fun getEventDetail(id: String): Result<ListEventsItem> {
        return try {
            val response = apiService.getEventDetail(id)
            if (response.isSuccessful) {
                val event = response.body()?.event
                if (event != null) {
                    Result.success(event)
                } else {
                    Result.failure(Exception("Event not found"))
                }
            } else {
                Result.failure(Exception("Failed to fetch event detail"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}