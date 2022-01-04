package com.example.mini_projet.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_projet.databinding.MenuItemBinding
import com.example.mini_projet.models.Restaurant
import com.example.mini_projet.utils.ApiClient
import com.squareup.picasso.Picasso

class MenuAdapter(private val customListeners: CustomListeners, private val context: Context) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private val diffUtilItemCallback = object : DiffUtil.ItemCallback<Restaurant.Menu>() {

        //pk is the primary key for the data class.
        //replace with any unique identifier of your specific data class.
        override fun areItemsTheSame(oldItem: Restaurant.Menu, newItem: Restaurant.Menu): Boolean {
            return oldItem.menuId == newItem.menuId
        }

        override fun areContentsTheSame(
            oldItem: Restaurant.Menu,
            newItem: Restaurant.Menu
        ): Boolean {
            return oldItem == newItem
        }

    }

    private val listDiffer = AsyncListDiffer(this, diffUtilItemCallback)

    private lateinit var binding: MenuItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding, customListeners, context)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount(): Int {
        return listDiffer.currentList.size
    }

    fun submitList(list: List<Restaurant.Menu>) {
        listDiffer.submitList(list)
    }

    class MenuViewHolder
    constructor(
        private val binding: MenuItemBinding,
        private val customListeners: CustomListeners,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {


        private val title = binding.menuName
        private val description = binding.menuDescription
        private val image = binding.imageView
        private val type = binding.menuTag
        fun bind(item: Restaurant.Menu) {
            val path = "https://firebasestorage.googleapis.com/v0/b/mini-projet-2e934.appspot.com/o/images%2F"+item.menuImg+"?alt=media"
            title.text = item.menuName
            description.text = item.menuDescription
            Picasso.with(context).load(path).into(image)

            ("#" + item.menuType).also { type.text = it }


            //Custom onClick for whole item onClick
            binding.root.setOnClickListener {
                //Pass respective parameter, adapterPosition or pk.
                customListeners.onItemSelected(item)
            }

            //TODO : Bind your data to views here.
            //Use CustomListeners respective function for respective view's listeners.
        }
    }

    // Interface to be inherited by view to provide
    //custom listeners for each item based on position
    //or other custom parameters (ex : Primary key)

    interface CustomListeners {
        fun onItemSelected(item: Restaurant.Menu)
        fun onCartSelected(item: Restaurant.Menu)
        // add your view listeners here
        // ex : fun onSwitchChecked(..) , fun onItemLongPress(..)
    }
}
