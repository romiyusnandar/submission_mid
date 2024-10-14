package com.ryu.dicodingevents.ui.complete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ryu.dicodingevents.adapter.EventAdapter
import com.ryu.dicodingevents.adapter.GridAdapter
import com.ryu.dicodingevents.databinding.FragmentCompleteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompleteFragment : Fragment() {

    private var _binding: FragmentCompleteBinding? = null
    private val binding get() = _binding!!
    private val completeViewModel: CompleteViewModel by viewModels()
    private lateinit var eventAdapter: GridAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompleteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        completeViewModel.getCompletedEvents()
    }

    private fun setupRecyclerView() {
        eventAdapter = GridAdapter()
        binding.rvComplete.apply {
            layoutManager = GridLayoutManager(context, 2) // 2 columns
            adapter = eventAdapter
        }
    }

    private fun observeViewModel() {
        completeViewModel.completedEvents.observe(viewLifecycleOwner) { events ->
            eventAdapter.setData(events)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}