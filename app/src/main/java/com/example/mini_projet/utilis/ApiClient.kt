package com.example.mini_projet.utils

import com.example.mini_projet.utilis.ApiInterface
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
     const val BASE_URL: String = "https://iyum.herokuapp.com/"

    private val gson : Gson by lazy {
        GsonBuilder().setLenient().create()
    }


    private val interceptor : HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val httpClient : OkHttpClient by lazy {
        OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val apiservice:  ApiInterface by lazy{
        retrofit.create(ApiInterface::class.java)
    }

    }