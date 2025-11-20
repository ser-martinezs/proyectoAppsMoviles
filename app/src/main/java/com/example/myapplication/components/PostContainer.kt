package com.example.myapplication.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
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
    onPageSwitch:(Int)-> Unit = {},
    topBar: @Composable ()-> Unit={}

)
{
    var isRefreshing by remember {  mutableStateOf(false)}




    Scaffold(
        topBar = topBar,
        modifier = Modifier.fillMaxSize().padding(8.dp),
        bottomBar = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically) {
                val prevEnabled = (curPage > 0)
                var lastEnabled = (curPage < maxPage)
                Button({onPageSwitch(curPage-1)}, enabled = prevEnabled){Text(if (prevEnabled) "Anterior (${curPage})" else "Anterior")}

                TextField(
                    value = "${curPage+1}",
                    onValueChange = {},
                    modifier = Modifier.width(125.dp).size(12.dp),
                )
                Button({onPageSwitch(curPage+1)}, enabled = lastEnabled){Text(if (lastEnabled) "Siguiente (${curPage+2})" else "Siguiente")}

            }
        }

    ) {
        innerPadding->

        PullToRefreshBox(isRefreshing=isRefreshing, onRefresh = onRefresh, modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                items(posts){ post -> PostThingy(post,navController) }
            }
        }

    }

}

