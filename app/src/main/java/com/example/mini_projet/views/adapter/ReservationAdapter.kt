package com.example.mini_projet.views.adapter

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mini_projet.R
import com.example.mini_projet.models.Reservations
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.code_reservation.view.*
import kotlinx.android.synthetic.main.reservationfrag.view.*

class ReservationAdapter (var reservations: MutableList<Reservations>) :

    RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {
    inner class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.reservationfrag, parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        var filename : String
        holder.itemView.apply {

            filename = reservations[position].picture
            val path = "https://firebasestorage.googleapis.com/v0/b/mini-projet-2e934.appspot.com/o/images%2F"+filename+"?alt=media"
            Glide.with(this)
                .load(path)
                .into(resto_reserv_img)
            var d = reservations[position].createdAt.time
            val now = System.currentTimeMillis()
            users_reservation_desc.setText("Number of places : "+reservations[position].place+" \n"+"Date : "+reservations[position].dateres+"\n"+ DateUtils.getRelativeTimeSpanString(d,now, DateUtils.SECOND_IN_MILLIS))
            reservationshow.setOnClickListener {
                val encoder = BarcodeEncoder()
                val bitmap = encoder.encodeBitmap(reservations[position].code, BarcodeFormat.QR_CODE, 900, 900)
                val builder = AlertDialog.Builder(holder.itemView.context)
                builder.setPositiveButton(
                    "Save This Qr Code"
                ) { dialog, which ->
                }
                val dialog = builder.create()
                val dialogLayout: View = LayoutInflater.from(holder.itemView.context).inflate(R.layout.code_reservation, null)
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
        }
        holder.itemView.setOnClickListener{
            // holder.itemView.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return reservations.size
    }
}