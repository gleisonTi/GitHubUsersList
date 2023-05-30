package com.gleisonti.githubusers.domain.paging.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gleisonti.githubusers.model.models.repomodels.Repository
import com.gleisonti.githubusers.model.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoriesRemoteDataSourceImpl @Inject constructor(
    private val gitHubRepository: GitHubRepository
): IRepositoryRemoteDataSource {
    override fun getRepositories(userName:String): Flow<PagingData<Repository>> {
        return Pager(PagingConfig(pageSize = 1)) {
            RepositoryListPagingSource(gitHubRepository,userName)
        }.flow
    }
}