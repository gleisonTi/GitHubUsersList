package com.gleisonti.githubusers.presenter.listusers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gleisonti.githubusers.R
import com.gleisonti.githubusers.model.models.usermodels.UserSearchItem


@Composable
fun UserList(userListViewModel: UserListViewModel, navController: NavHostController) {

    val userList = userListViewModel.users.collectAsLazyPagingItems()

    Column(Modifier.padding(8.dp)) {
        SearchUser(modifier = Modifier,userListViewModel)
        LazyColumn {
            items(
                count = userList.itemCount,
                key = userList.itemKey(),
                contentType = userList.itemContentType()
            ) { index ->
                val user = userList[index]
                user?.let {
                    UserCard(userUserSearchItem = it, userListViewModel, navController)
                }
            }

            when (userList.loadState.append) {
                is LoadState.NotLoading -> Unit
                LoadState.Loading -> {
                    item {
                        LoadingItem()
                    }
                }
                is LoadState.Error -> {
                    item {
                        ErrorItem("Error")
                    }
                }
            }

            when (userList.loadState.refresh) {
                is LoadState.NotLoading -> Unit
                LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(vertical = 24.dp)
                            )
                        }
                    }
                }
                is LoadState.Error -> {
                    item {
                        val errorState = userList.loadState.refresh as LoadState.Error
                        ErrorItem(errorState.error.message.toString())
                    }
                }
            }
        }
    }
}




@Composable
fun SearchUser(modifier: Modifier, userListViewModel: UserListViewModel) {
    val text by userListViewModel.currentQuery.collectAsState()
    Row() {
        OutlinedTextField(
            modifier = modifier.fillMaxWidth().testTag("field"),
            value = text,
            singleLine = true,
            onValueChange = { newText ->
                userListViewModel.currentQuery.value = newText
            },
            label = {
                Text(text = "Pesquisa por Nome")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Pesquisar")
            },
            shape = RoundedCornerShape(4.dp)

        )
    }

}

@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .wrapContentHeight(),

        contentAlignment = Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(42.dp)
                .height(42.dp)
                .padding(8.dp),
            strokeWidth = 5.dp
        )
    }
}

@Composable
fun ErrorItem(message: String) {
    Card(
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp, horizontal = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
                .padding(8.dp)
        ) {
            Text(
                color = Color.White,
                text = message,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(CenterVertically)
            )
        }
    }
}

@Composable
fun UserCard(userUserSearchItem: UserSearchItem, userListViewModel: UserListViewModel, navController: NavHostController) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    userListViewModel.setUserDetail(userUserSearchItem)
                    navController.navigate("detailUsers")
                }
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(userUserSearchItem.avatar_url)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentDescription = stringResource(R.string.description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .width(42.dp)
                    .height(42.dp)
            )
            Text(
                text = userUserSearchItem.login,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(CenterVertically)
            )
        }
    }
}

