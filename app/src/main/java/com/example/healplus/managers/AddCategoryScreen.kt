package com.example.healplus.managers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import com.example.healplus.settings.ProfileTopAppBar
import kotlinx.coroutines.launch

@Composable
fun AddCategoryScreen(navController: NavController, apiCallViewModel: ApiCallViewModel) {
    var title by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            ProfileTopAppBar("Thêm mới doanh mục", navController)
        },
        snackbarHost = { SnackbarHost(snackbarHostState,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ){data ->
            Snackbar(
                snackbarData = data,
                containerColor = MaterialTheme.colorScheme.secondaryContainer, // Màu nền
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer, // Màu chữ
                actionColor = MaterialTheme.colorScheme.primary, // Màu nút
                shape = MaterialTheme.shapes.medium,
            )
        } }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
            contentAlignment = Alignment.Center ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Nhập doanh mục sản phẩm") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Button(
                        onClick = {
                            if (title.isNotEmpty()) {
                                apiCallViewModel.addCategory(title) { response ->
                                    scope.launch {
                                        snackbarHostState.showSnackbar(response.message)
                                    }
                                }
                            } else {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Vui lòng nhập tên danh mục!")
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .height(56.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Thêm mới",
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun AddIngredientsScreen(navController: NavController, apiCallViewModel: ApiCallViewModel) {
    Scaffold(
        topBar = {
            ProfileTopAppBar("Thêm mới doanh mục sản phẩm", navController)
        }

    ){paddingValues ->
        Column (modifier = Modifier.padding(paddingValues)){  }
    }
}