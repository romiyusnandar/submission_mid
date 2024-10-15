package com.ryu.dicodingevents.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.text.HtmlCompat
import coil.load
import com.bumptech.glide.Glide
import com.ryu.dicodingevents.R
import com.ryu.dicodingevents.data.response.ListEventsItem
import com.ryu.dicodingevents.databinding.ActivityDetailEventBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone

class DetailEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEventBinding

    companion object {
        const val EXTRA_EVENT = "extra_event"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val event = intent.getParcelableExtra<ListEventsItem>(EXTRA_EVENT)
        event?.let { displayEventDetails(it) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("StringFormatInvalid")
    private fun displayEventDetails(event: ListEventsItem) {
        with(binding) {
            tvEventTitle.text = event.name
            tvEventDescription.text = HtmlCompat.fromHtml(event.description ?: "", HtmlCompat.FROM_HTML_MODE_COMPACT)
            ivEventImage.load(event.mediaCover) {
                crossfade(true)
                crossfade(1000)
            }

            tvEventCategory.text = event.category
            tvEventOwner.text = "Organized by: ${event.ownerName}"
            tvEventLocation.text = "Lokasi: ${event.cityName}"

            val beginTime = event.beginTime?.let { parseDateTime(it) }
            val endTime = event.endTime?.let { parseDateTime(it) }

            tvEventDate.text = "Tanggal: ${beginTime?.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))}"
            tvEventTime.text = "Waktu: ${beginTime?.format(DateTimeFormatter.ofPattern("HH:mm"))} - ${endTime?.format(
                DateTimeFormatter.ofPattern("HH:mm"))}"

            tvEventQuota.text = "Quota: ${event.registrants}/${event.quota}"

            val sisaQuota = (event.quota ?: 0) - (event.registrants ?: 0)
            tvSisaQuota.text = "Sisa Quota: $sisaQuota"

            btnOpenLink.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
                    startActivity(intent)

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun parseDateTime(dateTimeString: String): LocalDateTime? {
        return try {
            LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        } catch (e: Exception) {
            null
        }
    }

}