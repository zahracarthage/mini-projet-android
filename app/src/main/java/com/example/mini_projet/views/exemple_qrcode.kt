package com.example.mini_projet.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mini_projet.R
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_exemple_qrcode.*

class exemple_qrcode : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exemple_qrcode)
        val encoder = BarcodeEncoder()
        val bitmap = encoder.encodeBitmap("hi", BarcodeFormat.QR_CODE, 400, 400)
        kk1.setImageBitmap(bitmap)
    }
}