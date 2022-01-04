package com.example.mini_projet.views.all_locations

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mini_projet.models.Restaurant
import com.example.mini_projet.utils.ApiActions
import com.example.mini_projet.utils.AppResult
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class AllLocationsViewModel() : ViewModel() {

    private val TAG = "AllLocationsViewModel"
    val apiActions = ApiActions()
    var restaurantsStable: ArrayList<Restaurant>? = null
    var currentStable: ArrayList<Restaurant>? = null

    var fusedProvider: FusedLocationProviderClient? = null
    private lateinit var locationCallback: LocationCallback

    val locationRequest = LocationRequest.create().apply {
        this.interval = 10000
        fastestInterval = 3000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    internal var locationUpdate = MutableLiveData<Location>()


    @SuppressLint("MissingPermission")
    fun getLocationAndUpdates() {
        initCallBack()
        viewModelScope.launch {
            try {
                Log.d(TAG, "getLocationAndUpdates: init called ")
                fusedProvider?.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )
            } catch (e: Exception) {
                Log.e(TAG, "getLocationAndUpdates: ${e.message ?: "unknown error occured"}")
            }
        }
    }

    private fun initCallBack() {
        viewModelScope.launch {
            try {
                locationCallback = object : LocationCallback() {

                    override fun onLocationResult(p0: LocationResult) {
                        super.onLocationResult(p0)


                        try {
                            Log.d(TAG, "onLocationResult:${p0}")

                            for (location in p0.locations) {
                                locationUpdate.value = location
                                Log.d(TAG, "onLocationResult:  ${location}")
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "onLocationResult: e", e)
                        }


                    }


                }
            } catch (e: Exception) {
                Log.e(TAG, "initCallBack: ${e.message}")
            }
        }
    }

    val resturants = MutableLiveData<ArrayList<Restaurant>>()
    fun getResturants() = viewModelScope.launch {
        apiActions.getAllRestaurants().catch { e ->
            Log.e(TAG, "getCategories: ", e)
        }.distinctUntilChanged().collect {
            when (it) {
                is AppResult.Error.NonRecoverableError -> {
                    Log.e(TAG, "getRestaurants: ", it.exception)
                }
                is AppResult.Error.RecoverableError -> {
                    Log.e(TAG, "getRestaurants: ", it.exception)
                }
                is AppResult.Failure -> {


                }
                AppResult.InProgress -> {}
                is AppResult.Success -> {

                    restaurantsStable = it.data

                    resturants.postValue(it.data ?: arrayListOf())
                }
            }

        }
    }

    fun calculateDistance(currentLocation: LatLng?, latLng: LatLng?): Double? {
        return if (currentLocation != null) {
            val startLocation = Location("")
            startLocation.latitude = currentLocation.latitude
            startLocation.longitude = currentLocation.longitude
            val endLocation = Location("")
            endLocation.latitude = latLng?.latitude ?: currentLocation.latitude
            endLocation.longitude = latLng?.longitude ?: currentLocation.longitude
            startLocation.distanceTo(endLocation).toDouble()
        } else {
            null
        }
    }

    fun getItem(id: String?): Int {
        val item = currentStable?.find {
            it.id == id
        }

        return currentStable?.indexOf(item) ?: -1
    }

    fun filterAndShow(currentLocation: LatLng): java.util.ArrayList<Restaurant>? {
        val result: java.util.ArrayList<Restaurant>? = restaurantsStable?.filter {
            val item = it.RestaurantLocalisation.getOrNull(0)

            val latLng = item?.let {
                LatLng(item?.Latitude, item?.Longititude)
            }

            val distnace = calculateDistance(currentLocation, latLng) ?: 0.0
            distnace <= 10000
        } as ArrayList?

        currentStable = result

        return result
    }

}