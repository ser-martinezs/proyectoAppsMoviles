package com.example.myapplication.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.model.User
import com.example.myapplication.ui.viewmodel.PostReadViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostContainer(
    navController: NavController,
    posts:List<Post>,
    curPage: Int,
    maxPage:Int,
    onRefresh:()-> Unit,
    onPageSwitch:(Int)-> Unit = {} )
{
    var isRefreshing by remember {  mutableStateOf(false)}



    PullToRefreshBox(isRefreshing=isRefreshing, onRefresh = onRefresh, modifier = Modifier.fillMaxSize().padding(8.dp)) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            items(posts){ post -> PostThingy(post,navController) }
        }
    }

}

