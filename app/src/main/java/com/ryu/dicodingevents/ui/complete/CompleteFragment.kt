package com.ryu.dicodingevents.ui.complete

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryu.dicodingevents.R
import com.ryu.dicodingevents.adapter.EventAdapter
import com.ryu.dicodingevents.adapter.GridAdapter
import com.ryu.dicodingevents.adapter.UpcomingAdapter
import com.ryu.dicodingevents.adapter.VerticalEventAdapter
import com.ryu.dicodingevents.databinding.FragmentCompleteBinding
import com.ryu.dicodingevents.databinding.FragmentUpcomingBinding
import com.ryu.dicodingevents.ui.upcoming.CompleteViewModel
import com.ryu.dicodingevents.ui.upcoming.UpcomingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompleteFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private lateinit var upcomingAdapter: UpcomingAdapter
    private lateinit var viewModel: CompleteViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("UpcomingFragment", "onViewCreated: Fragment is created")

        viewModel = ViewModelProvider(this)[CompleteViewModel::class.java]
        upcomingAdapter = UpcomingAdapter()

        binding.rvActiveEvents.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvActiveEvents.adapter = upcomingAdapter

        viewModel.events.observe(viewLifecycleOwner) { events ->
            Log.d("UpcomingFragment", "Observed events: $events")
            if (events != null && events.isNotEmpty()) {
                upcomingAdapter.setEvents(events)
            } else {
                Log.w("UpcomingFragment", "No events available")
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error.isNotEmpty()) {
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                Log.e("UpcomingFragment", "Error: $error")
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}