package com.example.mini_projet.views

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.mini_projet.R
import com.example.mini_projet.models.Restaurant
import com.example.mini_projet.utilis.ApiInterface
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_reserve.*
import kotlinx.android.synthetic.main.code_reservation.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.HashMap

class Reserve : AppCompatActivity() {
    private lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve)
        retourres.setOnClickListener {
            finish()
        }
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        val calendar = Calendar.getInstance()

        val date1 = calendar.getTime().time
        val DatePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select start date")
            .build()

        DatePicker.addOnPositiveButtonClickListener {
            ti_Editdate.setText(DatePicker.headerText.toString())

            filter(ti_Editdate.text.toString(),intent.getStringExtra("id").toString())
            val date2 : Long? = DatePicker.selection
            if(date2!!.compareTo(date1) < 1)
            {
                ti_Editdate.text!!.clear()
                ti_Editdate.clearFocus()
                EditdateContainer.error = getString(R.string.noreserv)
                reservenow.isEnabled = false
            }
            else
                reservenow.isEnabled = true
        }
        ti_Editdate.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus){
                DatePicker.show(supportFragmentManager, "START_DATE")
            }else{
                DatePicker.dismiss()
            }
        }



        var filename : String
        filename = intent.getStringExtra("picture").toString()
        val path = "https://firebasestorage.googleapis.com/v0/b/mini-projet-2e934.appspot.com/o/images%2F"+filename+"?alt=media"
        Glide.with(this)
            .load(path)
            .into(restoreserv_img)
        restonamereserv.setText(intent.getStringExtra("name").toString())


        reservenow.setOnClickListener {

            if(validate())
            {
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
                val text : String
                text = getRandomString(10)
                Log.e("random : ",text)
                val apiInterface = ApiInterface.create()
                val map: HashMap<String, String> = HashMap()

                map["place"] = nbplaces.text.toString()
                map["code"] = text
                map["dateres"] = ti_Editdate.text.toString()
                apiInterface.addreservation(intent.getStringExtra("id").toString(),mSharedPref.getString(ID, "").toString(),map).enqueue(object : Callback<Restaurant> {

                    override fun onResponse(call: Call<Restaurant>, response:
                    Response<Restaurant>
                    ) {
                        val resto = response.body()
                        Log.e("user : ", resto.toString())
                        if(resto != null)
                        {
                            val encoder = BarcodeEncoder()
                            val bitmap = encoder.encodeBitmap(text, BarcodeFormat.QR_CODE, 900, 900)
                            val builder = AlertDialog.Builder(this@Reserve)
                            builder.setPositiveButton(
                                "Thanks"
                            ) { dialog, which ->
                                val intent = Intent(this@Reserve,Accueil::class.java)
                                startActivity(intent)
                            }
                            val dialog = builder.create()
                            val inflater = layoutInflater
                            val dialogLayout: View = inflater.inflate(R.layout.code_reservation, null)
                            dialog.setView(dialogLayout)
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialogLayout.dialog_imageview.setImageBitmap(bitmap)
                            dialog.show()

                            dialog.setOnShowListener {

                                val image =
                                    dialog.findViewById<View>(R.id.dialog_imageview) as ImageView
                                image.setImageBitmap(bitmap)
                                val imageWidthInPX = image.width.toFloat()
                                val layoutParams = LinearLayout.LayoutParams(
                                    Math.round(imageWidthInPX),
                                    Math.round(imageWidthInPX)
                                )
                                image.layoutParams = layoutParams
                            }
                        }

                        else
                        {
                        }
                    }

                    override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                        val encoder = BarcodeEncoder()
                        val bitmap = encoder.encodeBitmap(text, BarcodeFormat.QR_CODE, 900, 900)
                        val builder = AlertDialog.Builder(this@Reserve)
                        builder.setPositiveButton(
                            "Thanks"
                        ) { dialog, which ->

                            val intent = Intent(this@Reserve,Accueil::class.java)
                            startActivity(intent)
                        }
                        val dialog = builder.create()
                        val inflater = layoutInflater
                        val dialogLayout: View = inflater.inflate(R.layout.code_reservation, null)
                        dialog.setView(dialogLayout)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialogLayout.dialog_imageview.setImageBitmap(bitmap)
                        dialog.show()

                        dialog.setOnShowListener {

                            val image =
                                dialog.findViewById<View>(R.id.dialog_imageview) as ImageView
                            image.setImageBitmap(bitmap)
                            val imageWidthInPX = image.width.toFloat()
                            val layoutParams = LinearLayout.LayoutParams(
                                Math.round(imageWidthInPX),
                                Math.round(imageWidthInPX)
                            )
                            image.layoutParams = layoutParams
                        }
                    }
                })
            }

        }
    }

    fun filter(dateres : String,idrestt : String)  {

        val map: HashMap<String, String> = HashMap()

        map["dateres"] = dateres
        map["idrestt"] = idrestt
        val apiInterface = ApiInterface.create()
        apiInterface.filter(map).enqueue(object :
            Callback<String> {

            override fun onResponse(call: Call<String>, response:
            Response<String>
            ) {
                val user = response.body()
                Log.e("user : ", user.toString())
                if(user != null)
                {
                   if(user.toString()=="false")
                   {
                       reservecomplet.visibility = View.VISIBLE
                       reservenow. visibility = View.GONE
                       Toast.makeText(this@Reserve," No places left at this day!", Toast.LENGTH_SHORT).show()
                       ti_Editdate.clearFocus()
                   }
                    else
                   {
                       reservecomplet.visibility = View.GONE
                       reservenow. visibility = View.VISIBLE
                   }


                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })


    }
    private fun validate(): Boolean {
        EditdateContainer.error = null
        val place: String = nbplaces.getText().toString()
        if (place.isEmpty()){
            Toast.makeText(this@Reserve," Please choice number of places!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (ti_Editdate.text!!.isEmpty()){
            EditdateContainer.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        return true
    }
    fun getRandomString(length: Int) : String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        return List(length) { charset.random() }
            .joinToString("")
    }
}

