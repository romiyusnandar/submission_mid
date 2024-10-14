package com.ryu.dicodingevents.data.response

data class ApiResponse(
	val error: Boolean,
	val message: String,
	val listEvents: Event
)

data class Event(
	val id: Int,
	val name: String,
	val summary: String,
	val description: String,
	val imageLogo: String,
	val mediaCover: String,
	val category: String,
	val ownerName: String,
	val cityName: String,
	val quota: Int,
	val registrants: Int,
	val beginTime: String,
	val endTime: String,
	val link: String
)
