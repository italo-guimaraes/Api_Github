package com.example.api_github.presenter.model

import com.example.api_github.domain.model.User

data class UserUiModel(
    val avatar_url: String,
    val login: String,
)

fun User.toUserUiModel() = UserUiModel(
    avatar_url = this.avatar_url,
    login = this.login
)