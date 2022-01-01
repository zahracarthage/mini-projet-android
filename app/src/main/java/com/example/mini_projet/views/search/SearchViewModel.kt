package com.example.mini_projet.views.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mini_projet.models.Restaurant
import com.example.mini_projet.models.RestaurantList
import java.util.*

class SearchViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    val restaurants: RestaurantList? = savedStateHandle.get<RestaurantList>("restaurant_list")
    val query: String? = savedStateHandle.get<String>("query")

    val restaurantsLive = MutableLiveData<ArrayList<Restaurant>>()

    init {
        println("query ${query} resturants ${restaurants} ")
    }


    fun handleQuery(p0: String?) {
        if (p0 != null) {
            val items: ArrayList<Restaurant>? = restaurants?.list?.filter {
                it.title?.lowercase(locale = Locale.getDefault())
                    ?.contains(p0) == true || it.category?.joinToString()?.lowercase()
                    ?.contains(p0) == true
            } as ArrayList<Restaurant>?
            restaurantsLive.value = items ?: arrayListOf()
        } else {
            restaurantsLive.value = arrayListOf()
        }
    }
}