package com.example.mini_projet.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_projet.R
import com.example.mini_projet.adapters.categorieadapter
import com.example.mini_projet.adapters.restaurantadapter
import com.example.mini_projet.models.Restaurant
import com.example.mini_projet.utils.ApiClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Main1Activity : AppCompatActivity(){

    var restaurantList: ArrayList<Restaurant> = ArrayList()
    var categoryList: ArrayList<String> = ArrayList()
    private var adapter: restaurantadapter? = null
    private var adapterCategory: categorieadapter? =null
    private var toolbar: Toolbar? = null
    private  var bottomNavigation: BottomNavigationView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        bottomNavigation =  findViewById(R.id.bottom_navigation);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
       // bottomNavigation.selectedItemId(R.id.MenuHome)
        bottomNavigation?.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.MenuHome-> {

                }

                R.id.MenuProfile->{
                    val toast = Toast.makeText(this, "profile", Toast.LENGTH_LONG)
                    toast.show()
                }
                R.id.MenuNearby->{
                    val intent = Intent(applicationContext, allPlaces::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.MenuPanier->{
                    val toast = Toast.makeText(this, "Panier", Toast.LENGTH_LONG)
                    toast.show()
                }

            }
            true
        }





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
                Toast.makeText(getApplicationContext(), "cat f", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "res failure", Toast.LENGTH_SHORT).show();
                    t.message?.let { Log.d("exception", it) }
                }
            })



    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bottom_navigation_menu, menu)
        return true
    }

     fun onNavigationItemSelected  (item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.MenuHome -> {
                true
            }
            R.id.MenuProfile -> {
                // showHelp()
                true
            }
            R.id.MenuNearby ->
            {
                Log.d("bug","bug")
                val toast = Toast.makeText(this, "OK", Toast.LENGTH_LONG)
                toast.show()
                val intent = Intent(applicationContext, allPlaces::class.java)
                startActivity(intent)
                finish()
                true

            }
            R.id.MenuPanier->
            {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }



    }






}







