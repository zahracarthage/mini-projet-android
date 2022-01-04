package com.example.mini_projet.views.adapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mini_projet.R
import com.example.mini_projet.models.User
import kotlinx.android.synthetic.main.fragementsearch.view.*
import kotlinx.android.synthetic.main.searchfrag.view.*
import kotlinx.android.synthetic.main.searchtoolbar.view.*
import kotlinx.android.synthetic.main.subsfrag.view.*

class SearchAdapter (var users: MutableList<User>) :

    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.searchfrag, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.itemView.apply {

            var filename : String

            if(users[position].picture.toString()=="No Picture")
            {
                search_users_img.setImageResource(R.drawable.avatar)
            }
            else
            {
                filename = users[position].picture.toString()
                val path = "https://firebasestorage.googleapis.com/v0/b/mini-projet-2e934.appspot.com/o/images%2F"+filename+"?alt=media"
                Glide.with(this)
                    .load(path)
                    .into(search_users_img)
            }
            search_users_username.text =  users[position].username
            search_users_nbfollow.text = "0 Followers"


            //if(users[position].username.startsWith(search_text.text))
        }
        holder.itemView.setOnClickListener{
            // holder.itemView.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return users.size
    }
}