package com.gleisonti.githubusers.model.models.usermodels

data class UserDetail(
    val avatar_url: String? = "https://avatars.githubusercontent.com/u/583231?v=4",
    val company: String? = "",
    val followers: Int? = 0,
    val location: String? = "",
    val login: String? = "user",
    val name: String? = "Teste de Usuario",
    val public_repos: Int? = 0,
)