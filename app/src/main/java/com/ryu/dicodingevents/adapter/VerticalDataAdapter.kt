package com.ryu.dicodingevents.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ryu.dicodingevents.data.response.ListEventsItem
import com.ryu.dicodingevents.databinding.ItemEventVerticalBinding

class VerticalEventAdapter(private val onItemClick: (ListEventsItem) -> Unit) :
    RecyclerView.Adapter<VerticalEventAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemEventVerticalBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<ListEventsItem>() {
        override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallBack)

    var eventList: List<ListEventsItem>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemEventVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = eventList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentEvent = eventList[position]

        holder.binding.apply {
            tvEventTitle.text = currentEvent.name
//            tvEventDescription.text = HtmlCompat.fromHtml(
//                currentEvent.description.toString(),
//                HtmlCompat.FROM_HTML_MODE_LEGACY
//            )
            tvEventDescription.text = currentEvent.summary
            tvEventDate.text = currentEvent.endTime
            ivEventImage.load(currentEvent.mediaCover) {
                crossfade(true)
                crossfade(1000)
            }
            root.setOnClickListener {
                onItemClick(currentEvent)
            }
        }
    }

    fun setData(newList: List<ListEventsItem>) {
        eventList = newList
    }

    fun filter(query: String) {
        val filteredList = if (query.isEmpty()) {
            eventList
        } else {
            eventList.filter {
                it.name?.contains(query, ignoreCase = true) == true ||
                        it.summary?.contains(query, ignoreCase = true) == true
            }
        }
        differ.submitList(filteredList)
    }
}