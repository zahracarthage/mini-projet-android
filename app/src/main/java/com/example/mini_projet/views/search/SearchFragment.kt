package com.example.mini_projet.views.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_projet.adapters.RestaurantAdapter
import com.example.mini_projet.databinding.SearchLayoutBinding
import com.example.mini_projet.models.Restaurant

class SearchFragment() : Fragment(), RestaurantAdapter.CustomListeners {


    private lateinit var binding: SearchLayoutBinding
    private lateinit var searchView: SearchView
    private lateinit var searchRecycler: RecyclerView
    private lateinit var searchNotFound: TextView
    private lateinit var adapter: RestaurantAdapter
    private lateinit var args: SearchFragmentArgs

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = SearchFragmentArgs.fromBundle(requireArguments())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchLayoutBinding.inflate(inflater, container, false)

        searchView = binding.search
        searchNotFound = binding.noResults
        searchRecycler = binding.searchResult


        searchRecycler.layoutManager = LinearLayoutManager(requireContext())

        adapter = RestaurantAdapter(this, requireContext())
        searchRecycler.adapter = adapter

        println("search viewModel query ${searchViewModel.query}")

        searchViewModel.handleQuery(searchViewModel.query)

        searchNotFound.isVisible = false

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {

                searchViewModel.handleQuery(p0)
                return false
            }


            override fun onQueryTextChange(p0: String?): Boolean {
                searchViewModel.handleQuery(p0)
                searchView.queryHint = null
                return false
            }
        })



        return binding.root
    }

    override fun onStart() {
        super.onStart()
        searchViewModel.restaurantsLive.observe(viewLifecycleOwner, {
            searchNotFound.isVisible = it.isNullOrEmpty()
            adapter.submitList(it ?: listOf())
        })

    }

    override fun onItemSelected(restaurant: Restaurant) {
        requireView().findNavController()
            .navigate(SearchFragmentDirections.actionSearchFragmentToShowOneFragment(restaurant))
    }
}