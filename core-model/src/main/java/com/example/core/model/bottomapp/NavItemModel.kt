package com.example.core.model.bottomapp

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItemModel {
    abstract val route: String
    abstract val label: String
    abstract val badgeCount: Int

    data class VectorIconItem(
        override val route: String,
        override val label: String,
        val icon: ImageVector,
        override val badgeCount: Int = 0
    ) : NavItemModel()

    data class DrawableResItem(
        override val route: String,
        override val label: String,
        @DrawableRes val iconResId: Int,
        override val badgeCount: Int = 0
    ) : NavItemModel()
}