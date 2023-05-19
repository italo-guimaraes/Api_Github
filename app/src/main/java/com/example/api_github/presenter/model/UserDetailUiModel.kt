package com.example.api_github.presenter.model

import com.example.api_github.domain.model.UserDetail

data class UserDetailUiModel(
    val avatar_url: String,
    val login: String,
    val repositories: List<UserRepositoryUiModel>
)

fun UserDetail.toUserDetailUiModel() = UserDetailUiModel(
    avatar_url = this.avatar_url,
    login = this.login,
    repositories = this.repositories.map { it.toUserRepositoryUiModel() }
)