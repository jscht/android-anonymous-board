package com.example.anonymousboard.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JsServer {
    companion object {
        var url = "http://10.0.2.2:3030"
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        private var server: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        var postsApi: PostsApi = server.create(PostsApi::class.java)
    }
}