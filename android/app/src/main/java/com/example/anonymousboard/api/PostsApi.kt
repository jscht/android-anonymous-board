package com.example.anonymousboard.api

import androidx.lifecycle.LiveData
import com.example.anonymousboard.model.DeleteModel
import com.example.anonymousboard.model.Posts
import com.example.anonymousboard.model.RegistModel
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
    fun registPost(@Body registData: RegistModel): Call<Posts>

    @PUT("/revise")
    fun revisePost(@Body reviseData: Posts): Call<String>

    @DELETE("/delete") // 그냥 보내야지
    fun deletePost(@Body id: LiveData<Int>, password: String): Call<String>
}