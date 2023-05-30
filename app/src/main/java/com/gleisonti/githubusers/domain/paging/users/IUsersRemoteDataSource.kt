package com.gleisonti.githubusers.domain.paging.users

import androidx.paging.PagingData
import com.gleisonti.githubusers.model.models.usermodels.UserSearchItem
import kotlinx.coroutines.flow.Flow


interface IUsersRemoteDataSource {
    fun getUsers(query: String): Flow<PagingData<UserSearchItem>>
}
