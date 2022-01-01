package com.example.mini_projet.views

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.navArgs
import com.example.mini_projet.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var client: FusedLocationProviderClient
    private val args: MapsActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        client = LocationServices.getFusedLocationProviderClient(this);

    }


    override fun onMapReady(googleMap: GoogleMap) {

        val restaurant = args.resturant


        mMap = googleMap

        Log.d("rest", restaurant.toString())
        args.resturant?.RestaurantLocalisation.getOrNull(0)?.let {
            val latLng = LatLng(it.Latitude, it.Longititude)
            mMap.addMarker(
                MarkerOptions().position(latLng).title(restaurant.title)
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17F))
        }

        mMap.uiSettings.isZoomControlsEnabled = true

        setUpMap()


    }


    private fun setUpMap() {
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
        mMap.isMyLocationEnabled = true
        client.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                val currentLoc = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 17f))
            }
        }
    }


}