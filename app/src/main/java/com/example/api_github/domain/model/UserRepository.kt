package com.example.api_github.domain.model

data class UserRepository(
    val id: Int,
    val node_id: String,
    val html_url: String,
    val name: String,
    val description: String?,
    val language: String?,
)
