package com.example.mini_projet.views.fragement

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mini_projet.R
import com.example.mini_projet.models.Posts
import com.example.mini_projet.models.User
import com.example.mini_projet.utilis.ApiInterface
import com.example.mini_projet.views.*
import com.example.mini_projet.views.adapter.PostAdapter
import kotlinx.android.synthetic.main.fragementpost.*
import kotlinx.android.synthetic.main.fragementresto.*
import kotlinx.android.synthetic.main.transtool.*
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class fragementpost : Fragment(R.layout.fragementpost) {
    val apiInterface = ApiInterface.create()
    protected val maxSize = 0
    //var mylist : MutableList<Posts>?=null
    var listpost : List<Posts>?=null
    private lateinit var mSharedPref: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.fragementpost, container, false)
        refresh(context)
        return rootView

    }
    fun refresh(context: Context?)
    {
        context?.let {
            val fragementManager = (context as? AppCompatActivity)?.supportFragmentManager
            fragementManager?.let {
                val currentFragement = fragementManager.findFragmentById(R.id.container)
                currentFragement?.let {
                    val fragmentTransaction = fragementManager.beginTransaction()
                    fragmentTransaction.detach(it)
                    fragmentTransaction.attach(it)
                    fragmentTransaction.commit()
                }
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var mylist = mutableListOf<Posts>()
        mSharedPref =  requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        if(mSharedPref.getString(PICTURE, "").toString()=="No Picture")
        {
            users_post_all_img.setImageResource(R.drawable.avatar)
        }
        else
        {
            val filename2 = mSharedPref.getString(PICTURE, "").toString()
            val path = "https://firebasestorage.googleapis.com/v0/b/mini-projet-2e934.appspot.com/o/images%2F"+filename2+"?alt=media"
            Glide.with(context)
                .load(path)
                .into(users_post_all_img)
        }
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        progBarPost.visibility = View.VISIBLE
        apiInterface.retrivepost(mSharedPref.getString(ID, "").toString()).enqueue(object : Callback<MutableList<User>> {
            override fun onResponse(call: Call<MutableList<User>>, response:
            Response<MutableList<User>>
            ) {
                if (response.body() == null) {
                    postempty.visibility = View.VISIBLE
                } else
                {
                    val user = response.body() as MutableList<User>
                    if (user != null) {
                        Log.e("user size :", user.size.toString())
                        Log.e("users : ", user.toString())
                        var i = 0
                        while (i >= 0 && i < user.size) {
                            if(user[i].posts.size==0)
                            {
                                postempty.visibility = View.VISIBLE
                            }
                            Log.e("size : ",user[i].posts.size.toString())
                            var j = 0
                            while (j >= 0 && j < user[i].posts.size){
                            //for (j in 0 until user[i].posts.size) {
                                Log.e("post id : ", user[i].posts[j])
                                requireActivity().window.setFlags(
                                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                                )
                                progBarPost.visibility = View.VISIBLE
                                apiInterface.getById(user[i].posts[j])
                                    .enqueue(object : Callback<Posts> {
                                        override fun onResponse(
                                            call: Call<Posts>, response:
                                            Response<Posts>
                                        ) {
                                            val post = response.body()

                                            Log.e("post : ", post.toString())
                                            if (post != null) {
                                                mylist.addAll(listOf(post))
                                                //mylist!!.addAll(post)
                                                Log.e("list size : ",mylist.size.toString())

                                                var adapter = PostAdapter(mylist)
                                                //adapter.Additem(mylist)
                                                rv_posts_users.adapter = adapter
                                                rv_posts_users.layoutManager =
                                                    LinearLayoutManager(
                                                        context,
                                                        LinearLayoutManager.VERTICAL,
                                                        false
                                                    )


                                            } else {
                                                postempty.visibility = View.VISIBLE
                                            }
                                            progBarPost.visibility = View.INVISIBLE
                                            requireActivity().window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                                        }
                                        override fun onFailure(
                                            call: Call<Posts>,
                                            t: Throwable
                                        ) {
                                            Toast.makeText(context, "Connexion error!", Toast.LENGTH_SHORT).show()
                                            progBarPost.visibility = View.INVISIBLE
                                            requireActivity().window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                                        }
                                    })
                                j++
                            }

                            i++
                        }
                        mylist.clear()



                    }
                }
                progBarPost.visibility = View.INVISIBLE
                requireActivity().window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

            }

            override fun onFailure(call: Call<MutableList<User>>, t: Throwable) {
                Toast.makeText(context, "Connexion error!", Toast.LENGTH_SHORT).show()
                progBarPost.visibility = View.INVISIBLE
                requireActivity().window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
        })

        post_add.setOnClickListener {
            if (post_message.text.isEmpty()){
                Toast.makeText(context,R.string.mustNotBeEmpty, Toast.LENGTH_SHORT).show()

            }
            else
            {
                requireActivity().window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
                apiInterface.addpost(mSharedPref.getString(ID, "").toString(),post_message.text.toString()).enqueue(object : Callback<User> {

                    override fun onResponse(call: Call<User>, response:
                    Response<User>
                    ) {
                        val  user = response.body()
                        if(user !=null)
                        {
                            mSharedPref.edit().apply{
                                putString(FOLLOWERS,user.followers.size.toString())
                                putString(NBPOST,user.posts.size.toString())
                                putString(FOLLOWING,user.following.size.toString())

                            }.apply()
                            post_message.text.clear()
                            val intent = Intent(context,MyPost::class.java)
                            startActivity(intent)
                        }
                        else
                        {

                        }
                        requireActivity().window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

                    }
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        post_message.text.clear()
                        val intent = Intent(context,MyPost::class.java)
                        startActivity(intent)
                    }
                })
                requireActivity().window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

            }

        }
        super.onViewCreated(view, savedInstanceState)
    }

}
