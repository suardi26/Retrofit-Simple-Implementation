package com.practice.retrofit_simple_implementation.api

import com.practice.retrofit_simple_implementation.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @GET("posts")
    fun getUsers(): Call<ArrayList<UserResponse>>

    @FormUrlEncoded
    @POST("posts")
    fun createUser(
        @Field("userId") userId: Int,
        @Field("title") title: String?,
        @Field("body") text: String?
    ): Call<UserResponse>

    @FormUrlEncoded
    @PUT("posts/{id}")
    fun putUser(
        @Path("id") id : Int,
        @Field("userId") userId: Int,
        @Field("title") title: String,
        @Field("body") text: String
    ) : Call<UserResponse>

    @DELETE("posts/{id}")
    fun deletePost(@Path("id") id: Int): Call<Unit>

}