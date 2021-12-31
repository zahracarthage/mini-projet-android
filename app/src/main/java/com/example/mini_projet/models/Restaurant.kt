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
    @SerializedName("Menu")
    var ListMenu: ArrayList<Menu> = ArrayList(),
    @SerializedName("localisation")
    var RestaurantLocalisation: ArrayList<Localisation> = ArrayList(),




) : Serializable
{

    data class Menu(
        @SerializedName("_id")
        var menuId: String? =null,
        @SerializedName("menuName")
        var menuName :String? = null,
        @SerializedName("menuType")
        var menuType : String? =null,
        @SerializedName("menuDescription")
        var menuDescription: String ? =null

    )
    data class Localisation(
        @SerializedName("_id")
        var localisationId: String?=null,
        @SerializedName("Longitude")
        var Longititude: Double,
        @SerializedName("Latitude")
        var Latitude: Double ,
        @SerializedName("zoomIndex")
        var zoomIndex: Double
    )

}







