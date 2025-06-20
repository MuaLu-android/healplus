package com.example.healplus.feature.shop.managers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.healplus.feature.personalization.profiles.ProfileTopAppBar

@Composable
fun AddElementScreen(navController: NavController) {
    Scaffold(
        topBar = {
            ProfileTopAppBar("Thêm mới doanh mục sản phẩm", navController)
        }

    ){paddingValues ->
        Column (modifier = Modifier.padding(paddingValues)){  }
    }
}