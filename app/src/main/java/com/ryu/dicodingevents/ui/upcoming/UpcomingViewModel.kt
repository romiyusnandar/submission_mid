package com.ryu.dicodingevents.ui.upcoming

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryu.dicodingevents.data.response.ListEventsItem
import com.ryu.dicodingevents.data.retrofit.ApiConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingViewModel @Inject
constructor(): ViewModel() {


    private val _events = MutableLiveData<List<ListEventsItem>>()
    val events: LiveData<List<ListEventsItem>> = _events

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        getActiveEvents()
    }

    @SuppressLint("NullSafeMutableLiveData")
    private fun getActiveEvents() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = ApiConfig.apiService.getEvents(1) // 1 for active events
                if (!response.error!!) {
                    _events.value = response.listEvents as List<ListEventsItem>?
                    Log.d("UpcomingViewModel", "Events fetched successfully: ${response.listEvents}")
                } else {
                    _error.value = response.message
                    Log.e("UpcomingViewModel", "Error fetching events: ${response.message}")
                }
            } catch (e: Exception) {
                _error.value = "Tidak dapat terhubungan dengan server api, mendapatkan error berikut: ${e.message}"
                Log.e("UpcomingViewModel", "Exception: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

}