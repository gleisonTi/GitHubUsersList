package com.gleisonti.githubusers.model.models.usermodels

data class SearchUserResponse(
    val incomplete_results: Boolean,
    val items: List<UserSearchItem>,
    val total_count: Int
)