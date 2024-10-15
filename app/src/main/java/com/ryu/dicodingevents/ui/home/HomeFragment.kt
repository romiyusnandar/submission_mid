package com.ryu.dicodingevents.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryu.dicodingevents.R
import com.ryu.dicodingevents.adapter.EventAdapter
import com.ryu.dicodingevents.adapter.VerticalEventAdapter
import com.ryu.dicodingevents.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var horizontalEventAdapter: EventAdapter
    private lateinit var verticalEventAdapter: VerticalEventAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        horizontalEventAdapter = EventAdapter { event ->
            Navigation.findNavController(requireView())
                .navigate(R.id.detailFragment, Bundle().apply {
                    putString("eventId", event.id.toString())
                })
            Log.d("HomeFragment", "Navigating to DetailFragment with eventId: ${event.id}")
        }

        verticalEventAdapter = VerticalEventAdapter()

        binding.rvHorizontal.apply {
            adapter = horizontalEventAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        binding.rvVertical.apply {
            adapter = verticalEventAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() {
        homeViewModel.getHorizontalEvents()
        homeViewModel.horizontalEvents.observe(viewLifecycleOwner) { eventList ->
            if (eventList != null) {
                horizontalEventAdapter.eventList = eventList
                binding.progressBar.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.VISIBLE
            }
        }

        homeViewModel.getVerticalEvents()
        homeViewModel.verticalEvents.observe(viewLifecycleOwner) { eventList ->
            if (eventList != null) {
                verticalEventAdapter.eventList = eventList
                binding.progressBar.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
