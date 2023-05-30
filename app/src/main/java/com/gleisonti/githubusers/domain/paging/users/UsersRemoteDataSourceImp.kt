package com.gleisonti.githubusers.domain.paging.users

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gleisonti.githubusers.model.models.usermodels.UserSearchItem
import com.gleisonti.githubusers.model.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersRemoteDataSourceImpl @Inject constructor(
    private val gitHubRepository: GitHubRepository
): IUsersRemoteDataSource {
    override fun getUsers(query: String): Flow<PagingData<UserSearchItem>> {
        return Pager(PagingConfig(pageSize = 20)) {
            UserSearchPagingSource(gitHubRepository, query)
        }.flow
    }
}