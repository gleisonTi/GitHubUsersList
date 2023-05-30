package com.gleisonti.githubusers.domain.paging.users

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gleisonti.githubusers.helper.ApiResponse
import com.gleisonti.githubusers.model.models.usermodels.UserSearchItem
import com.gleisonti.githubusers.model.repository.GitHubRepository

class UserSearchPagingSource(
    private val gitHubRepository: GitHubRepository,
    private val query: String
) : PagingSource<Int, UserSearchItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserSearchItem> {
        val page = params.key ?: 1

        val response = if (query.isEmpty())
            gitHubRepository.getUserList(page,params.loadSize)
        else
            gitHubRepository.searchUser(query, page, params.loadSize)

        return if (response is ApiResponse.Success) {
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (response.data.items.isEmpty()) null else page + 1

            LoadResult.Page(
                data = response.data.items,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } else {
            LoadResult.Error(Throwable("Houve Um Problema ao Carregar"))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserSearchItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}