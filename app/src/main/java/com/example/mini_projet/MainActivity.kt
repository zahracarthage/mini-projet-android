package com.example.mini_projet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.mini_projet.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var bottomNavigation: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        bottomNavigation = binding.bottomNavigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_navigation_host) as NavHostFragment
        navController = navHostFragment.navController


        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->

        }

        NavigationUI.setupWithNavController(bottomNavigation, navController)
        setContentView(binding.root)
    }
}