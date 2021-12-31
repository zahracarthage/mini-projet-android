package com.example.mini_projet.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_projet.R
import com.example.mini_projet.models.Restaurant
import com.example.mini_projet.views.showone
import com.squareup.picasso.Picasso

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
        var url: String = "http://192.168.1.6:3000/"
        val restaurant = restaurantList.get(position)
        holder.titleTextView?.text = restaurant.title
        holder.categoryTextView?.text = restaurant.category?.joinToString();
        holder.descriptionTxtView?.text=restaurant.description
        holder.adresseTxtView?.text=restaurant.adresse
        holder.idTxtView?.text= restaurant._id
        Picasso.with(context).load(url+restaurant.image).into(holder.restoImg)


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var titleTextView: TextView? = null
        var categoryTextView : TextView? = null
        var restoImg: ImageView? =null
        var adresseTxtView: TextView?=null
        var descriptionTxtView: TextView?=null
        var idTxtView : TextView?= null


        override fun onClick(v: View?) {
            val Context = v?.context
            val title :String = titleTextView?.text.toString()
            val description : String = descriptionTxtView?.text.toString()
            val adresse :String = adresseTxtView?.text.toString()
            val id: String = idTxtView?.text.toString()

            val intent = Intent(Context,showone::class.java)
            intent.putExtra("position",adapterPosition.toString())
            intent.putExtra("title",title)
            intent.putExtra("description",description)
            intent.putExtra("adresse",adresse)
            intent.putExtra("id",id)

            Log.d("position in adapter:", adapterPosition.toString())
            Log.d("id:", id.toString())

            Context?.startActivity(intent)



        }

        init {
            titleTextView = itemView.findViewById(R.id.restaurantname)
            restoImg = itemView.findViewById(R.id.restoimg)
            categoryTextView = itemView.findViewById(R.id.restaurantCategory)
            adresseTxtView= itemView.findViewById(R.id.restoAdresse)
            descriptionTxtView= itemView.findViewById(R.id.restodescription)
            idTxtView = itemView.findViewById(R.id.restoId)
            itemView.setOnClickListener(this)
        }



    }}




