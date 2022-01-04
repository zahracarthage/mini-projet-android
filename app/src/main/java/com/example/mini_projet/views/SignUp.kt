package com.example.mini_projet.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.mini_projet.R
import com.example.mini_projet.models.User
import com.example.mini_projet.utilis.ApiInterface
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUp : AppCompatActivity() {
    lateinit var txtLoginSign: TextInputEditText
    lateinit var txtLayoutLoginSign: TextInputLayout

    lateinit var txtPasswordSign: TextInputEditText
    lateinit var txtLayoutPasswordSign: TextInputLayout

    lateinit var btnlogin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)
        txtLoginSign = findViewById(R.id.txtLoginSign)
        txtPasswordSign =findViewById(R.id.txtPasswordSign)
        txtLayoutLoginSign=findViewById(R.id.txtLayoutLoginSign)
        txtLayoutPasswordSign=findViewById(R.id.txtLayoutPasswordSign)
        btnlogin = findViewById(R.id.btnregis)
        btnlogin.setOnClickListener {
            Register()
        }
    }
    private fun Register(){
        if (validate()){
            val apiInterface = ApiInterface.create()
            val map: HashMap<String, String> = HashMap()

            map["username"] = txtLoginSign.text.toString()
            map["password"] = txtPasswordSign.text.toString()
            apiInterface.signup(map).enqueue(object : Callback<User> {

                override fun onResponse(call: Call<User>, response: Response<User>) {

                    val user = response.body()
                    if (user != null){
                       val intent = Intent(this@SignUp, Login::class.java)
                        startActivity(intent)
                    }else{

                            txtLayoutLoginSign.error = getString(R.string.exsit)

                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@SignUp, "Connexion error!", Toast.LENGTH_SHORT).show()
                }

            })

        }
    }

    private fun validate(): Boolean {
        txtLayoutLoginSign.error = null
        txtLayoutPasswordSign.error = null

        if (txtLoginSign.text!!.isEmpty()){
            txtLayoutLoginSign.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if (txtLoginSign.text!!.length < 4 ){
            txtLayoutLoginSign.error = getString(R.string.Usernameslength)
            return false
        }
        if (txtPasswordSign.text!!.length < 4 ){
            txtLayoutPasswordSign.error = getString(R.string.passworderror)
            return false
        }
        if (txtPasswordSign.text!!.isEmpty()){
            txtLayoutPasswordSign.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        return true
    }
}