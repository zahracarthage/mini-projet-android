package com.example.mini_projet.models


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Restaurant (
    @SerializedName("_id")
    var _id : String?=null,
    @SerializedName("title")
    var title : String?=null,
    @SerializedName("description")
    var description : String?=null,


) : Serializable



