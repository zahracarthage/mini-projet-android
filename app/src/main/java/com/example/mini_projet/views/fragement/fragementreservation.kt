package com.example.mini_projet.views.fragement

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mini_projet.R
import com.example.mini_projet.models.Reservations
import com.example.mini_projet.utilis.ApiInterface
import com.example.mini_projet.views.ID
import com.example.mini_projet.views.PREF_NAME
import com.example.mini_projet.views.adapter.ReservationAdapter
import kotlinx.android.synthetic.main.fragementreservation.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
private lateinit var mSharedPref: SharedPreferences
class fragementreservation : Fragment(R.layout.fragementreservation) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var rootView: View = inflater.inflate(R.layout.fragementreservation, container, false)
        return rootView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        progBarReserv.visibility = View.VISIBLE
        mSharedPref =  requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        val apiInterface = ApiInterface.create()
        apiInterface.findRes(mSharedPref.getString(ID, "").toString()).enqueue(object :
            Callback<MutableList<Reservations>> {

            override fun onResponse(call: Call<MutableList<Reservations>>, response:
            Response<MutableList<Reservations>>
            ) {
                val reservs = response.body()

                Log.e("reservs : ",reservs.toString())
                if(!reservs.isNullOrEmpty())
                {
                    val adapter = ReservationAdapter(reservs)
                    rv_users_reservations.adapter = adapter
                    rv_users_reservations.apply {
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
                    }

                }
                else
                {
                    NoReservation.visibility = View.VISIBLE
                }
                progBarReserv.visibility = View.INVISIBLE
                requireActivity().window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

            override fun onFailure(call: Call<MutableList<Reservations>>, t: Throwable) {
                Toast.makeText(context, "Connexion error!", Toast.LENGTH_SHORT).show()
                progBarReserv.visibility = View.INVISIBLE
                requireActivity().window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
        })


        super.onViewCreated(view, savedInstanceState)
    }

}