package com.gleisonti.githubusers.domain.paging.repositories

import androidx.paging.PagingData
import com.gleisonti.githubusers.model.models.repomodels.Repository
import com.gleisonti.githubusers.model.models.usermodels.UserItem
import kotlinx.coroutines.flow.Flow


interface IRepositoryRemoteDataSource {
    fun getRepositories(userName:String): Flow<PagingData<Repository>>
}
