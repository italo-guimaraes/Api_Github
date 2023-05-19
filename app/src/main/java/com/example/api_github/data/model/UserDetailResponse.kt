package com.example.api_github.data.model

import com.example.api_github.domain.model.UserDetail
import com.example.api_github.domain.model.UserRepository

data class UserDetailResponse(
    val avatar_url: String,
    val bio: Any,
    val blog: String,
    val company: String,
    val created_at: String,
    val email: Any,
    val events_url: String,
    val followers: Int,
    val followers_url: String,
    val following: Int,
    val following_url: String,
    val gists_url: String,
    val gravatar_id: String,
    val hireable: Any,
    val html_url: String,
    val id: Int,
    val location: String,
    val login: String,
    val name: String,
    val node_id: String,
    val organizations_url: String,
    val public_gists: Int,
    val public_repos: Int,
    val received_events_url: String,
    val repos_url: String,
    val site_admin: Boolean,
    val starred_url: String,
    val subscriptions_url: String,
    val twitter_username: String,
    val type: String,
    val updated_at: String,
    val url: String
)

fun UserDetailResponse.toUserDetail() = UserDetail(
    id = this.id,
    node_id = this.node_id,
    avatar_url = this.avatar_url,
    html_url = this.html_url,
    repositories = arrayListOf(),
    login = this.login
)

fun UserDetail.addRepositories(repositories: List<UserRepository>) {
    this.repositories.addAll(repositories)
}