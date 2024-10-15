package com.ryu.dicodingevents.adapter

import com.ryu.dicodingevents.data.response.ListEventsItem
import com.ryu.dicodingevents.databinding.ItemUpcomingBinding
import com.ryu.dicodingevents.ui.detail.DetailEventActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class UpcomingAdapter : RecyclerView.Adapter<UpcomingAdapter.UpcomingEventBinding>() {

    private val events = mutableListOf<ListEventsItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setEvents(newEvents: List<ListEventsItem>) {
        events.clear()
        events.addAll(newEvents)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingEventBinding {
        val binding = ItemUpcomingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UpcomingEventBinding(binding)
    }

    override fun onBindViewHolder(holder: UpcomingEventBinding, position: Int) {
        val event = events[position]
        holder.bind(event)
    }

    override fun getItemCount() = events.size

    inner class UpcomingEventBinding(private val binding: ItemUpcomingBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val event = events[position]
                    val intent = Intent(itemView.context, DetailEventActivity::class.java).apply {
                        putExtra(DetailEventActivity.EXTRA_EVENT, event)
                    }
                    itemView.context.startActivity(intent)
                }
            }
        }

        fun bind(event: ListEventsItem) {
            Log.d("UpcomingAdapter", "Binding event: ${event.name}")
            binding.tvEventName.text = event.name
            binding.tvEventDate.text = formatDate(event.beginTime.toString(),
                event.endTime.toString()
            )
            binding.ivEventLogo.load(event.mediaCover)
        }

        private fun formatDate(beginTime: String, endTime: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())

            try {
                val beginDate = inputFormat.parse(beginTime)
                val endDate = inputFormat.parse(endTime)

                return if (beginDate != null && endDate != null) {
                    "${outputFormat.format(beginDate)} - ${outputFormat.format(endDate)}"
                } else {
                    "Invalid date"
                }
            } catch (e: Exception) {
                return "Invalid date format"
            }
        }
    }
}