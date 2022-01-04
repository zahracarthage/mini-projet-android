package com.example.mini_projet.views

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mini_projet.R
import com.example.mini_projet.models.Posts
import com.example.mini_projet.utilis.ApiInterface
import com.example.mini_projet.views.adapter.MyPostAdapter
import kotlinx.android.synthetic.main.activity_following.*
import kotlinx.android.synthetic.main.activity_my_post.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPost : AppCompatActivity() {
    private lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_post)
        var DesignImage = findViewById<CardView>(R.id.DesignImage)
        DesignImage.visibility = View.INVISIBLE
        val toolbar: Toolbar = findViewById(R.id.app_bar)
        toolbar.setTitle("")
        toolbar.setNavigationIcon(R.drawable.ic_retour)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        var mylist = mutableListOf<Posts>()
        mSharedPref =  getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        nomypost.visibility = View.GONE
        rv_mypost_users.visibility = View.VISIBLE
        val apiInterface = ApiInterface.create()
        apiInterface.retrivepostconnected(mSharedPref.getString(ID, "").toString()).enqueue(object :
            Callback<MutableList<Posts>> {
            override fun onResponse(call: Call<MutableList<Posts>>, response:
            Response<MutableList<Posts>>
            ) {
                val  post = response.body()
                if(post != null)
                {
                    mylist.addAll(post)
                    val adapter = MyPostAdapter(mylist)
                    rv_mypost_users.adapter = adapter
                    rv_mypost_users.layoutManager =
                        LinearLayoutManager(this@MyPost, LinearLayoutManager.VERTICAL, false)
                }
                else
                {
                    nomypost.visibility = View.VISIBLE
                    rv_mypost_users.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<MutableList<Posts>>, t: Throwable) {
                Log.e("error","error")
            }
        })



    }
}