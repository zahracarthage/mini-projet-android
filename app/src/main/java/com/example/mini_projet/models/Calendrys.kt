package com.example.mini_projet.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Calendrys (
    @SerializedName("_id") val id : String,
    @SerializedName("nbplaceforday") val nbplaceforday : Int,
    @SerializedName("dateforday") var dateforday : String,
    @SerializedName("idresto") var idresto : String,
) : Serializable
