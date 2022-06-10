package com.example.anonymousboard.model

data class Posts (
    val id: Int,
    val title: String,
    val subject: String,
    val boardPw: String,
    val views: Int,
    val created: String
)

//data class Post (
//    val id: Int,
//    val title: String,
//    val subject: String,
//    val boardPw: String,
//    val views: Int,
//    val created: String
//)

data class RegistModel (
    val title: String,
    val subject: String,
    val boardPw: String,
)