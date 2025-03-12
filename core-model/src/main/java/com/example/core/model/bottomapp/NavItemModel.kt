package com.example.healplus.Model

import androidx.compose.ui.graphics.vector.ImageVector

data class NavItemModel(
    val route: String,
    val label : String,
    val icon : ImageVector,
    val badgeCount : Int,
)