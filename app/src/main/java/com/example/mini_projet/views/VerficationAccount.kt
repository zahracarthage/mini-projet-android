package com.example.mini_projet.views

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mini_projet.R
import com.example.mini_projet.models.User
import com.example.mini_projet.utilis.ApiInterface
import com.example.mini_projet.views.adapter.FollowingAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_following.*
import kotlinx.android.synthetic.main.activity_verfication_account.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerficationAccount : AppCompatActivity() {
    private lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verfication_account)
        imgretour.setOnClickListener {
            finish()
        }
        val random : String
        random = getRandomString(10)
        Log.e("random : ",random)
        val apiInterface = ApiInterface.create()
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        txtLayoutCode.visibility = View.GONE
        txtCode.visibility = View.GONE
        govalid.visibility = View.GONE
        sendmail.setOnClickListener {
            if (validate()) {
                sendmailer(random)
                val timer = object: CountDownTimer(60000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {

                        sendmail.setText("Resend in : " + millisUntilFinished / 1000)
                        sendmail.setClickable(false)
                        txtLayoutCode.visibility = View.VISIBLE
                        txtCode.visibility = View.VISIBLE
                        govalid.visibility = View.VISIBLE
                    }
                    override fun onFinish() {
                        sendmail.setText("Resend")
                        sendmail.setClickable(true)
                        txtLayoutCode.visibility = View.GONE
                        txtCode.visibility = View.GONE
                        govalid.visibility = View.GONE
                    }
                }
                timer.start()
            }
        }
        govalid.setOnClickListener {


            if(qrcode(random))
            {
                val map: HashMap<String, String> = HashMap()
                map["email"] = txtEmail.text.toString()
                CoroutineScope(Dispatchers.IO).launch {

                    apiInterface.VerificationEmail(mSharedPref.getString(ID, "").toString(), map)
                        .enqueue(object :
                            Callback<User> {
                            override fun onResponse(
                                call: Call<User>, response:
                                Response<User>
                            ) {
                                val text = response.body()
                                if (text != null) {
                                    mSharedPref.edit().apply {
                                        putString(EMAIL, text.email)
                                    }.apply()
                                    finish()
                                }
                            }

                            override fun onFailure(call: Call<User>, t: Throwable) {
                                Log.e("error", "error")
                            }
                        })
                }
            }


        }

    }

    fun sendmailer(codex : String) {
        val apiInterface = ApiInterface.create()
        val map: HashMap<String, String> = HashMap()
        map["email"] = txtEmail.text.toString()
        map["code"] = codex
        CoroutineScope(Dispatchers.IO).launch {

            apiInterface.sendmail(mSharedPref.getString(ID, "").toString(), map).enqueue(object :
                Callback<String> {
                override fun onResponse(
                    call: Call<String>, response:
                    Response<String>
                ) {
                    val text = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("error", "error")
                }
            })
        }
        }
    fun getRandomString(length: Int) : String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        return List(length) { charset.random() }
            .joinToString("")
    }
    fun qrcode(codex : String) : Boolean {
        txtLayoutCode.error = null

        if (txtCode.text!!.isEmpty()) {
            txtLayoutCode!!.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if(txtCode.text.toString() != codex)
        {
            txtLayoutCode!!.error = getString(R.string.invalidcode)
            return false
        }
        return true
    }
    fun validate(): Boolean {
        txtLayoutEmail.error = null

        if (txtEmail.text!!.isEmpty()) {
            txtLayoutEmail!!.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail.text).matches()) {
            txtLayoutEmail.error = getString(R.string.checkYourEmail)
            return false
        }
        return true
    }
}