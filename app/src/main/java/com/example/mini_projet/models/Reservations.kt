package com.example.mini_projet.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Reservations (
    @SerializedName("_id") val id : String,
    @SerializedName("place") val place : String,
    @SerializedName("code") val code : String,
    @SerializedName("picture") val picture : String,
    @SerializedName("dateres") val dateres : String,
    @SerializedName("user") val user : MutableList<String>,
    @SerializedName("resto") val resto : MutableList<String>,
    @SerializedName("createdAt") val createdAt : Date,
) : Serializable
