package com.example.mini_projet.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.mini_projet.R
import org.w3c.dom.Text


class showone : AppCompatActivity() {

 private var descriptionsec: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showone)

        descriptionsec = findViewById(R.id.aboutSection)
        val bundle = getIntent().extras
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        val position = intent.getStringExtra("position")
        val title = intent.getStringExtra("title")
        Log.d("title in new ac: ", title.toString())
        Log.d("possition in new ac: ", position.toString())
        val description = intent.getStringExtra("description")
        Log.d("possition in new ac: ", description.toString())

        descriptionsec?.text = description.toString()

       // binding.aboutSection.text = description




    }
}


