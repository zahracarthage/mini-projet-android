package com.example.mini_projet.utilis

import com.example.mini_projet.models.Restaurant
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {
    @GET("/restaurants")
    fun getRestaurants(): Call<ArrayList<Restaurant>>

}