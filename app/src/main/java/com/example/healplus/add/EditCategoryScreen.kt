package com.example.healplus.add

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import com.example.healplus.settings.ProfileTopAppBar

@Composable
fun EditCategoryScreen(navController: NavController, apiCallViewModel: ApiCallViewModel, idc: String, oldTitle: String) {
    var title by remember { mutableStateOf(oldTitle) }
    var message by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { ProfileTopAppBar("Chỉnh sửa danh mục", navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Nhập danh mục sản phẩm") },
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
                            apiCallViewModel.updateCategory(idc, title) { response ->
                                message = response.message
                                showSnackbar = true
                            }
                        } else {
                            message = "Vui lòng nhập tên danh mục!"
                            showSnackbar = true
                        }
                    },
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 60.dp)
                        .height(56.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Cập nhật", fontSize = 24.sp)
                }
            }
        }
    }

    if (showSnackbar) {
        SnackbarMessage(message = message) { showSnackbar = false }
    }
}