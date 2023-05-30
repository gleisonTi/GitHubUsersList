package com.gleisonti.githubusers.domain.paging.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gleisonti.githubusers.helper.ApiResponse
import com.gleisonti.githubusers.model.models.repomodels.Repository
import com.gleisonti.githubusers.model.models.usermodels.UserDetail
import com.gleisonti.githubusers.model.repository.GitHubRepository
import com.gleisonti.githubusers.model.models.usermodels.UserItem

class RepositoryListPagingSource (
    private val gitHubRepository: GitHubRepository,
    private val userName: String
) : PagingSource<Int, Repository>() {
    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
        return try {
            val response = gitHubRepository.getUserRepository(userName)
            val data = mutableListOf<Repository>()

            if(response is ApiResponse.Success){
                data.addAll(response.data)
            }

            val prevKey = params.key?.let { data[it - 1].id }
            val nextKey = params.key?.plus(1)?.takeIf { it < data.size }?.let { data[it].id }
            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey)

        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}