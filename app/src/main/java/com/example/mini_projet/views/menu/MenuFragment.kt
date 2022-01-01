package com.example.mini_projet.views.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_projet.adapters.MenuAdapter
import com.example.mini_projet.databinding.FragmentMenuLayouBinding
import com.example.mini_projet.models.Restaurant

class MenuFragment() : Fragment(), MenuAdapter.CustomListeners {

    private lateinit var binding: FragmentMenuLayouBinding
    private lateinit var adapter: MenuAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var args: MenuFragmentArgs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = MenuFragmentArgs.fromBundle(requireArguments())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuLayouBinding.inflate(inflater, container, false)

        val menus = args.menu

        recyclerView = binding.menuItems
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = MenuAdapter(this, requireContext())

        recyclerView.adapter = adapter

        adapter.submitList(menus.list)

        return binding.root
    }

    override fun onItemSelected(item: Restaurant.Menu) {

    }

    override fun onCartSelected(item: Restaurant.Menu) {

    }
}