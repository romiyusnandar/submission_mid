package com.ryu.dicodingevents.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryu.dicodingevents.data.repository.EventRepository
import com.ryu.dicodingevents.data.response.ListEventsItem
import com.ryu.dicodingevents.data.response.ResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: EventRepository) : ViewModel() {

    private val _eventDetail = MutableLiveData<ListEventsItem?>()
    val eventDetail: LiveData<ListEventsItem?> = _eventDetail

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getEvent(eventId: String) {
        viewModelScope.launch {
            try {
                val result = repository.getEventDetail(eventId)
                result.onSuccess { event ->
                    _eventDetail.value = event
                }.onFailure { exception ->
                    _error.value = exception.message ?: "An unknown error occurred"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "An unknown error occurred"
            }
        }
    }
}