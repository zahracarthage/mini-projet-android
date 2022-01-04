package com.example.mini_projet.views
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.mini_projet.R
import kotlinx.android.synthetic.main.activity_detailresto.*
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController


import androidx.navigation.navArgs
import com.example.mini_projet.models.Menus
import com.example.mini_projet.models.Restaurant
import com.example.mini_projet.views.fragement.MenuFragment

import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.code_reservation.view.*
import kotlinx.android.synthetic.main.fragementuser.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response


class detailresto : AppCompatActivity() {
    private lateinit var mSharedPref: SharedPreferences
    private var longitude: Double = 0.0
    private var latitude: Double =0.0
    private var zoomIndex : Double = 0.0
    //private val arguments: detailrestoArgs by navArgs()
       // private lateinit var arguments: detailrestoArgs


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailresto)
      // arguments = detailrestoArgs.fromBundle(requireArguments())

        retour.setOnClickListener {
            finish()

        }
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        var filename : String
        filename = intent.getStringExtra("picture").toString()
        val path = "https://firebasestorage.googleapis.com/v0/b/mini-projet-2e934.appspot.com/o/images%2F"+filename+"?alt=media"
        Glide.with(this)
            .load(path)
            .into(restodetail_img)
        restonamedetail.setText(intent.getStringExtra("name").toString())
        description.setText(intent.getStringExtra("about").toString())
        var id = intent.getStringExtra("id").toString()
        var name = intent.getStringExtra("name").toString()
        var picture = intent.getStringExtra("picture").toString()
        var longititude = intent.getDoubleExtra("longitude",0.0)
        var latitude = intent.getDoubleExtra("latitude",0.0)
        var zoomIndex = intent.getDoubleExtra("zoomIndex",0.0)
        var menu = intent.getParcelableExtra<Menus>("menu")

        ic_location.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("id",id)
            intent.putExtra("name",name)
            intent.putExtra("longitude",longititude)
            intent.putExtra("latitude",latitude)
            intent.putExtra("zoomIndex",zoomIndex)
            startActivity(intent)


        }

        ic_menu.setOnClickListener {


         /*  findNavController().navigate(
                R.id.menuFragment,(Menus(restaurant.ListMenu))
                )*/
            val intent = Intent(this, MenuFragment::class.java)
            intent.putExtra("menu",menu)

            startActivity(intent)
        }
         reserve.setOnClickListener {
             if(mSharedPref.getString(EMAIL,"").toString() != "No Email")
             {
                 val intent = Intent(this, Reserve::class.java)
                 intent.putExtra("id",id)
                 intent.putExtra("name",name)
                 intent.putExtra("picture",picture)

                 startActivity(intent)
             }
             else
             {
                 val intent = Intent(this, VerficationAccount::class.java)
                 startActivity(intent)
             }

        }

    }

    override fun onRestart() {
        super.onRestart()
    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container,fragment)
            addToBackStack("")
            commit()
        }

}




