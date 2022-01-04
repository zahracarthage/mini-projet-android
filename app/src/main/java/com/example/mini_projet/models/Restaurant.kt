package com.example.mini_projet.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
@Parcelize
data class Restaurant (
    @SerializedName("_id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("picture") var picture : String,
    @SerializedName("about") var about : String,
    @SerializedName("nbplaces") var nbplaces : MutableList<String>,
    @SerializedName("reservation") val reservation : MutableList<String>,
    @SerializedName("category")
    var category: List<String>? = null,
    @SerializedName("Menu")
    var ListMenu: ArrayList<Menu> = ArrayList(),
    @SerializedName("localisation")
    var RestaurantLocalisation: ArrayList<Localisation> = ArrayList(),


    ) : Serializable, Parcelable {

    @Parcelize
    data class Menu(
        @SerializedName("_id")
        var menuId: String? = null,
        @SerializedName("menuName")
        var menuName: String? = null,
        @SerializedName("menuType")
        var menuType: String? = null,
        @SerializedName("menuDescription")
        var menuDescription: String? = null,
        @SerializedName("menuImg")
        val menuImg: String? = null

    ) : Parcelable


    @Parcelize
    data class Localisation(
        @SerializedName("_id")
        var localisationId: String? = null,
        @SerializedName("Longitude")
        var Longititude: Double,
        @SerializedName("Latitude")
        var Latitude: Double,
        @SerializedName("zoomIndex")
        var zoomIndex: Double
    ) : Parcelable

}

@Parcelize
data class Menus(val list: List<Restaurant.Menu>) : Parcelable


@Parcelize
data class RestaurantList(val list: ArrayList<Restaurant>) : Parcelable

@Parcelize
data class LocalizationList(val list: ArrayList<Restaurant.Localisation>) : Parcelable







