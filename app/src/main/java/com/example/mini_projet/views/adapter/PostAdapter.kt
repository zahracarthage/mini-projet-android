package com.example.mini_projet.views.adapter

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.mini_projet.R
import com.example.mini_projet.models.Posts
import com.example.mini_projet.views.*
import kotlinx.android.synthetic.main.mypostfrag.view.*
import kotlinx.android.synthetic.main.postfrag.view.*
import retrofit2.Response
private lateinit var mSharedPref: SharedPreferences
class PostAdapter(var posts: MutableList<Posts>) :

    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.postfrag, parent, false)
        return PostViewHolder(view)
    }
    override fun onBindViewHolder(holder: PostViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.itemView.apply {

            var filename : String
            mSharedPref = holder.itemView.context.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
            if(posts[position].usersimage=="No Picture")
            {
                users_post_img.setImageResource(R.drawable.avatar)
            }
            else
            {
                Log.e("image",posts[position].usersimage.toString())
                filename = posts[position].usersimage
                val path = "https://firebasestorage.googleapis.com/v0/b/mini-projet-2e934.appspot.com/o/images%2F"+filename+"?alt=media"
                Glide.with(this)
                    .load(path)
                    .into(users_post_img)
            }
            var d = posts[position].createdAt.time
            val now = System.currentTimeMillis()
            createdatpost.setText(DateUtils.getRelativeTimeSpanString(d,now, DateUtils.SECOND_IN_MILLIS))
                users_message_posted.setText(posts[position].message)
            //if(users[position].username.startsWith(search_text.text))
        }
        holder.itemView.setOnClickListener{
            // holder.itemView.context.startActivity(intent)

        }

    }
    public fun Additem(p : MutableList<Posts>) {
        posts.addAll(p)
        notifyDataSetChanged()
    }
    // Clean all elements of the recycler
    fun clear() {
        posts.clear()
        notifyDataSetChanged()
    }
    // Add a list of items -- change to type used
    fun addAll(postList: List<Posts>) {
        posts.addAll(postList)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return posts.size
    }

}
