package com.example.api_github.data.api

import com.example.api_github.data.model.SearchResponse
import com.example.api_github.data.model.UserDetailResponse
import com.example.api_github.data.model.UserRepositoryResponse
import com.example.api_github.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("/users")
    suspend fun getUsers(): Response<List<UserResponse>>

    @GET("/search/users")
    suspend fun getSearchUsers(
        @Query("q") q: String
    ): Response<SearchResponse>

    @GET("/users/{login}")
    suspend fun getUserDetail(
        @Path("login") login: String
    ): Response<UserDetailResponse>

    @GET("/users/{login}/repos")
    suspend fun getUserRepositories(
        @Path("login") login: String
    ): Response<List<UserRepositoryResponse>>
}