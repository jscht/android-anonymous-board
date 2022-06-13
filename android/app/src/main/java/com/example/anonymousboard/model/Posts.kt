package com.example.anonymousboard.model

data class Posts (
    val id: Int,
    val title: String,
    val subject: String,
    val boardPw: String,
    val views: Int,
    val created: String
)

data class RegistModel (
    val title: String,
    val subject: String,
    val boardPw: String,
)

data class ReviseInfoModel (
    val id: Int,
    val title: String,
    val subject: String,
)

data class ReviseModel (
    val id: String,
    val title: String,
    val subject: String,
    val boardPw: String,
)

data class ResultMessage (
    val msg: String,
    val reqState: Boolean
)