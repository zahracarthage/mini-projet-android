package com.example.mini_projet.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    const val TEST_URL:String = "http://192.168.0.30:3000/"
     const val BASE_URL: String = "http://10.0.2.2:3000/"

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
