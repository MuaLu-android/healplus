package com.example.healplus.add

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.healplus.settings.ProfileTopAppBar

@Composable
fun AddIngredientsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            ProfileTopAppBar("Thêm mới doanh mục sản phẩm", navController)
        }

    ){paddingValues ->

    }
}