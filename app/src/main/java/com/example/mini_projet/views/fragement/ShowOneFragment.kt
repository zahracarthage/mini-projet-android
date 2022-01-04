package com.example.mini_projet.views.fragement

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.mini_projet.databinding.ActivityShowoneBinding
import com.example.mini_projet.models.Menus
import com.example.mini_projet.models.Restaurant
import com.example.mini_projet.utils.ApiClient
import com.squareup.picasso.Picasso

class ShowOneFragment : Fragment() {


    private lateinit var binding: ActivityShowoneBinding
    private lateinit var arguments: ShowOneFragmentArgs
    private lateinit var image: ImageView
    private lateinit var freeWifi: AppCompatButton
    private lateinit var like: AppCompatButton
    private lateinit var menu: AppCompatButton
    private lateinit var places: AppCompatButton
    private lateinit var share: AppCompatButton

    private lateinit var abtText: TextView
    private lateinit var aboutDescription: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments = ShowOneFragmentArgs.fromBundle(requireArguments())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ActivityShowoneBinding.inflate(inflater, container, false)

        image = binding.resaurantpicture
        freeWifi = binding.btnWifi
        like = binding.btnLike
        menu = binding.btnMenu
        places = binding.btnlocalisation
        share = binding.btnShare
        aboutDescription = binding.aboutSection
        abtText = binding.aboutTxt


        return binding.root
    }


    fun goToPlace(view: android.view.View) {
        //  navigate()
        //  IdRestaurant


    }

    override fun onStart() {
        super.onStart()

        val restaurant: Restaurant = arguments.resturant
        Picasso.with(context).load(ApiClient.BASE_URL + restaurant.picture).into(image)

        abtText.text = restaurant.name
        aboutDescription.text = restaurant.about


        menu.setOnClickListener {
            requireView().findNavController().navigate(
                ShowOneFragmentDirections.actionShowOneFragmentToMenuFragment(
                    Menus(restaurant.ListMenu)
                )
            )
            Log.d("menu",restaurant.ListMenu.toString())

        }

        places.setOnClickListener {
            requireView().findNavController()
                .navigate(ShowOneFragmentDirections.actionGlobalMapsActivity(restaurant))
        }
    }
}