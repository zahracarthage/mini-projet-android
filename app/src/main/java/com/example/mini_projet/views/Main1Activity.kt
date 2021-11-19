package com.example.mini_projet.views

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_projet.R
import com.example.mini_projet.adapters.restaurantadapter
import com.example.mini_projet.models.Restaurant
import com.example.mini_projet.utilis.ApiClient
import com.example.mini_projet.utilis.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Main1Activity : AppCompatActivity() {

    var restaurantList: ArrayList<Restaurant> = ArrayList()
    private var adapter: restaurantadapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)
        var recyclerView: RecyclerView = findViewById(R.id.restaurantslist)
        recyclerView.layoutManager=  LinearLayoutManager(this@Main1Activity, LinearLayoutManager.HORIZONTAL,false)
        adapter=restaurantadapter(restaurantList,this
        )

        recyclerView.adapter=adapter

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





