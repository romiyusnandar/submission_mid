package com.ryu.dicodingevents.ui.upcoming

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryu.dicodingevents.data.repository.EventRepository
import com.ryu.dicodingevents.data.response.ListEventsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingViewModel
@Inject
constructor(private val repository: EventRepository) : ViewModel() {

    private val _responseUpcoming = MutableLiveData<List<ListEventsItem>>()
    val responseUpcoming: LiveData<List<ListEventsItem>> = _responseUpcoming

    private var originalEvents: List<ListEventsItem> = listOf()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getUpcomingEvents()
    }

    private fun getUpcomingEvents() {
        viewModelScope.launch {
            try {
                val response = repository.getEvents("events?active=1")
                if (response.isSuccessful) {
                    originalEvents = response.body()?.listEvents?.filterNotNull() ?: listOf()
                    _responseUpcoming.value = originalEvents
                } else {
                    _error.value = "Failed to fetch events"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
                Log.e("UpcomingViewModel", "getUpcomingEvents Error: $e")
            }
        }
    }

    fun searchUpcomingEvents(query: String) {
        if (query.isEmpty()) {
            _responseUpcoming.value = originalEvents
        } else {
            _responseUpcoming.value = originalEvents.filter { event ->
                event.name?.contains(query, ignoreCase = true) == true
            }
        }
    }

    fun resetSearch() {
        _responseUpcoming.value = originalEvents
    }
}