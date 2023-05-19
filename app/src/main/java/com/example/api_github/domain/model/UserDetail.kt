package com.example.api_github.domain.model

data class UserDetail(
    val id: Int,
    val node_id: String,
    val avatar_url: String,
    val html_url: String,
    val repositories: ArrayList<UserRepository>,
    val login: String,
)
