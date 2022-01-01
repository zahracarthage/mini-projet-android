package com.example.mini_projet.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mini_projet.MainActivity
import com.example.mini_projet.databinding.ActivityAccueilBinding

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccueilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccueilBinding.inflate(layoutInflater)

        binding.btncontinue.setOnClickListener {

            handleNavigate()

        }
        setContentView(binding.root)
    }

    fun handleNavigate() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


}