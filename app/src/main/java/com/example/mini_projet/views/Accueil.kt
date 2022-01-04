package com.example.mini_projet.views


import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import com.example.mini_projet.R

import android.view.View

import android.widget.Toast
import androidx.fragment.app.Fragment

import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.searchtoolbar.*

import com.example.mini_projet.models.User
import com.example.mini_projet.utilis.ApiInterface
import com.example.mini_projet.views.fragement.*

import kotlinx.android.synthetic.main.accueil.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.graphics.PorterDuff

import android.graphics.Bitmap

import android.graphics.drawable.BitmapDrawable

import android.graphics.Color


class Accueil : AppCompatActivity() {
    private lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.accueil)
        val toolbar: Toolbar = findViewById(R.id.toolbar54)
        toolbar.setNavigationIcon(R.drawable.yum)
        toolbar.setTitle("")
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            val drawable = resources.getDrawable(R.drawable.yum)
            val bitmap = (drawable as BitmapDrawable).bitmap
            val newdrawable: Drawable =
                BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, 50, 50, true))
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(newdrawable)
        }
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        val users_Fragment = fragementsubs()
        val user_Fragement = fragementuser()
        val reservation_Fragement = fragementreservation()
        val resto_Fragement = HomeFragment()
        val post_Fragement = fragementpost()
        val menu = bottomNavigationView.menu
        var profilimg =  menu.findItem(R.id.navigation_profil)
        profilimg.setTitle(mSharedPref.getString(USERNAME, "").toString())

        setCurrentFragment(resto_Fragement)
        bottomNavigationView.setItemIconTintList(null)

        bottomNavigationView.setOnItemSelectedListener() {

            when(it.itemId) {
                R.id.navigation_users-> {
                    toolbar.visibility = View.GONE

                    setCurrentFragment(users_Fragment)
                }
                R.id.navigation_home-> {
                    toolbar.visibility = View.VISIBLE
                    setCurrentFragment(resto_Fragement)
                }
                R.id.navigation_panier->{
                    toolbar.visibility = View.VISIBLE
                    setCurrentFragment(reservation_Fragement)
                }
                R.id.navigation_profil ->{
                    toolbar.visibility = View.VISIBLE
                    refresh()
                    setCurrentFragment(user_Fragement)
                }
                R.id.navigation_post->{
                    toolbar.visibility = View.VISIBLE
                    setCurrentFragment(post_Fragement)
                }
            }
            true
        }

    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container,fragment)
            addToBackStack("")
            commit()
        }

    private fun refresh()
    {
        val apiInterface = ApiInterface.create()
        mSharedPref = this.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        apiInterface.retriveuser(mSharedPref.getString(ID, "").toString()).enqueue(object :
            Callback<User> {

            override fun onResponse(call: Call<User>, response:
            Response<User>
            ) {
                val user = response.body()
                Log.e("user : ", user.toString())
                if(user != null)
                {
                    mSharedPref.edit().apply{
                        putString(FOLLOWERS,user.followers.size.toString())
                        putString(FOLLOWING,user.following.size.toString())
                        putString(NBPOST,user.posts.size.toString())
                    }.apply()

                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@Accueil,"Connexion error!", Toast.LENGTH_SHORT).show()
            }
        })
    }

}

