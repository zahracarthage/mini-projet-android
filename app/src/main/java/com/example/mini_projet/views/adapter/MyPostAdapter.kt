package com.example.mini_projet.views.adapter

import android.content.SharedPreferences
import android.os.Build
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mini_projet.R
import com.example.mini_projet.models.Posts
import com.example.mini_projet.views.*
import kotlinx.android.synthetic.main.followingfrag.view.following_users_img
import kotlinx.android.synthetic.main.mypostfrag.view.*
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.time.ExperimentalTime
import kotlin.time.hours

private lateinit var mSharedPref: SharedPreferences
class MyPostAdapter (var posts: MutableList<Posts>) :

    RecyclerView.Adapter<MyPostAdapter.MyPostViewHolder>() {
    inner class MyPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.mypostfrag, parent, false)
        return MyPostViewHolder(view)
    }
    @ExperimentalTime
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyPostViewHolder, position: Int) {
        holder.itemView.apply {
            var filename : String
            mSharedPref = holder.itemView.context.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
            if(posts[position].usersimage=="No Picture")
            {
                users_mypost_img.setImageResource(R.drawable.avatar)
            }
            else
            {
                filename = posts[position].usersimage
                val path = "https://firebasestorage.googleapis.com/v0/b/mini-projet-2e934.appspot.com/o/images%2F"+filename+"?alt=media"
                Glide.with(this)
                    .load(path)
                    .into(users_mypost_img)
            }
            users_mymessage_posted.text =  posts[position].message
            var d = posts[position].createdAt.time
            val now = System.currentTimeMillis()
            createdat.setText(DateUtils.getRelativeTimeSpanString(d,now, DateUtils.SECOND_IN_MILLIS))
            //createdat.setText(posts[position].createdAt.toString())
        }
        holder.itemView.setOnClickListener{
            // holder.itemView.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}