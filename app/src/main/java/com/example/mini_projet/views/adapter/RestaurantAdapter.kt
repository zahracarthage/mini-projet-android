package com.example.mini_projet.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_projet.databinding.RestaurantCardViewBinding
import com.example.mini_projet.models.Restaurant
import com.example.mini_projet.utils.ApiClient
import com.squareup.picasso.Picasso

class RestaurantAdapter(
    private val customListeners: CustomListeners,
    private val context: Context
) :
    RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    private val diffUtilItemCallback = object : DiffUtil.ItemCallback<Restaurant>() {

        //pk is the primary key for the data class.
        //replace with any unique identifier of your specific data class.
        override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem == newItem
        }

    }

    private val listDiffer = AsyncListDiffer(this, diffUtilItemCallback)

    private lateinit var binding: RestaurantCardViewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        binding =
            RestaurantCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding, customListeners, context)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount(): Int {
        return listDiffer.currentList.size
    }

    fun submitList(list: List<Restaurant>) {
        listDiffer.submitList(list)
    }

    class RestaurantViewHolder
    constructor(

        private val binding: RestaurantCardViewBinding,
        private val customListeners: CustomListeners,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {


        private val title = binding.restaurantname
        private val category = binding.restaurantCategory
        private val description = binding.restodescription
        private val address = binding.restoAdresse
        private val restoid = binding.restoId
        private val img = binding.restoimg




        fun bind(restaurant: Restaurant) {


            title.text = restaurant.name
            category.text = restaurant.category?.joinToString(separator = ",");
            description.text = restaurant.about
            restoid.text = restaurant.id
            val path = "https://firebasestorage.googleapis.com/v0/b/mini-projet-2e934.appspot.com/o/images%2F"+restaurant.picture+"?alt=media"
            Picasso.with(context).load(path).into(img)

            //Custom onClick for whole item onClick
            binding.root.setOnClickListener {
                //Pass respective parameter, adapterPosition or pk.
                customListeners.onItemSelected(restaurant)
            }

            //Use CustomListeners respective function for respective view's listeners.
        }
    }

    // Interface to be inherited by view to provide
    //custom listeners for each item based on position
    //or other custom parameters (ex : Primary key)

    interface CustomListeners {
        fun onItemSelected(restaurant: Restaurant)
        // add your view listeners here
        // ex : fun onSwitchChecked(..) , fun onItemLongPress(..)
    }
}
