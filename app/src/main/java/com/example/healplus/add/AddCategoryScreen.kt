package com.example.healplus.add

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.navigation.NavController
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import com.example.healplus.settings.ProfileTopAppBar

@Composable
fun AddCategoryScreen(navController: NavController, apiCallViewModel: ApiCallViewModel) {
    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }
    Scaffold(
    topBar = {
        ProfileTopAppBar("Thêm mới doanh mục sản phẩm", navController)
    }

    ){paddingValues ->
        Column (modifier = Modifier.padding(paddingValues)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally){
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Nhập doanh mục sản phẩm") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1
            )
            Spacer(modifier = Modifier
                .weight(1f))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Button(
                    onClick = {
                        if (title.isNotEmpty()) {
                            apiCallViewModel.addCategory(title) { response ->
                                message = response.message
                                showSnackbar = true
                            }
                        } else {
                            message = "Vui lòng nhập tên danh mục!"
                            showSnackbar = true
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
    if (showSnackbar) {
        SnackbarMessage(message = message) {
            showSnackbar = false
        }
    }
}

@Composable
fun SnackbarMessage(message: String, onDismiss: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize() // Chiếm toàn bộ màn hình để căn giữa
    ) {
        androidx.compose.material3.Snackbar(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(0.9f), // Giới hạn chiều rộng của Snackbar
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center // Căn giữa theo chiều dọc
            ) {
                Text(message)
                Spacer(modifier = Modifier.height(12.dp)) // Tạo khoảng cách giữa Button và Text
                Button(onClick = onDismiss) {
                    Text("Đóng")
                }
            }
        }
    }
}