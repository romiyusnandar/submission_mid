package com.ryu.dicodingevents.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ryu.dicodingevents.data.response.ListEventsItem
import com.ryu.dicodingevents.databinding.ItemGridBinding

class GridAdapter : RecyclerView.Adapter<GridAdapter.GridViewHolder>() {

    private var eventList: List<ListEventsItem> = listOf()

    class GridViewHolder(private val binding: ItemGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: ListEventsItem) {
            binding.apply {
                tvEventTitle.text = event.name
                ivEventImage.load(event.mediaCover) {
                    crossfade(true)
                    crossfade(1000)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val binding = ItemGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.bind(eventList[position])
    }

    override fun getItemCount(): Int = eventList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<ListEventsItem>) {
        eventList = newList
        notifyDataSetChanged()
    }
}