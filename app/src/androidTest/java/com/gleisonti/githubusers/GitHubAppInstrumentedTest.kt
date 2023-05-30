package com.gleisonti.githubusers

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.paging.PagingData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gleisonti.githubusers.domain.paging.repositories.RepositoriesRemoteDataSourceImpl
import com.gleisonti.githubusers.domain.paging.users.UsersRemoteDataSourceImpl
import com.gleisonti.githubusers.helper.ApiResponse
import com.gleisonti.githubusers.model.models.repomodels.Repository
import com.gleisonti.githubusers.model.models.usermodels.SearchUserResponse
import com.gleisonti.githubusers.model.models.usermodels.UserSearchItem
import com.gleisonti.githubusers.model.repository.GitHubRepository
import com.gleisonti.githubusers.presenter.listusers.UserListViewModel
import com.gleisonti.githubusers.presenter.scafoldnav.NavigationView
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ScaffoldComposeTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var gitHubRepository: GitHubRepository
    private lateinit var usersRemoteDataSourceImpl: UsersRemoteDataSourceImpl
    private lateinit var repositoriesRemoteDataSourceImpl: RepositoriesRemoteDataSourceImpl
    private lateinit var userListViewModel: UserListViewModel
    lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        gitHubRepository = mockk(relaxed = true)
        usersRemoteDataSourceImpl = mockk()
        repositoriesRemoteDataSourceImpl = mockk()
        userListViewModel = UserListViewModel(
            gitHubRepository, usersRemoteDataSourceImpl, repositoriesRemoteDataSourceImpl
        )

        // mock dos dados
        val mockResponse = ApiResponse.Success(
            SearchUserResponse(
                incomplete_results = false,
                items = mockUserListData(),
                total_count = mockUserListData().size
            )
        )

        val mockpagingUser = PagingData.from(mockUserListData())
        val mockpagingRepository = PagingData.from(mockRepoListData())
        coEvery { gitHubRepository.getUserList(1,100) } returns mockResponse
        coEvery { usersRemoteDataSourceImpl.getUsers("") } returns flowOf(mockpagingUser)
        coEvery { usersRemoteDataSourceImpl.getUsers("user") } returns flowOf(mockpagingUser)
        coEvery { userListViewModel.getUserList("") } returns flowOf(mockpagingUser)
        coEvery { repositoriesRemoteDataSourceImpl.getRepositories("user") } returns flowOf(mockpagingRepository)
        coEvery { userListViewModel.getRepositoryList("user1") } returns flowOf(mockpagingRepository)

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NavigationView(navController,userListViewModel)
        }
    }

    @Test
    fun test_load_list_user(){
        composeTestRule.onNode(hasText("Lista De Usuários")).assertIsDisplayed()
        composeTestRule.waitForIdle()

        mockUserListData().forEach {
            composeTestRule.onNodeWithText(it.login).assertIsDisplayed()
        }

    }

    @Test
    fun test_click_user_and_navigate(){

        composeTestRule.onNode(hasText("Lista De Usuários")).assertIsDisplayed()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("User 1").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNode(hasText("Detalhe do Usuário")).assertIsDisplayed()
        mockRepoListData().forEach {
            composeTestRule.onNodeWithText(it.name.toString()).assertIsDisplayed()
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription("Back").performClick()

    }

    @Test
    fun test_search_user(){
        composeTestRule.onNode(hasText("Lista De Usuários")).assertIsDisplayed()

        composeTestRule.onNodeWithTag("field").assert(hasText(""))

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("field").performTextInput("user")
    }

    fun mockUserListData() = mutableStateListOf(
        UserSearchItem(
            avatar_url = "https://avatars.githubusercontent.com/u/432763?v=4",
            login = "User 1",
            id = 1
        ),
        UserSearchItem(
            avatar_url = "https://avatars.githubusercontent.com/u/432763?v=4",
            login = "User 2",
            id = 2
        ),
        UserSearchItem(
            avatar_url = "https://avatars.githubusercontent.com/u/432763?v=4",
            login = "User 3",
            id = 3
        ),
        UserSearchItem(
            avatar_url = "https://avatars.githubusercontent.com/u/432763?v=4",
            login = "User 4",
            id = 4
        ),
    )

    fun mockRepoListData() = mutableStateListOf(
        Repository(
            forksCount = 20,
            stargazersCount = 10,
            language = "Java",
            name = "Repo1",
            id = 1
        ),
        Repository(
            forksCount = 20,
            stargazersCount = 10,
            language = "Java",
            name = "Repo2",
            id = 2
        ),
        Repository(
            forksCount = 20,
            stargazersCount = 10,
            language = "Java",
            name = "Repo3",
            id = 3
        ),
        Repository(
            forksCount = 20,
            stargazersCount = 10,
            language = "Java",
            name = "Repo4",
            id = 4
        )
    )

}
