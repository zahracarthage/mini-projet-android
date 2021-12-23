package com.example.mini_projet.views

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.mini_projet.R
import com.example.mini_projet.models.Restaurant
import com.example.mini_projet.utilis.ApiClient
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class showone : AppCompatActivity() {

 private var descriptionsec: TextView? = null
    private  var img : ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showone)

        var url: String = "http://192.168.1.9:3000/"

        descriptionsec = findViewById(R.id.aboutSection)
        val bundle = getIntent().extras
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        val position = intent.getStringExtra("position")
        val title = intent.getStringExtra("title")

        val description = intent.getStringExtra("description")
        val id = intent.getStringExtra("id")
        Log.d("+id",id.toString())
        img = findViewById(R.id.resaurantpicture)


        descriptionsec?.text = description.toString()

       // binding.aboutSection.text = description


        ApiClient.apiservice.findRestaurantbyId(id)
            ?.enqueue(object: Callback<Restaurant?> {
                override fun onResponse(
                    call: Call<Restaurant?>,
                    response: Response<Restaurant?>
                )

                {
                    val response = response.body()
                    Log.d("restaurant id"+id,response.toString())
                    Picasso.with(applicationContext).load(url+response?.image).into(img)


                    response?.let {
                       // adapter?.updateData(it)
                        Log.d("restaurant id1 : "+id,response.toString())

                    }
                }

                override fun onFailure(call: Call<Restaurant?>, t: Throwable) {
                    Toast.makeText(getApplicationContext(), "mon message", Toast.LENGTH_SHORT).show();
                    t.message?.let { Log.d("exception", it) }
                }
            })


    }
}


