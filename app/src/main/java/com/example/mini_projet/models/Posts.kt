package com.example.mini_projet.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Posts (
    @SerializedName("_id") val id : String,
    @SerializedName("message") val message : String,
    @SerializedName("usersimage") val usersimage : String,
    @SerializedName("createdAt") val createdAt : Date,
        ): Serializable