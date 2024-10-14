package com.ryu.dicodingevents.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ryu.dicodingevents.data.response.ListEventsItem
import com.ryu.dicodingevents.databinding.ItemEventBinding

class EventAdapter : RecyclerView.Adapter<EventAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ItemEventBinding) :
    RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<ListEventsItem>() {
        override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
            return newItem.id == oldItem.id
        }
    }

    private val differ = AsyncListDiffer(this, differCallBack)
    var eventList: List<ListEventsItem>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = eventList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentEvent = eventList[position]

        holder.binding.apply {
            tvEventTitle.text = currentEvent.name
            tvEventDesc.text = currentEvent.description
            ivEventImage.load(currentEvent.mediaCover) {
                crossfade(true)
                crossfade(1000)
            }
        }
    }

}