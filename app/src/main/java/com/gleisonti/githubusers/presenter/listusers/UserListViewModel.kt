package com.gleisonti.githubusers.presenter.listusers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gleisonti.githubusers.domain.paging.repositories.RepositoriesRemoteDataSourceImpl
import com.gleisonti.githubusers.helper.ApiResponse
import com.gleisonti.githubusers.model.models.repomodels.Repository
import com.gleisonti.githubusers.model.models.usermodels.UserDetail
import com.gleisonti.githubusers.model.repository.GitHubRepository
import com.gleisonti.githubusers.domain.paging.users.UsersRemoteDataSourceImpl
import com.gleisonti.githubusers.model.models.usermodels.UserSearchItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val gitHubRepository: GitHubRepository,
    private val usersRemoteDataSourceImpl: UsersRemoteDataSourceImpl,
    private val repositoriesRemoteDataSourceImpl: RepositoriesRemoteDataSourceImpl
) : ViewModel() {

    val currentQuery = MutableStateFlow("")
    val users = currentQuery
        .flatMapLatest {
        getUserList(it)
    }

    private val _userDetail = MutableStateFlow(UserDetail())
    val userDetail: StateFlow<UserDetail> get() = _userDetail

    fun setUserDetail(userUserSearchItem: UserSearchItem) {
        viewModelScope.launch {
            val response = gitHubRepository.getUserDetails(userUserSearchItem.login)
            when (response) {
                is ApiResponse.Success -> {
                    _userDetail.value = response.data
                }
                is ApiResponse.Error -> {}
                is ApiResponse.Loading -> {}
            }
        }
    }

    fun getUserList(query: String): Flow<PagingData<UserSearchItem>> {
        return usersRemoteDataSourceImpl.getUsers(query = query).cachedIn(viewModelScope)
    }

    fun getRepositoryList(userName: String): Flow<PagingData<Repository>> {
        return repositoriesRemoteDataSourceImpl.getRepositories(userName).cachedIn(viewModelScope)
    }

}