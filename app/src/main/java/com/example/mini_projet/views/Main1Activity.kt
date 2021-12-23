package com.example.mini_projet.views

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_projet.R
import com.example.mini_projet.adapters.categorieadapter
import com.example.mini_projet.adapters.restaurantadapter
import com.example.mini_projet.models.Restaurant
import com.example.mini_projet.utilis.ApiClient
import com.example.mini_projet.utilis.ApiInterface
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.AccessController.getContext


class Main1Activity : AppCompatActivity(){

    var restaurantList: ArrayList<Restaurant> = ArrayList()
    var categoryList: ArrayList<String> = ArrayList()
    private var adapter: restaurantadapter? = null
    private var adapterCategory: categorieadapter? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)
        var recyclerViewCategory: RecyclerView= findViewById(R.id.filters)
        recyclerViewCategory.layoutManager= LinearLayoutManager(this@Main1Activity, LinearLayoutManager.VERTICAL,false)
        adapterCategory= categorieadapter(categoryList,restaurantList,this)
        recyclerViewCategory.adapter=adapterCategory

        var recyclerView: RecyclerView = findViewById(R.id.restaurantslist)

        recyclerView.layoutManager=  LinearLayoutManager(this@Main1Activity, LinearLayoutManager.VERTICAL,false)
        adapter=restaurantadapter(restaurantList,this)

        recyclerView.addItemDecoration(DividerItemDecoration(this@Main1Activity,
            DividerItemDecoration.VERTICAL))


        recyclerView.adapter=adapter

        ApiClient.apiservice.getCategories().enqueue(object: Callback<ArrayList<String>>{
            override fun onResponse(
                call: Call<ArrayList<String>>,
                response: Response<ArrayList<String>>
            )

            {
                val response = response.body()
                Log.d("cat:", response.toString())
                response?.let {
                    adapterCategory?.updateData(it)
                }
            }

            override fun onFailure(call: Call<ArrayList<String>>, t: Throwable) {
                Toast.makeText(getApplicationContext(), "mon message", Toast.LENGTH_SHORT).show();
                t.message?.let { Log.d("exception", it) }
            }
        })


        ApiClient.apiservice.getRestaurants()
            .enqueue(object: Callback<ArrayList<Restaurant>>{
                override fun onResponse(
                    call: Call<ArrayList<Restaurant>>,
                    response: Response<ArrayList<Restaurant>>
                )

                {
                   val response = response.body()
                    response?.let {
                        adapter?.updateData(it)
                        }
                    }

                override fun onFailure(call: Call<ArrayList<Restaurant>>, t: Throwable) {
                Toast.makeText(getApplicationContext(), "mon message", Toast.LENGTH_SHORT).show();
                    t.message?.let { Log.d("exception", it) }
                }
            })



    }



}





