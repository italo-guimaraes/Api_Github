package com.example.api_github.domain.repository

import com.example.api_github.domain.BaseResult
import com.example.api_github.domain.model.User
import com.example.api_github.domain.model.UserDetail

interface GithubRepository {
    suspend fun getUsers() : BaseResult<List<User>>
    suspend fun searchUsers(query: String) : BaseResult<List<User>>
    suspend fun getUserDetail(login: String) : BaseResult<UserDetail>
}