package com.example.mini_projet.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_projet.R
import com.example.mini_projet.models.Restaurant

class categorieadapter(private var categoryList: ArrayList<String>,private var restaurantList: ArrayList<Restaurant>, private val context: Context) : RecyclerView.Adapter<categorieadapter.ViewHolder>() {
  //  private lateinit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.categorie_card_view, parent, false)
        )
    }
    fun updateData(newCategory: ArrayList<String>) {
       // categoryList = newCategory

        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        // return categorieList.size
        return categoryList.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder, position: Int
    ) {
//        val category = categoryList.get(position)
//        val restaurant= restaurantList.get(position)
//        holder.categorytxtView?.text = restaurant.category.toString()
    }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var categorytxtView: TextView? = null

            init {
//                categorytxtView = itemView.findViewById(R.id.categoryname)

            }
        }

}


