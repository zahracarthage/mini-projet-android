package com.example.mini_projet.views

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.example.mini_projet.R
import com.example.mini_projet.models.User
import com.example.mini_projet.utilis.ApiInterface
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.edit_profil.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class EditProfil : AppCompatActivity() {
    lateinit var UsernameShow: TextInputEditText
    lateinit var PasswordShow : TextInputEditText
    private  var btnupload : Button? = null
    private  var btnModifier : Button? = null
    private lateinit var mSharedPref: SharedPreferences
    private var profilePic: ImageView? = null
    private var selectedImageUri: Uri? = null
    private var taptoopen : TextView? = null
    protected val inputdone = 0
    lateinit var storage: FirebaseStorage
    val formater = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
    val now = Date()
    val fileName = formater.format(now)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profil)
        val toolbar: Toolbar = findViewById(R.id.app_bar)

        toolbar.setTitle("")
        var DesignImage = findViewById<CardView>(R.id.DesignImage)
        DesignImage.visibility = View.INVISIBLE
        toolbar.setNavigationIcon(R.drawable.ic_retour)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        //fireBase
        storage = Firebase.storage
        btnupload = findViewById(R.id.upload)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        UsernameShow = findViewById(R.id.UsernameShow)
        PasswordShow = findViewById(R.id.PasswordShow)
        profilePic = findViewById(R.id.profilePic)
        if(mSharedPref.getString(PICTURE, "").toString()=="No Picture")
        {
            profilePic!!.setImageResource(R.drawable.avatar)
        }
        else
        {
            val filename2 = mSharedPref.getString(PICTURE, "").toString()
            val path = "https://firebasestorage.googleapis.com/v0/b/mini-projet-2e934.appspot.com/o/images%2F"+filename2+"?alt=media"
            Glide.with(this)
                .load(path)
                .into(profilePic)
        }
        btnModifier = findViewById(R.id.Modifier)
        UsernameShow.setText(mSharedPref.getString(USERNAME, "").toString())
        PasswordShow.setText(mSharedPref.getString(PASSWORD, "").toString())





        profilePic!!.setOnClickListener {
            openGallery()
        }

        btnupload!!.setOnClickListener {
            uploadImage()
        }
        btnModifier!!.setOnClickListener {
            if(doUpdate())
            {
                val apiInterface = ApiInterface.create()
                val map: HashMap<String, String> = HashMap()
                map["username"] = UsernameShow.text.toString()
                map["password"] = PasswordShow.text.toString()
                if (selectedImageUri == null) {
                    map["picture"] = mSharedPref.getString(PICTURE, "").toString()
                }
                else
                    map["picture"] = fileName.toString()
                apiInterface.update(mSharedPref.getString(ID, "").toString(),map).enqueue(object :
                    Callback<User> {

                    override fun onResponse(call: Call<User>, response:
                    Response<User>
                    ) {
                        val user = response.body()
                        if(user !=null)
                        {
                            mSharedPref.edit().apply{
                                putString(ID,user.id)
                                putString(USERNAME, user.username)
                                putString(PASSWORD, user.password)
                                putString(EMAIL, "No Email")
                                putString(PICTURE, user.picture)
                            }.apply()
                            val intent = Intent(this@EditProfil, Accueil::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else
                        {
                            Toast.makeText(this@EditProfil,"error!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(this@EditProfil,"Connexion error!", Toast.LENGTH_SHORT).show()
                    }
                })
            }

        }

    }
    private fun doUpdate(): Boolean
    {
        UsernameTextLayout.error = null
        PasswordTextLayout.error = null
        if(UsernameShow.text!!.isEmpty())
        {
            UsernameTextLayout.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        if(PasswordShow.text!!.isEmpty())
        {
            PasswordTextLayout.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        return true
    }
    private fun uploadImage()
    {
        if (selectedImageUri == null) {
            Toast.makeText(this@EditProfil,"Please Select Picture", Toast.LENGTH_SHORT).show()
        }
        else
        {
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Uploading Image ...")
            progressDialog.setCancelable(false)
            progressDialog.show()
            val storageReference = FirebaseStorage.getInstance().reference.child("images/$fileName")
            storageReference.putFile(selectedImageUri!!).
            addOnSuccessListener {
                profilePic!!.setImageURI(selectedImageUri)
                if(progressDialog.isShowing)
                {
                    progressDialog.dismiss()
                }
                Toast.makeText(this,"Successfuly uploaded", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                if(progressDialog.isShowing)
                {
                    progressDialog.dismiss()
                }
                Toast.makeText(this,"Sorry", Toast.LENGTH_SHORT).show()

            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == RESULT_OK)
        {
            selectedImageUri = data?.data!!
            profilePic!!.setImageURI(selectedImageUri)
        }
    }
    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,100)
    }

}