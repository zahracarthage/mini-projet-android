package com.example.mini_projet.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mini_projet.R

class Accueil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil)
    }

    fun goToFirstPage(view: android.view.View) {
        val intent = Intent(applicationContext,Main1Activity::class.java)
        startActivity(intent)
        finish()
    }
}