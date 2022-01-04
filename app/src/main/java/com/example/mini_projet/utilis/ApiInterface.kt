package com.example.mini_projet.utilis


import com.example.mini_projet.models.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {
    @GET("/restos/findAllResto")
    suspend fun getRestaurants(): Response<ArrayList<Restaurant>>

    @GET("/restos/categories")
    suspend fun getCategories(): Response<ArrayList<String>>

    @GET("/restos/{id}")
    suspend  fun findRestobyId(@Path("id") id: String?): Response<Restaurant?>?

    @POST("/users/login")
    fun login(@Body map : HashMap<String, String> ): Call<User>

    @GET("/users/retriveuser/{id}")
    fun retriveuser(@Path("id") id: String?): Call<User>

    @GET("/users/findAll")
    fun getAll(): Call<MutableList<User>>

    @GET("/posts/getById/{id}")
    fun getById(@Path("id") id: String?): Call<Posts>

    @GET("/users/getwithuser/{id}")
    fun getwithuser(@Path("id") id: String?): Call<MutableList<User>>

    @GET ("/users/retrivepost/{id}")
    fun retrivepost(@Path("id") id: String?): Call<MutableList<User>>

    @GET ("/users/retrivepostconnected/{id}")
    fun retrivepostconnected(@Path("id") id: String?): Call<MutableList<Posts>>

    @POST("/users/findByName")
    fun getByName(@Body map : HashMap<String, String>): Call<MutableList<User>>

    @POST ("/users/followunfollow")
    fun followunfollow(@Body map : HashMap<String, String> ): Call<String>

    @POST("/users/signup")
    fun signup(@Body map : HashMap<String, String> ): Call<User>

    @GET("/users/getfollowers/{id}")
    fun getNbFollows(@Path("id") id: String?): Call<MutableList<User>>

    @GET("/users/getfollowing/{id}")
    fun getfollowing(@Path("id") id: String?): Call<MutableList<User>>

    @PATCH("/users/update/{id}")
    fun update(@Path("id") id: String?,@Body map : HashMap<String, String> ): Call<User>

    @PATCH("/users/addpost/{id}/{message}")
    fun addpost(@Path("id") id: String?,@Path("message") message: String?): Call<User>

    @PATCH("/users/updatefollowers/{id}/{follow}")
    fun follow(@Path("id") id: String?,@Path("follow") follow: String?): Call<User>

    @PATCH("/users/removefollowers/{id}/{follow}")
    fun removefollowers(@Path("id") id: String?,@Path("follow") follow: String?,@Body map : HashMap<String, String> ): Call<User>

    @POST("/users/SendMail/{id}")
    fun sendmail(@Path("id") id: String?,@Body map : HashMap<String, String> ): Call<String>

    @PATCH("/users/VerificationEmail/{id}")
    fun VerificationEmail(@Path("id") id: String?,@Body map : HashMap<String, String> ): Call<User>

    @GET("/restos/findAllResto")
    fun findAllResto() : Call<MutableList<Restaurant>>

    @PATCH("/restos/addreservation/{iduser}/{idresto}")
    fun addreservation(@Path("iduser") iduser: String?,@Path("idresto") idresto: String?,@Body map : HashMap<String, String>) : Call<Restaurant>

    @GET("/reservation/findRes/{id}")
    fun findRes(@Path("id") id: String?) : Call<MutableList<Reservations>>

    @POST("/calendry/filter")
    fun filter(@Body map : HashMap<String, String>) : Call<String>


    companion object {
        //var BASE_URL = "http://192.168.1.16:5000"
       // var BASE_URL = "http://192.168.1.6:5000"
        var BASE_URL = "https://iyum.herokuapp.com"
        //var BASE_URL = "http://172.17.13.29:5000"
        fun create() : ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }

}