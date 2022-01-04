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
import com.example.mini_projet.views.adapter.FollowsAdapter
import kotlinx.android.synthetic.main.activity_follows.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowsActivity : AppCompatActivity() {
    private lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follows)
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
        nofollows.visibility = View.GONE
        rv_follows_users.visibility = View.VISIBLE
        Log.e("follows : ",FOLLOWERSARRAY.toString())
        //val set: MutableSet<String> = mSharedPref.getStringSet(FOLLOWERSARRAY, null) as MutableSet<String>
        val map: HashMap<String, String> = HashMap()
        map["followers"] = mSharedPref.getString(USERNAME, "").toString()
        val apiInterface = ApiInterface.create()
        apiInterface.getNbFollows(mSharedPref.getString(ID, "").toString()).enqueue(object : Callback<MutableList<User>> {
            override fun onResponse(call: Call<MutableList<User>>, response:
            Response<MutableList<User>>
            ) {
                val  user = response.body() as MutableList<User>
                if(!user.isEmpty())
                {
                    Log.e("users",user.toString())
                    val adapter = FollowsAdapter(user)
                    rv_follows_users.adapter = adapter
                    rv_follows_users.layoutManager =
                        LinearLayoutManager(this@FollowsActivity, LinearLayoutManager.VERTICAL, false)
                }
                else
                {
                    nofollows.visibility = View.VISIBLE
                    rv_follows_users.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<MutableList<User>>, t: Throwable) {
                Log.e("error","error")
            }
        })



    }
}