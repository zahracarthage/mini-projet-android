package com.example.mini_projet.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User  (
    @SerializedName("_id") val id : String,
    @SerializedName("username") val username : String,
    @SerializedName("password") val password : String,
    @SerializedName("picture") val picture : String,
    @SerializedName("email") val email : String,
    @SerializedName("followers") val followers : MutableList<String>,
    @SerializedName("following") val following : MutableList<String>,
    @SerializedName("posts") val posts : MutableList<String>,
        ) :  Serializable