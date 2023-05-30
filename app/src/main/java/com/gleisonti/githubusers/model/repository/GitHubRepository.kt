package com.gleisonti.githubusers.model.repository

import com.gleisonti.githubusers.helper.ApiResponse
import com.gleisonti.githubusers.model.apis.ApiGitHubService
import com.gleisonti.githubusers.model.models.repomodels.Repository
import com.gleisonti.githubusers.model.models.usermodels.SearchUserResponse
import com.gleisonti.githubusers.model.models.usermodels.UserDetail
import com.gleisonti.githubusers.model.models.usermodels.UserSearchItem
import javax.inject.Inject

class GitHubRepository @Inject
constructor(
    private val apiGitHubService: ApiGitHubService
) : IGitHubRepository {

    override suspend fun getUserDetails(userName: String): ApiResponse<UserDetail> {
        try {
            val response = apiGitHubService.getUserDetails(userName = userName)
            val userData = response.body()

            return ApiResponse.Success(
                UserDetail(
                    avatar_url = userData?.avatar_url,
                    company = userData?.company,
                    followers = userData?.followers,
                    location = userData?.company,
                    login = userData?.login,
                    name = userData?.name,
                    public_repos = userData?.public_repos,
                )
            )

        } catch (error: Exception) {
            return ApiResponse.Error(error.message.toString())
        }
    }


    override suspend fun getUserRepository(userName: String): ApiResponse<List<Repository>> {
        try {
            val response = apiGitHubService.getUserRepository(userName = userName)
            val repositoryData = mutableListOf<Repository>()
            val data = response.body()?.map {
                Repository(
                    id = it.id,
                    name = it.name,
                    language = it.language,
                    stargazersCount = it.stargazers_count,
                    forksCount = it.forks_count
                )
            }
            repositoryData.addAll(data as Collection<Repository>)
            return ApiResponse.Success(
                repositoryData
            )
        } catch (error: Exception) {
            return ApiResponse.Error(error.message.toString())
        }
    }

    override suspend fun getUserList(page: Int, perPage: Int): ApiResponse<SearchUserResponse> {
        return try {
            val response = apiGitHubService.getUsers(page,perPage)
            val data = response.body() as List<UserSearchItem>
            ApiResponse.Success(
                SearchUserResponse(
                    incomplete_results = false,
                    items = data,
                    total_count = data.size
                )
            )
        } catch (error: Exception) {
            ApiResponse.Error(error.message.toString())
        }
    }


    override suspend fun searchUser(
        query: String,
        page: Int,
        perPage: Int
    ): ApiResponse<SearchUserResponse> {
        return try {
            val response = apiGitHubService.searchUsers(query, page, perPage)
            ApiResponse.Success(
                response
            )
        } catch (error: Exception) {
            ApiResponse.Error(error.message.toString())
        }
    }

}


