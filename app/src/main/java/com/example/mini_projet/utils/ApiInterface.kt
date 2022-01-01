package com.example.mini_projet.utils

import com.example.mini_projet.models.Restaurant
import retrofit2.Response
import retrofit2.http.GET
import kotlin.collections.ArrayList
import retrofit2.http.Path


interface ApiInterface {
    @GET("/restaurants")
    suspend fun getRestaurants(): Response<ArrayList<Restaurant>>

   @GET("/restaurants/categories")
    suspend fun getCategories(): Response<ArrayList<String>>

    @GET("/restaurants/{id}")
    suspend  fun findRestaurantbyId(@Path("id") id: String?): Response<Restaurant?>?


}