package com.example.api_github.domain.usecase

import com.example.api_github.domain.BaseResult
import com.example.api_github.domain.model.UserDetail
import com.example.api_github.domain.repository.GithubRepository
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(private val repository: GithubRepository) {

    suspend fun invoke(login: String): BaseResult<UserDetail> {
        return repository.getUserDetail(login)
    }

}