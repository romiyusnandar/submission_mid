package com.ryu.dicodingevents.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryu.dicodingevents.adapter.VerticalEventAdapter
import com.ryu.dicodingevents.databinding.FragmentUpcomingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingFragment : Fragment() {


    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private lateinit var upcomingAdapter: VerticalEventAdapter
    private val upcomingViewModel: UpcomingViewModel by viewModels()


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

        setupSearchView()
        setupRecyclerView()
        observeViewModel()

    }

    private fun setupSearchView() {
        binding.search.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { upcomingViewModel.searchUpcomingEvents(it) }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { upcomingViewModel.searchUpcomingEvents(it) }
                    return true
                }
            })

            setOnCloseListener {
                upcomingViewModel.resetSearch()
                setQuery("", false)
                clearFocus()
                true
            }
        }
    }

    private fun setupRecyclerView() {
        upcomingAdapter = VerticalEventAdapter()
        binding.rvVertical.apply {
            adapter = upcomingAdapter
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() {
        upcomingViewModel.responseUpcoming.observe(viewLifecycleOwner) { eventList ->
            upcomingAdapter.setData(eventList)
        }

        upcomingViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}