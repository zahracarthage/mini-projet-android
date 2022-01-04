package com.example.mini_projet.views

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.example.mini_projet.R

class Splash_screen : AppCompatActivity() {
    private lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        val topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation)
        val downAnimation = AnimationUtils.loadAnimation(this, R.anim.down_animation)
        val TopTextView = findViewById<TextView>(R.id.TopTextView)
        val MiddleTextView = findViewById<TextView>(R.id.MiddleTextView)
        val DownTextView = findViewById<TextView>(R.id.DownTextView)
        TopTextView.startAnimation(topAnimation)
        MiddleTextView.startAnimation(topAnimation)
        DownTextView.startAnimation(topAnimation)
        val splashScreenTimeOut = 4000
        if (mSharedPref.getBoolean(IS_REMEMBRED, true))
        {
            val homeIntent = Intent(this, Login::class.java)
            Handler().postDelayed({
                startActivity(homeIntent)
                finish()
            },splashScreenTimeOut.toLong())
        }
        else
        {
            val homeIntent = Intent(this, Accueil::class.java)
            Handler().postDelayed({
                startActivity(homeIntent)
                finish()
            },splashScreenTimeOut.toLong())
        }

    }
}