package com.example.mini_projet.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_projet.R
import com.example.mini_projet.models.Restaurant

class restaurantadapter(private var restaurantList: ArrayList<Restaurant>, private val context: Context) : RecyclerView.Adapter<restaurantadapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.restaurant_card_view, parent, false))
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    fun updateData(newRestaurant: ArrayList<Restaurant>) {
        restaurantList = newRestaurant
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val restaurant = restaurantList.get(position)
        holder.titleTextView?.text = restaurant.title
        }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         var titleTextView: TextView? = null

        init {
            titleTextView = itemView.findViewById(R.id.restaurantname)

        }
    }

}