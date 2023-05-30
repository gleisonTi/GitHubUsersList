package com.gleisonti.githubusers.presenter.scafoldnav

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gleisonti.githubusers.presenter.detailuser.UserDetailView
import com.gleisonti.githubusers.presenter.listusers.UserList
import com.gleisonti.githubusers.presenter.listusers.UserListViewModel

@Composable
fun NavigationView(navController: NavHostController, userListViewModel: UserListViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TitlePage(navController)
                },
                navigationIcon = {
                    BackButton(navController = navController,)
                }
            )
        },
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = "listUsers",
            modifier = Modifier.padding(contentPadding)
        ) {
            composable("listUsers") { UserList(userListViewModel, navController) }
            composable("detailUsers") {
                UserDetailView(
                    userListViewModel,
                    navController
                )
            }
        }
    }
}

@Composable
fun BackButton(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    if (currentDestination == "detailUsers") {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
        }
    }
}

@Composable
fun TitlePage(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val title = when (currentDestination) {
        "listUsers" -> "Lista De Usuários"
        "detailUsers" -> "Detalhe do Usuário"
        else -> "GitHubApp"
    }

    Text(text = title)
}