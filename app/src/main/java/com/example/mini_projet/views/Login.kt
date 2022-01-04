package com.example.mini_projet.views

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.mini_projet.R
import com.example.mini_projet.models.User
import com.example.mini_projet.utilis.ApiInterface
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
const val PREF_NAME = "DATA_PREF"
const val USERNAME = "USERNAME"
const val PASSWORD = "PASSWORD"
const val EMAIL = "EMAIL"
const val PICTURE = "PICTURE"
const val FOLLOWERS = "FOLLOWERS"
const val FOLLOWERSARRAY = "FOLLOWERSARRAY"
const val ID = "ID"
const val IS_REMEMBRED = "IS_REMEMBRED"
const val NBPOST = "POST"
const val FOLLOWING = "FOLLOWING"
class Login : AppCompatActivity() {
    lateinit var usernmane: TextInputEditText
    lateinit var txtLayoutLogin: TextInputLayout

    lateinit var pwd: TextInputEditText
    lateinit var txtLayoutPassword: TextInputLayout
    private lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        val btnsign = findViewById<Button>(R.id.btnsign)
         val btnlogin = findViewById<Button>(R.id.btnlogin)
         usernmane = findViewById(R.id.txtLogin)
         txtLayoutLogin = findViewById(R.id.txtLayoutLogin)
         pwd = findViewById(R.id.txtPassword)
        txtLayoutPassword = findViewById(R.id.txtLayoutPassword)
        btnlogin.setOnClickListener {
            doLogin()
        }
        btnsign.setOnClickListener{
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

    }
    private fun doLogin(){
        if (validate()){

            val apiInterface = ApiInterface.create()
            val map: HashMap<String, String> = HashMap()

            map["username"] = usernmane.text.toString()
            map["password"] = pwd.text.toString()
            CoroutineScope(Dispatchers.IO).launch {

            apiInterface.login(map).enqueue(object : Callback<User>{

                override fun onResponse(call: Call<User>, response:
                Response<User>
                ) {
                    val user = response.body()
                    Log.e("user : ", user.toString())
                    if(user != null)
                    {
                        Log.e("user : ", user.toString())
                                mSharedPref.edit().apply{
                                    putString(ID,user.id)
                                    putString(USERNAME, user.username)
                                    putString(PASSWORD, user.password)
                                    putString(EMAIL, user.email)
                                    putString(PICTURE, user.picture)
                                    putString(FOLLOWERS,user.followers.size.toString())
                                    putString(NBPOST,user.posts.size.toString())
                                    putString(FOLLOWING,user.following.size.toString())
                                    //putStringSet(FOLLOWERSARRAY,user.followers)
                                    putBoolean(IS_REMEMBRED, false)
                                }.apply()
                                val intent = Intent(this@Login, Accueil::class.java)
                                startActivity(intent)
                                finish()
                    }

                    else
                    {
                        Toast.makeText(this@Login,"Username or Password wrong !!",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@Login,"Connexion error!",Toast.LENGTH_SHORT).show()
                }
            })
            }
        }
    }
    private fun validate(): Boolean {
        txtLayoutLogin.error = null
        txtLayoutPassword.error = null

        if (usernmane.text!!.isEmpty()){
            txtLayoutLogin.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        if (pwd.text!!.isEmpty()){
            txtLayoutPassword.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        return true
    }
}