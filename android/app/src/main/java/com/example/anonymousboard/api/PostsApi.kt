package com.example.anonymousboard.api

import com.example.anonymousboard.model.*
import retrofit2.Call
import retrofit2.http.*

interface PostsApi {
    @GET("/board")
    fun getPosts(
        @Query("keyword") keyword: String?,
        @Query("order") order: String?
    ): Call<List<Posts>>

    @GET("/board/{id}")
    fun getPost(@Path("id")id: Int): Call<Posts>

    @POST("/regist")
    fun registPost(@Body registData: RegistModel): Call<ResultMessage>

    @GET("/revise/{postId}")
    fun revisePostInfo(@Path("postId") id: String): Call<ReviseInfoModel>

    @PUT("/revise")
    fun revisePost(@Body reviseData: ReviseModel): Call<ResultMessage>

    @DELETE("/delete")
    fun deletePost(
        @Query("id") id: String,
        @Query("password") password: String
    ): Call<ResultMessage>
}