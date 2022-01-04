package com.example.mini_projet.views.adapter

import android.app.Activity
import android.content.Intent
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
import kotlinx.android.synthetic.main.subsfrag.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



private lateinit var mSharedPref: SharedPreferences
private var removedItem: String = ""
private var removedPosition: Int = 0
private var text : String =""
class UserAdapter (var users: MutableList<User>) :
    RecyclerView.Adapter<UserAdapter.UsersViewHolder>() {
    inner class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.subsfrag, parent, false)
        return UsersViewHolder(view)
    }
    fun removeItem(position: Int, viewHolder: RecyclerView.ViewHolder) {
        removedItem = users[position].username
        removedPosition = position
        users.removeAt(position)
        notifyItemRemoved(position)
        Snackbar.make(viewHolder.itemView, "$removedItem $text", Snackbar.LENGTH_LONG).show()
        /*removedItem = users[position].username.toString()
        removedPosition = position
        var removedData = users[position]
        users.removeAt(position)
        notifyItemRemoved(position)

        Snackbar.make(viewHolder.itemView, "$removedItem removed", Snackbar.LENGTH_LONG).setAction("UNDO") {
            users.add(removedPosition, removedData)
            notifyItemInserted(removedPosition)

        }.show()*/
    }
    override fun onBindViewHolder(holder: UsersViewHolder,position: Int) {
        var filename : String
        holder.itemView.apply {
            mSharedPref = holder.itemView.context.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
            if(users[position].picture=="No Picture")
            {
                users_img.setImageResource(R.drawable.avatar)
            }
            else
            {
                filename = users[position].picture
                val path = "https://firebasestorage.googleapis.com/v0/b/mini-projet-2e934.appspot.com/o/images%2F"+filename+"?alt=media"
                Glide.with(this)
                    .load(path)
                    .into(users_img)
            }
            users_img.setOnClickListener {
                val activity = holder.itemView.context as Activity
                val intent = Intent(activity, CheckUser::class.java)
                intent.putExtra("id",users[position].id)
                intent.putExtra("image",users[position].picture)
                intent.putExtra("username",users[position].username)
                intent.putExtra("email",users[position].email)
                intent.putExtra("followers",users[position].followers.size.toString()+" Followers ")
                intent.putExtra("following",users[position].following.size.toString()+" Following ")
                activity.startActivity(intent)
            }
            users_nbfollow.setText(users[position].followers.size.toString()+" Followers")
            users_nbfollow.setTag(users[position].followers.size.toString()+" Followers")
            users_username.text =  users[position].username
            if(users[position].id == mSharedPref.getString(ID, "").toString())
            {
                follows_add.visibility = View.GONE
            }
            val map: HashMap<String, String> = HashMap()
            map["iduserchecking"] = users[position].id
            map["idusercon"] = mSharedPref.getString(ID, "").toString()
            val apiInterface = ApiInterface.create()
            apiInterface.followunfollow(map).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response:
                Response<String>
                ) {
                    val  user = response.body()
                    if(user.toString() == "follow")
                    {
                        text = "Follow"
                        follows_add.setText("Follow")
                    }
                    else
                    {
                        text = "Unfollow"
                        follows_add.setText("Unfollow")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("error","error")
                }
            })

            follows_add.setOnClickListener{
                val apiInterface = ApiInterface.create()

                apiInterface.follow(users[position].id,
                    mSharedPref.getString(ID, "").toString()).enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response:
                    Response<User>
                    ) {
                        val  user = response.body() as User
                        if(user !=null)
                        {
                            mSharedPref.edit().apply{
                                putString(FOLLOWERS, user.followers.size.toString())
                                putString(FOLLOWING,user.following.size.toString())
                            }.apply()
                                    removeItem(holder.adapterPosition,holder)
                                    //refresh(holder.adapterPosition,holder)
                        }
                       /* else
                        {
                            notifyItemChanged(position)
                        }*/
                    }
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.e("error","error")
                    }
                })
            }



        }
        holder.itemView.setOnClickListener{
            notifyDataSetChanged()
            // holder.itemView.context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return users.size
    }
}