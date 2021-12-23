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
    @SerializedName("image")
    var image: String?=null,
    @SerializedName("adresse")
    var adresse: String?=null,
    @SerializedName("capacity")
    var capacity: String?=null,
    @SerializedName("category")
    var category: List<String>?=null,




) : Serializable




