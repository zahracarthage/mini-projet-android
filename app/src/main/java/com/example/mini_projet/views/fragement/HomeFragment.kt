package com.example.mini_projet.views.fragement

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_projet.R
import com.example.mini_projet.adapters.RestaurantAdapter
import com.example.mini_projet.databinding.FragmentHomeBinding
import com.example.mini_projet.models.Menus
import com.example.mini_projet.models.RestaurantList
import com.example.mini_projet.models.Restaurant
import com.example.mini_projet.views.adapter.CategoriesAdapter
import com.example.mini_projet.views.detailresto
import com.example.mini_projet.views.home.HomeViewModel
import com.example.mini_projet.views.search.SearchFragment

class HomeFragment : Fragment(), CategoriesAdapter.CustomListeners,
    RestaurantAdapter.CustomListeners {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var searchView: SearchView
    private lateinit var filterRecyclerView: RecyclerView
    private lateinit var restaurantRecyclerView: RecyclerView

    private lateinit var restaurantAdapter: RestaurantAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        searchView = binding.search

        filterRecyclerView = binding.filters
        restaurantRecyclerView = binding.restaurantslist



        filterRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL, false)

        restaurantRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        categoriesAdapter = CategoriesAdapter(this)
        restaurantAdapter = RestaurantAdapter(this, requireContext())



        filterRecyclerView.adapter = categoriesAdapter
        restaurantRecyclerView.adapter = restaurantAdapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {

                if (p0 != null && viewModel.resturantsStable != null) {

                  /* requireView().findNavController().navigate(
                        HomeFragmentDirections.actionMenuHomeToSearchFragment(
                            RestaurantList(viewModel.resturantsStable!!),
                            p0
                        )
                    )*/
                    //setCurrentFragment(HomeFragment)

                    moveToSearch(SearchFragment(),RestaurantList(viewModel.resturantsStable!!),p0)
                }

                return false
            }


            override fun onQueryTextChange(p0: String?): Boolean {
                println("query change ")
                searchView.queryHint = null
                return false
            }
        })
        return binding.root

    }

    override fun onStart() {
        super.onStart()
        viewModel.getCategories()
        viewModel.getResturants()
        viewModel.resturants.observe(viewLifecycleOwner, {

            restaurantAdapter.submitList(it ?: listOf())
        })
        viewModel.categories.observe(viewLifecycleOwner, {
            println("categories ${it}")
            categoriesAdapter.submitList(it ?: listOf())
        })
    }

    override fun onItemSelected(item: String) {
        viewModel.handleSelection(item)
    }

    override fun onItemSelected(restaurant: Restaurant) {
        val intent = Intent(activity, detailresto::class.java)
        intent.putExtra("id",restaurant.id)
        intent.putExtra("menu",Menus(restaurant.ListMenu))
        intent.putExtra("name",restaurant.name)
        intent.putExtra("picture",restaurant.picture)
        intent.putExtra("about",restaurant.about)
        intent.putExtra("longitude",restaurant.RestaurantLocalisation[0].Longititude)
        intent.putExtra("latitude",restaurant.RestaurantLocalisation[0].Latitude)
        intent.putExtra("zoomIndex",restaurant.RestaurantLocalisation[0].zoomIndex)
        requireActivity().startActivity(intent)
        Log.e("aa","aa")
    }

    private  fun moveToSearch (fragment: Fragment,resturantList: RestaurantList,query:String)
    {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            val a = Bundle().apply {
                putParcelable("restaurant_list",resturantList)
                putString("query",query)
            }
            fragment.arguments = a
            replace(R.id.container,fragment)
            addToBackStack("")
            commit()
        }

    }

    private fun setCurrentFragment(fragment: Fragment)=
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.container,fragment)
            addToBackStack("")
            commit()
        }

}