package com.ryu.dicodingevents.ui.home

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
class HomeViewModel
@Inject
constructor (private val repository: EventRepository) : ViewModel() {

    private val _horizontalEvents = MutableLiveData<List<ListEventsItem>>()
    val horizontalEvents: LiveData<List<ListEventsItem>> = _horizontalEvents

    private val _verticalEvents = MutableLiveData<List<ListEventsItem>>()
    val verticalEvents: LiveData<List<ListEventsItem>> = _verticalEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getHorizontalEvents() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.getEvents("events?active=1")
                if (response.isSuccessful) {
                    _horizontalEvents.value = response.body()?.listEvents?.filterNotNull()
                }
            } catch (e: Exception) {
                Log.d("HomeViewModel", "getHorizontalEvents Error: $e")
            }
        }
    }

    fun getVerticalEvents() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.getEvents("events?active=0")
                if (response.isSuccessful) {
                    _verticalEvents.value = response.body()?.listEvents?.filterNotNull()
                }
            } catch (e: Exception) {
                Log.d("HomeViewModel", "getVerticalEvents Error: $e")
            }
        }
    }
}