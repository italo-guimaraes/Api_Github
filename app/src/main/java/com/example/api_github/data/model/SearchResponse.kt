package com.example.api_github.data.model

data class SearchResponse(
    val incomplete_results: Boolean,
    val items: List<UserResponse>,
    val total_count: Int
)