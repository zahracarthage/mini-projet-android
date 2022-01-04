package com.example.mini_projet.views.fragement

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_projet.R
import com.example.mini_projet.adapters.RestaurantAdapter
import com.example.mini_projet.databinding.AllFragmentBinding
import com.example.mini_projet.models.Restaurant
import com.example.mini_projet.views.all_locations.AllLocationsViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class AllLocationsFragment() : Fragment(), RestaurantAdapter.CustomListeners,
    GoogleMap.OnMarkerClickListener {
    private lateinit var lastLocation: Location

    private lateinit var binding: AllFragmentBinding
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var infoLayout: ConstraintLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RestaurantAdapter

    private lateinit var client: FusedLocationProviderClient
    private var mMap: GoogleMap? = null

    private var firstTime = false

    private val viewModel: AllLocationsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        client = LocationServices.getFusedLocationProviderClient(requireContext());

        viewModel.fusedProvider = client
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AllFragmentBinding.inflate(inflater, container, false)
        infoLayout = binding.itemLayout
        recyclerView = binding.itemRecycler


        adapter = RestaurantAdapter(this, requireContext())
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment


        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        recyclerView.adapter = adapter

        infoLayout.isVisible = false



        mapFragment.getMapAsync { googleMap ->
            mMap = googleMap
            setUpMap()
            viewModel.getLocationAndUpdates()

            viewModel.getResturants()


            mMap?.setOnMapClickListener {
                infoLayout.isVisible = false

            }
            mMap?.setOnMarkerClickListener(this)

            mMap?.uiSettings?.isZoomControlsEnabled = true
//            mMap?.setOnInfoWindowClickListener(this)
//            mMap?.setOnInfoWindowCloseListener(this)


        }


        return binding.root
    }

    private fun observer(location: Location) {

        viewModel.resturants.observe(viewLifecycleOwner, {
            println("result $it")
            if (!it.isNullOrEmpty()) {

                mMap?.clear()
                viewModel.filterAndShow(LatLng(location.latitude, location.longitude))?.let {
                    it.forEach { resturant ->
                        resturant.RestaurantLocalisation.getOrNull(0)?.let {
                            val latLng = LatLng(it.Latitude, it.Longititude)
                            mMap?.addMarker(
                                MarkerOptions().position(latLng).title(resturant.name)
                                    .snippet(resturant.id)
                            )

                            println("marker set")
                        }


                    }



                    adapter.submitList(it ?: listOf())
                }

            }

        })
    }

    override fun onStart() {
        super.onStart()



        viewModel.locationUpdate.observe(viewLifecycleOwner, {
            if (!firstTime && it != null) {
                showLocation(it)
                firstTime = true
            }
        })
    }

    override fun onItemSelected(restaurant: Restaurant) {

     /*   requireView().findNavController()
            .navigate(AllLocationsFragmentDirections.actionAllPlacesToShowOneFragment(restaurant))*/

    }

    override fun onMarkerClick(p0: Marker): Boolean {


        println("marker clicked ${p0} ${p0.snippet}")
        val result = viewModel.getItem(p0.snippet)
        if (result != -1) {
            println("result not found ")
            recyclerView.smoothScrollToPosition(result)
            infoLayout.isVisible = true
            mMap?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    p0.position,
                    18f
                )
            )
        }






        return false
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        mMap?.isMyLocationEnabled = true
//        client.lastLocation.addOnSuccessListener(requireActivity()) { location ->
//            if (location != null) {
//                lastLocation = location
//                println("locations ${location}")
//                showLocation(location)
//            }
//        }
    }

    private fun showLocation(location: Location?) {
        if (location != null) {
            val currentLoc = LatLng(location.latitude, location.longitude)
            println("location changed")
            observer(location)
            mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 17f))
        }

    }


}