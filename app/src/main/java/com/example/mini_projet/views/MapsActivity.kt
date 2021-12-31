package com.example.mini_projet.views

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.location.Location
import android.widget.Toast
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mini_projet.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var client: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
             mapFragment.getMapAsync(this)

        client = LocationServices.getFusedLocationProviderClient(this);

    }


    override fun onMapReady(googleMap: GoogleMap) {

        val bundle = getIntent().extras
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        val longitude = intent.getDoubleExtra("Longitude",0.0)
        val latitude = intent.getDoubleExtra("Latitude",0.0)
       // val zindex = intent.getFloatExtra("zoomindex",0.0f)
        Log.d("longitude: "+longitude,"latitude :"+latitude)
        val Restitle = intent.getStringExtra("Title")

      //  Log.d("zindeix",zindex.toString())

        mMap = googleMap
        val restaurant = LatLng(latitude,longitude)
        Log.d("rest",restaurant.toString())
        mMap.addMarker(MarkerOptions().position(restaurant).title(Restitle))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurant, 17F))
        mMap .uiSettings.isZoomControlsEnabled= true

        setUpMap()



    }


    private fun setUpMap()
    {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        mMap.isMyLocationEnabled=true
        client.lastLocation.addOnSuccessListener(this) { location->
            if (location != null)
            {
                lastLocation = location
                val currentLoc = LatLng(location.latitude,location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc,17f))
            }
        }
    }




}