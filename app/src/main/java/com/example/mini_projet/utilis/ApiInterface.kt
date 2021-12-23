package com.example.mini_projet.utilis

import com.example.mini_projet.models.Restaurant
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.*
import kotlin.collections.ArrayList
import com.example.mini_projet.models.User
import retrofit2.http.Body
import retrofit2.http.Path


interface ApiInterface {
    @GET("/restaurants")
    fun getRestaurants(): Call<ArrayList<Restaurant>>

   @GET("/restaurants/categories")
    fun getCategories(): Call<ArrayList<String>>

    @GET("/restaurants/{id}")
    fun findRestaurantbyId(@Path("id") id: String,): Call<Restaurant?>?


}