package com.gleisonti.githubusers.model.repository

import com.gleisonti.githubusers.helper.ApiResponse
import com.gleisonti.githubusers.model.models.repomodels.Repository
import com.gleisonti.githubusers.model.models.usermodels.SearchUserResponse
import com.gleisonti.githubusers.model.models.usermodels.UserDetail
import com.gleisonti.githubusers.model.models.usermodels.UserListResponse
import retrofit2.Response

interface IGitHubRepository {
    suspend fun getUserList(page: Int, perPage: Int): ApiResponse<SearchUserResponse>
    suspend fun getUserDetails(userName: String): ApiResponse<UserDetail>
    suspend fun getUserRepository(userName: String): ApiResponse<List<Repository>?>
    suspend fun searchUser(query: String, page: Int, perPage: Int): ApiResponse<SearchUserResponse?>
}