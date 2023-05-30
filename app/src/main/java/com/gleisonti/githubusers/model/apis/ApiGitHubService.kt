package com.gleisonti.githubusers.model.apis

import com.gleisonti.githubusers.helper.Constants
import com.gleisonti.githubusers.model.models.repomodels.RepositoryListResponse
import com.gleisonti.githubusers.model.models.usermodels.SearchUserResponse
import com.gleisonti.githubusers.model.models.usermodels.UserListResponse
import com.gleisonti.githubusers.model.models.usermodels.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiGitHubService {

    @GET(Constants.END_POINT_SEARCH_USERS)
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): SearchUserResponse

    @GET(Constants.END_POINT_USERS)
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int,
    ): Response<UserListResponse>

    @GET(Constants.END_POINT_USER_DETAIL)
    suspend fun getUserDetails(
        @Path("username") userName: String,
    ): Response<UserResponse>

    @GET(Constants.END_POINT_USER_REPOS)
    suspend fun getUserRepository(
        @Path("username") userName: String,
    ): Response<RepositoryListResponse>

}