package com.example.api_github.domain.model

data class User(
    val id: Int,
    val node_id: String,
    val avatar_url: String,
    val html_url: String,
    val login: String,
)