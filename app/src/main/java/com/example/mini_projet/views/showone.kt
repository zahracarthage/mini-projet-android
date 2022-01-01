package com.example.mini_projet.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.adapters.NumberPickerBindingAdapter.setValue
import com.example.mini_projet.R
//import com.example.mini_projet.adapters.restaurantadapter
import com.example.mini_projet.models.Restaurant
import com.example.mini_projet.utils.ApiClient
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class showone : AppCompatActivity() {

 private var descriptionsec: TextView? = null
    private  var img : ImageView? = null
    private var idRestaurant: String?=null
    private var longitude: Double = 0.0
    private var latitude: Double =0.0
    private var zoomIndex : Double = 0.0
    private lateinit var Title : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showone)

        var url: String = "http://192.168.1.6:3000/"

        descriptionsec = findViewById(R.id.aboutSection)
        val bundle = getIntent().extras
        if (bundle != null) {
            intent.putExtras(bundle);
        }
     //   val position = intent.getStringExtra("position")
       // val title = intent.getStringExtra("title")

        val description = intent.getStringExtra("description")
        val id = intent.getStringExtra("id")
        Log.d("aa","ffkf-----------------")
        idRestaurant=id
        Log.d("iddd:",idRestaurant.toString())
       // Log.d("+id",id.toString())
        img = findViewById(R.id.resaurantpicture)

        descriptionsec?.text = description.toString()

//        ApiClient.apiservice.findRestaurantbyId(id)
//            ?.enqueue(object: Callback<Restaurant?> {
//                override fun onResponse(
//                    call: Call<Restaurant?>,
//                    response: Response<Restaurant?>
//                )
//
//                {
//                    val response = response.body()
//                  //  Log.d("restaurant id"+id,response.toString())
//                    Picasso.with(applicationContext).load(url+response?.image).into(img)
//                    //localisationrestaurant= response?.localisation
//                    Title = response?.title.toString()
//
//                    if (response != null) {
//                        longitude = response.RestaurantLocalisation[0].Longititude
//                        latitude = response.RestaurantLocalisation[0].Latitude
//                        zoomIndex = response.RestaurantLocalisation[0].zoomIndex
//
//
//
//                    }
//
//
//                    response?.let {
//                       // adapter?.updateData(it)
//                    //    Log.d("restaurant id1 : "+id,response.toString())
//                      //  Log.d("menu: ",response.ListMenu.toString())
//
//                    }
//                }
//
//                override fun onFailure(call: Call<Restaurant?>, t: Throwable) {
//                    Toast.makeText(getApplicationContext(), "mon message", Toast.LENGTH_SHORT).show();
//                    t.message?.let { Log.d("exception", it) }
//                }
//            })


    }

    fun goToPlace(view: android.view.View) {
      //  navigate()
      //  IdRestaurant
        idRestaurant?.let { Log.d("aa", it) }
        val intent = Intent(applicationContext,MapsActivity::class.java)
         intent.putExtra("Longitude",longitude)
         intent.putExtra("Latitude",latitude)
         intent.putExtra("zoomindex",zoomIndex)
        intent.putExtra("Title",Title)
        startActivity(intent)
        finish()


    }




}


