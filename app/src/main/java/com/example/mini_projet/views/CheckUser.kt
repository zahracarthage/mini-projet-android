package com.example.mini_projet.views

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mini_projet.R
import com.example.mini_projet.models.Posts
import com.example.mini_projet.models.User
import com.example.mini_projet.utilis.ApiInterface
import com.example.mini_projet.views.adapter.FollowingAdapter
import com.example.mini_projet.views.adapter.MyPostAdapter
import kotlinx.android.synthetic.main.activity_check_user.*
import kotlinx.android.synthetic.main.activity_following.*
import kotlinx.android.synthetic.main.activity_my_post.*
import kotlinx.android.synthetic.main.fragementuser.*
import kotlinx.android.synthetic.main.subsfrag.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckUser : AppCompatActivity() {
    private lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_user)
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
        checkuser_notpost.visibility = View.GONE
        rv_checkuserposts_users.visibility = View.VISIBLE
        var filename : String
        if(intent.getStringExtra("image").toString()=="No Picture")
        {
            checkuser_imageuser.setImageResource(R.drawable.avatar)
        }
        else
        {
            filename = intent.getStringExtra("image").toString()
            val path = "https://firebasestorage.googleapis.com/v0/b/mini-projet-2e934.appspot.com/o/images%2F"+filename+"?alt=media"
            Glide.with(this)
                .load(path)
                .into(checkuser_imageuser)
        }
        var mylist = mutableListOf<Posts>()
        checkuser_username.setText("@"+intent.getStringExtra("username").toString())
        usercheckedverified.setText("@"+intent.getStringExtra("username").toString()+" ")

        if(intent.getStringExtra("email").toString()!="No Email")
        {
            usercheckedverified.visibility = View.VISIBLE
            checkuser_username.visibility = View.GONE
        }

        val apiInterface = ApiInterface.create()
        apiInterface.retrivepostconnected(intent.getStringExtra("id").toString()).enqueue(object :
            Callback<MutableList<Posts>> {
            override fun onResponse(call: Call<MutableList<Posts>>, response:
            Response<MutableList<Posts>>
            ) {
                val  post = response.body()
                if(post != null)
                {
                    mylist.addAll(post)
                    val adapter = MyPostAdapter(mylist)
                    rv_checkuserposts_users.adapter = adapter
                    rv_checkuserposts_users.layoutManager =
                        LinearLayoutManager(this@CheckUser, LinearLayoutManager.VERTICAL, false)
                }
                else
                {
                    checkuser_notpost.visibility = View.VISIBLE
                    rv_checkuserposts_users.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<MutableList<Posts>>, t: Throwable) {
                Log.e("error","error")
            }
        })
    }
}