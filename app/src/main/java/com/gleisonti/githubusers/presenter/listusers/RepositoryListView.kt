package com.gleisonti.githubusers.presenter.listusers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.gleisonti.githubusers.model.models.repomodels.Repository


@Composable
fun RepositoryList(
    userListViewModel: UserListViewModel, name: String
) {
    val repositoryList = userListViewModel.getRepositoryList(name).collectAsLazyPagingItems()
    Column() {
        LazyColumn {
            items(
                count = repositoryList.itemCount,
                key = repositoryList.itemKey(),
                contentType = repositoryList.itemContentType()
            ) { index ->
                val user = repositoryList[index]
                user?.let {
                    RepositoryCard(repository = it, userListViewModel)
                }
            }

            when (repositoryList.loadState.append) {
                is LoadState.NotLoading -> Unit
                LoadState.Loading -> {
                    item {
                        LoadingItem()
                    }
                }
                is LoadState.Error -> {
                    item {
                        ErrorItem(message = "Some error occurred")
                    }
                }
            }

            when (repositoryList.loadState.refresh) {
                is LoadState.NotLoading -> Unit
                LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                is LoadState.Error -> {
                    item {
                        ErrorItem("Erro de Autenticação")
                    }
                }
            }
        }
    }
}

@Composable
fun RepositoryCard(repository: Repository, userListViewModel: UserListViewModel) {
    Card(
        elevation = 4.dp, modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .padding(start = 8.dp)

            ) {
                Text(
                    text = repository.name.toString(),
                    fontSize = 24.sp,
                    modifier = Modifier
                        .align(CenterVertically)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .padding(start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = CenterVertically
            ) {

                Row() {
                    Icon(
                        imageVector = Icons.Filled.Code,
                        contentDescription = "Name",
                        tint = MaterialTheme.colors.primary
                    )
                    Text(
                        text = repository.language.toString(),
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(4.dp)
                            .align(CenterVertically)
                    )
                }
                Row() {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "StarCount",
                        tint = MaterialTheme.colors.secondary
                    )
                    Text(
                        text = repository.stargazersCount.toString(),
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(4.dp)
                            .align(CenterVertically)
                    )
                }
                Row() {
                    Icon(
                        imageVector = Icons.Filled.AccountTree,
                        contentDescription = "Forks",
                        tint = MaterialTheme.colors.secondaryVariant
                    )
                    Text(
                        text = repository.forksCount.toString(),
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(4.dp)
                            .align(CenterVertically)
                    )
                }


            }

        }

    }
}

@Preview
@Composable
fun RepositoryCard() {
    Card(
        elevation = 4.dp, modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .padding(start = 8.dp)

            ) {
                Text(
                    text = "ListaRepo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .align(CenterVertically)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .padding(start = 8.dp, end = 8.dp),
                    verticalAlignment = CenterVertically
                ) {

                    Row(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth()) {
                        Icon(
                            imageVector = Icons.Filled.Code,
                            contentDescription = "Name",
                            tint = MaterialTheme.colors.primary
                        )
                        Text(
                            text = "kotlin",
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(4.dp)
                                .align(CenterVertically)
                        )
                    }
                    Row(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "StarCount",
                            tint = MaterialTheme.colors.secondary
                        )
                        Text(
                            text = "100",
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(4.dp)
                                .align(CenterVertically)
                        )
                    }
                    Row(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AccountTree,
                            contentDescription = "Forks",
                            tint = MaterialTheme.colors.secondaryVariant
                        )
                        Text(
                            text = "100",
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(4.dp)
                                .align(CenterVertically)
                        )
                    }

                }

            }

        }
    }
}


