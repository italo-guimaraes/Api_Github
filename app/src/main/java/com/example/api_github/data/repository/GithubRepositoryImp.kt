package com.example.api_github.data.repository

import com.example.api_github.data.api.GithubApi
import com.example.api_github.data.model.addRepositories
import com.example.api_github.data.model.toUser
import com.example.api_github.data.model.toUserDetail
import com.example.api_github.data.model.toUserRepository
import com.example.api_github.domain.BaseResult
import com.example.api_github.domain.model.User
import com.example.api_github.domain.model.UserDetail
import com.example.api_github.domain.parseResponse
import com.example.api_github.domain.repository.GithubRepository
import java.io.IOException
import javax.inject.Inject

class GithubRepositoryImp @Inject constructor(private val api: GithubApi) : GithubRepository {

    override suspend fun getUsers(): BaseResult<List<User>> {
        return try {
            when (val result = api.getUsers().parseResponse()) {
                is BaseResult.Success -> {
                    val users = result.value.map {
                        it.toUser()
                    }
                    BaseResult.Success(users)
                }

                is BaseResult.Failure -> {
                    BaseResult.Failure(result.statusCode)
                }
            }
        } catch (e: IOException) {
            BaseResult.Failure(404)
        }
    }

    override suspend fun searchUsers(query: String): BaseResult<List<User>> {
        return try {
            when (val result = api.getSearchUsers(query).parseResponse()) {
                is BaseResult.Success -> {
                    val users = result.value.items.map {
                        it.toUser()
                    }
                    BaseResult.Success(users)
                }

                is BaseResult.Failure -> {
                    BaseResult.Failure(result.statusCode)
                }
            }
        } catch (e: IOException) {
            BaseResult.Failure(404)
        }
    }

    override suspend fun getUserDetail(login: String): BaseResult<UserDetail> {
        return try {
            when (val result = api.getUserDetail(login).parseResponse()) {
                is BaseResult.Success -> {
                    val users = result.value.toUserDetail()
                    when (val userRepositoriesResult =
                        api.getUserRepositories(login).parseResponse()) {
                        is BaseResult.Failure -> {
                            BaseResult.Failure(userRepositoriesResult.statusCode)
                        }

                        is BaseResult.Success -> {
                            val repositories = userRepositoriesResult.value.map {
                                it.toUserRepository()
                            }
                            users.addRepositories(repositories)
                            BaseResult.Success(users)
                        }
                    }
                }

                is BaseResult.Failure -> BaseResult.Failure(result.statusCode)
            }
        } catch (e: IOException) {
            BaseResult.Failure(404)
        }
    }
}