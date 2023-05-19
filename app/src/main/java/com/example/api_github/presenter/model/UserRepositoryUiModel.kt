package com.example.api_github.presenter.model

import com.example.api_github.domain.model.UserRepository

data class UserRepositoryUiModel(
    val name: String,
)

fun UserRepository.toUserRepositoryUiModel() = UserRepositoryUiModel(
    name = this.name
)
