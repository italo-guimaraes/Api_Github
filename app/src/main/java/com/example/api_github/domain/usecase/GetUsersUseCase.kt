package com.example.api_github.domain.usecase

import com.example.api_github.domain.BaseResult
import com.example.api_github.domain.model.User
import com.example.api_github.domain.repository.GithubRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val repository: GithubRepository) {

    suspend fun invoke() : BaseResult<List<User>> {
        return repository.getUsers()
    }

}