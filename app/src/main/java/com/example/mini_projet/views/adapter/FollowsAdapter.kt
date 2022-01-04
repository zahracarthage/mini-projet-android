package com.example.mini_projet.views.adapter

import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mini_projet.R
import com.example.mini_projet.models.User
import com.example.mini_projet.utilis.ApiInterface
import com.example.mini_projet.views.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.followsfrag.view.*
import kotlinx.android.synthetic.main.searchfrag.view.*
import kotlinx.android.synthetic.main.searchfrag.view.search_users_img
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
private lateinit var mSharedPref: SharedPreferences
private var removedPosition: Int = 0
private var removedItem: String = ""
class FollowsAdapter (var users: MutableList<User>) :

    RecyclerView.Adapter<FollowsAdapter.FollowsViewHolder>() {
    inner class FollowsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowsViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.followsfrag, parent, false)
        return FollowsViewHolder(view)
    }
    fun removeItem(position: Int, viewHolder: RecyclerView.ViewHolder) {
        removedItem = users[position].username.toString()
        removedPosition = position
        var removedData = users[position]
        users.removeAt(position)
        notifyItemRemoved(position)
        Snackbar.make(viewHolder.itemView, "$removedItem removed", Snackbar.LENGTH_LONG).show()
    }
    override fun onBindViewHolder(holder: FollowsViewHolder, position: Int) {
        holder.itemView.apply {

            var filename : String
            mSharedPref = holder.itemView.context.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
            if(users[position].picture.toString()=="No Picture")
            {
                follows_users_img.setImageResource(R.drawable.avatar)
            }
            else
            {
                filename = users[position].picture.toString()
                val path = "https://firebasestorage.googleapis.com/v0/b/mini-projet-2e934.appspot.com/o/images%2F"+filename+"?alt=media"
                Glide.with(this)
                    .load(path)
                    .into(follows_users_img)
            }
            follows_users_username.text =  users[position].username

            follows_remove.setOnClickListener {
                val apiInterface = ApiInterface.create()
                apiInterface.follow(mSharedPref.getString(ID, "").toString(),users[position].id
                    ).enqueue(object : Callback<User> {

                    override fun onResponse(call: Call<User>, response:
                    Response<User>
                    ) {
                        val  user = response.body() as User
                        if(user !=null)
                        {
                            mSharedPref.edit().apply{
                                putString(FOLLOWERS, user.followers.size.toString())
                            }.apply()
                            Log.e("follow",user.toString())
                            removeItem(holder.adapterPosition,holder)
                        }
                        else
                        {
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.e("error","error")
                    }
                })
            }

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