package com.example.mini_projet.views.fragement

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mini_projet.R
import com.example.mini_projet.views.*
import kotlinx.android.synthetic.main.fragementuser.*
import kotlinx.android.synthetic.main.fragementuser.followers
import kotlinx.android.synthetic.main.fragementuser.imageuser
import kotlinx.android.synthetic.main.fragementuser.username

class fragementuser : Fragment(R.layout.fragementuser) {
    private lateinit var mSharedPref: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var rootView: View = inflater.inflate(R.layout.fragementuser, container, false)

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
        refresh(context)
        mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        if(mSharedPref.getString(PICTURE, "").toString()=="No Picture")
        {
            imageuser.setImageResource(R.drawable.avatar)
        }
        else
        {
            val filename2 = mSharedPref.getString(PICTURE, "").toString()
            val path = "https://firebasestorage.googleapis.com/v0/b/mini-projet-2e934.appspot.com/o/images%2F"+filename2+"?alt=media"
            Glide.with(context)
                .load(path)
                .into(imageuser)
        }
        username.setText("@"+mSharedPref.getString(USERNAME, "").toString())
        usernameverified.setText("@"+mSharedPref.getString(USERNAME, "").toString()+" ")
        followers.setText(mSharedPref.getString(FOLLOWERS, "").toString()+" Followers")
        following.setText(mSharedPref.getString(FOLLOWING, "").toString()+" Following")
        nbpost.setText(mSharedPref.getString(NBPOST, "").toString()+" Posts")
        followers.setOnClickListener {
            val intent = Intent(context,FollowsActivity::class.java)
            startActivity(intent)
        }
        following.setOnClickListener {
            val intent = Intent(context,Following::class.java)
            startActivity(intent)
        }
        nbpost.setOnClickListener {
            val intent = Intent(context,MyPost::class.java)
            startActivity(intent)
        }

        all_locations.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.container,AllLocationsFragment())
                addToBackStack("")
                commit()
            }

        }


        UserLogout.setOnClickListener {
            val builder = AlertDialog.Builder(view.context)
            builder.setTitle("Logout")
            builder.setMessage("Are you sure you want to logout ?")
            builder.setPositiveButton("Yes"){ dialogInterface, which ->
                requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().clear().apply()
                val intent = Intent(context,Login::class.java)
                startActivity(intent)
            }
            builder.setNegativeButton("No"){dialogInterface, which ->
                dialogInterface.dismiss()
            }
            builder.create().show()
        }
        UserManagment.setOnClickListener {
            val intent = Intent(context,EditProfil::class.java)
            startActivity(intent)
        }
        verifyAccount.setOnClickListener {
            val intent = Intent(context,VerficationAccount::class.java)
            startActivity(intent)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        followers.setText(mSharedPref.getString(FOLLOWERS, "").toString()+" Followers")
        following.setText(mSharedPref.getString(FOLLOWING, "").toString()+" Following")
        nbpost.setText(mSharedPref.getString(NBPOST, "").toString()+" Posts")
        if(mSharedPref.getString(EMAIL, "").toString()!="No Email")
        {

            usernameverified.visibility = View.VISIBLE
            username.visibility = View.GONE
        }

        super.onResume()
    }
}