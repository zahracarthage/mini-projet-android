package com.example.mini_projet.views.adapter

import android.app.Activity
import android.content.Intent
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mini_projet.R
import com.example.mini_projet.models.Restaurant
import com.example.mini_projet.views.detailresto
import kotlinx.android.synthetic.main.restosfrag.view.*

class RestoAdapter (var restos: MutableList<Restaurant>) :

    RecyclerView.Adapter<RestoAdapter.RestosViewHolder>() {
    inner class RestosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestosViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.restosfrag, parent, false)
        return RestosViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestosViewHolder, position: Int) {
        var filename : String
        holder.itemView.apply {
            filename = restos[position].picture
            val path = "https://firebasestorage.googleapis.com/v0/b/mini-projet-2e934.appspot.com/o/images%2F"+filename+"?alt=media"
            Glide.with(this)
                    .load(path)
                    .into(resto_img)
            resto_name.setText(restos[position].name)
            taptores.setOnClickListener {
                val activity = holder.itemView.context as Activity
                val intent = Intent(activity, detailresto::class.java)
                intent.putExtra("id",restos[position].id)
                intent.putExtra("name",restos[position].name)
                intent.putExtra("picture",restos[position].picture)
                intent.putExtra("about",restos[position].about)
                activity.startActivity(intent)
            }
        }
        holder.itemView.setOnClickListener{
            // holder.itemView.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return restos.size
    }
}