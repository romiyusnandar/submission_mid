package com.ryu.dicodingevents.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.ryu.dicodingevents.data.response.ListEventsItem
import com.ryu.dicodingevents.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eventId = arguments?.getString("eventId")
        if (eventId != null) {
            detailViewModel.getEvent(eventId)
        }

        // Menampilkan progress bar saat data sedang dimuat
        binding.progressBar.visibility = View.VISIBLE
        observeViewModel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeViewModel() {
        detailViewModel.eventDetail.observe(viewLifecycleOwner) { event ->
            // Jika event tidak null, perbarui UI
            if (event != null) {
                // Tampilkan progress bar saat memproses dan memperbarui UI
                updateUI(event)
                binding.progressBar.visibility = View.GONE // Sembunyikan progress bar setelah UI diperbarui
            } else {
                // Jika event null, tetap tampilkan progress bar
                binding.progressBar.visibility = View.VISIBLE
            }
        }

        detailViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
            }
        }

        detailViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateUI(event: ListEventsItem) {
        binding.apply {
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
            tvEventTime.text = "Waktu: ${beginTime?.format(DateTimeFormatter.ofPattern("HH:mm"))} - ${endTime?.format(DateTimeFormatter.ofPattern("HH:mm"))}"

            tvEventQuota.text = "Quota: ${event.registrants}/${event.quota}"

            val sisaQuota = (event.quota ?: 0) - (event.registrants ?: 0)
            tvSisaQuota.text = "Sisa Quota: $sisaQuota"

            btnOpenLink.setOnClickListener {
                event.link?.let { url ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                } ?: run {
                    Toast.makeText(requireContext(), "Event link is not available", Toast.LENGTH_SHORT).show()
                }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
