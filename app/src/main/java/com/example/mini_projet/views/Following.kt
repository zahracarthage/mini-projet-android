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
import com.example.mini_projet.models.User
import com.example.mini_projet.utilis.ApiInterface
import com.example.mini_projet.views.adapter.FollowingAdapter
import com.example.mini_projet.views.adapter.FollowsAdapter
import kotlinx.android.synthetic.main.activity_following.*
import kotlinx.android.synthetic.main.activity_follows.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Following : AppCompatActivity() {
    private lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_following)
        var DesignImage = findViewById<CardView>(R.id.DesignImage)
        DesignImage.visibility = View.INVISIBLE
        val toolbar: Toolbar = findViewById(R.id.app_bar)
        toolbar.setTitle("")
        toolbar.setNavigationIcon(R.drawable.ic_retour)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        mSharedPref =  getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        nofollowing.visibility = View.GONE
        rv_following_users.visibility = View.VISIBLE
        Log.e("follows : ",FOLLOWERSARRAY.toString())
        val map: HashMap<String, String> = HashMap()
        map["followers"] = mSharedPref.getString(USERNAME, "").toString()
        val apiInterface = ApiInterface.create()
        apiInterface.getfollowing(mSharedPref.getString(ID, "").toString()).enqueue(object :
            Callback<MutableList<User>> {
            override fun onResponse(call: Call<MutableList<User>>, response:
            Response<MutableList<User>>
            ) {
                val  user = response.body() as MutableList<User>
                if(!user.isEmpty())
                {
                    Log.e("users",user.toString())
                    val adapter = FollowingAdapter(user)
                    rv_following_users.adapter = adapter
                    rv_following_users.layoutManager =
                        LinearLayoutManager(this@Following, LinearLayoutManager.VERTICAL, false)
                }
                else
                {
                    nofollowing.visibility = View.VISIBLE
                    rv_following_users.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<MutableList<User>>, t: Throwable) {
                Log.e("error","error")
            }
        })



    }
}