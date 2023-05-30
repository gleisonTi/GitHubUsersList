package com.gleisonti.githubusers.presenter.detailuser

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gleisonti.githubusers.R
import com.gleisonti.githubusers.presenter.listusers.RepositoryList
import com.gleisonti.githubusers.presenter.listusers.UserListViewModel


@Composable
fun UserDetailView(userListViewModel: UserListViewModel, navController: NavHostController) {

    val user by userListViewModel.userDetail.collectAsState()

    Column(Modifier.fillMaxSize()) {
        Card(
            elevation = 4.dp,
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            , horizontalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(user.avatar_url)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = stringResource(R.string.description),
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .clip(CircleShape)
                        .width(64.dp)
                        .height(64.dp)
                )
                Column(Modifier.weight(2f)) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Name",
                            tint = Color.LightGray
                        )
                        Text(
                            text = user.login.toString(),
                            fontSize = 14.sp,
                            modifier = Modifier
                                .padding(start = 8.dp),
                            fontWeight = FontWeight(FontWeight.Bold.weight)
                        )
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Name",
                            tint = Color.LightGray
                        )
                        Text(
                            text = user.name.toString(),
                            fontSize = 14.sp,
                            modifier = Modifier
                                .padding(start = 8.dp),
                        )
                    }
                }
                Column(Modifier.weight(1f)) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Groups,
                            contentDescription = "Name",
                            tint = Color.LightGray
                        )
                        Text(
                            text = user.followers.toString(),
                            fontSize = 14.sp,
                            modifier = Modifier
                                .padding(start = 8.dp),
                        )
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AllInbox,
                            contentDescription = "Name",
                            tint = Color.LightGray
                        )
                        Text(
                            text = user.public_repos.toString(),
                            fontSize = 14.sp,
                            modifier = Modifier
                                .padding(start = 8.dp),
                        )
                    }
                }

            }
        }
        Card(
            elevation = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ){
            Row(Modifier.fillMaxWidth().padding(4.dp), horizontalArrangement = Arrangement.Center) {
                Text(text = "Lista de Repositórios", modifier = Modifier.padding(vertical = 8.dp), fontWeight = FontWeight.Bold)
            }
        }
        RepositoryList(
            userListViewModel,
            user.login.toString()
        )
    }


}
