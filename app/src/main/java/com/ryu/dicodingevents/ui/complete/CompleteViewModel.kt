package com.ryu.dicodingevents.ui.complete

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
class CompleteViewModel @Inject constructor(private val repository: EventRepository) : ViewModel() {

    private val _completedEvents = MutableLiveData<List<ListEventsItem>>()
    val completedEvents: LiveData<List<ListEventsItem>> = _completedEvents

    fun getCompletedEvents() {
        viewModelScope.launch {
            try {
                val response = repository.getEvents("events?active=0")
                if (response.isSuccessful) {
                    _completedEvents.value = response.body()?.listEvents?.filterNotNull()
                }
            } catch (e: Exception) {
                Log.d("CompleteViewModel", "getCompletedEvents Error: $e")
            }
        }
    }
}