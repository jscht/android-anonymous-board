package com.example.anonymousboard.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TaskServer {
    var inputIP: String = ""

    init {
        inputIP = "10.30.3.60"
    }

    fun setIP(ip: String) {
        inputIP = ip
    }
}

class JsServer {
    companion object {
         //var inputIP = "10.30.3.60" // 입력 값 전환 예정
        var ip: String = TaskServer().inputIP
        var url = "http://${ip}:3030"

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