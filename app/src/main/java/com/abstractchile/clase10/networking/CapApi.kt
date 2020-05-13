package com.abstractchile.clase10.networking

import com.abstractchile.clase10.model.Cat
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*


interface CatApi {
    @GET("breeds")
    fun getCats(@Header("Authorization") key: String?): Call<List<Cat>>

    @GET("images/search")
    fun getImage(@Header("Authorization") key: String?,@Query("breed_id") breedId: String? ): Call<JsonArray>

    @POST("votes")
    @Headers("Content-Type: application/json")
    fun postVote(@Body body: String,
                 @Header("Authorization") key: String?
    ): Call<JsonObject>

}

