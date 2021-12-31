package com.example.mini_projet.utils

import com.example.mini_projet.models.Restaurant
import retrofit2.Call
import retrofit2.http.GET
import kotlin.collections.ArrayList
import retrofit2.http.Path


interface ApiInterface {
    @GET("/restaurants")
    fun getRestaurants(): Call<ArrayList<Restaurant>>

   @GET("/restaurants/categories")
    fun getCategories(): Call<ArrayList<String>>

    @GET("/restaurants/{id}")
    fun findRestaurantbyId(@Path("id") id: String?): Call<Restaurant?>?


}