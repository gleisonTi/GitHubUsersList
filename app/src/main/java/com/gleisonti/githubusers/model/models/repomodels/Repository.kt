package com.gleisonti.githubusers.model.models.repomodels

data class Repository(
    val id: Int = 0,
    val name: String? = "",
    val language: String? = "",
    val stargazersCount: Int? = 0,
    val forksCount: Int? = 0
)
