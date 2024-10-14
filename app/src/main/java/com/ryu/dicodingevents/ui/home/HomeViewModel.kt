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

    private val _response = MutableLiveData<List<ListEventsItem>>()
    val responseEvent: LiveData<List<ListEventsItem>>
        get() = _response

    init {
        getAllEvents()
    }

    private fun getAllEvents() = viewModelScope.launch {
        repository.getAllEvents().let {response ->

            if (response.isSuccessful) {
                val events = response.body()?.listEvents?.filterNotNull()
                _response.postValue(events ?: emptyList())
            } else {
                Log.d("tag", "getAllEvents Error: ${response.code()}")
            }
        }
    }
}