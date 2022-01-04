package com.example.mini_projet.views.fragement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_projet.adapters.MenuAdapter
import com.example.mini_projet.databinding.FragmentMenuLayouBinding
import com.example.mini_projet.models.Menus
import com.example.mini_projet.models.Restaurant

class MenuFragment() : AppCompatActivity(), MenuAdapter.CustomListeners {

    private lateinit var binding: FragmentMenuLayouBinding
    private lateinit var adapter: MenuAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var args: MenuFragmentArgs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMenuLayouBinding.inflate(layoutInflater)


        val menus =intent.getParcelableExtra<Menus>("menu")

        recyclerView = binding.menuItems
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = MenuAdapter(this, this)

        recyclerView.adapter = adapter

        adapter.submitList(menus?.list ?: listOf())

        setContentView(binding.root)

    }


    override fun onItemSelected(item: Restaurant.Menu) {

    }

    override fun onCartSelected(item: Restaurant.Menu) {

    }

}