package com.example.anonymousboard.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JsServer {
    companion object{
        var inputIP = "10.30.3.60" // 입력 값 전환 예정
        var url = "http://${inputIP}:3030"
        private var server: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var postsApi: PostsApi = server.create(PostsApi::class.java)
    }
}