package com.example.mini_projet.utils

import com.example.mini_projet.models.Restaurant
import kotlinx.coroutines.flow.Flow

class ApiActions() {


    fun getAllRestaurants(): Flow<AppResult<ArrayList<Restaurant>>> {
        return apiCall {
            ApiClient.apiservice.getRestaurants()
        }
    }

    fun getCategories(): Flow<AppResult<ArrayList<String>>> {
        return apiCall {
            ApiClient.apiservice.getCategories()
        }
    }
}