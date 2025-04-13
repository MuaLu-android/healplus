package com.example.healplus.acitivity

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core.model.bottomapp.NavItemModel
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.R
import com.example.healplus.navigation.AdminNavigation
import com.example.healplus.ui.theme.AppTheme

class AdminActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authViewModel: AuthViewModel by viewModels()

        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    AdminMain(authViewModel = authViewModel,
                        modifier = Modifier
                            .padding(innerPadding))
                }
            }
        }
    }
}
@Composable
fun AdminMain(modifier: Modifier = Modifier, authViewModel: AuthViewModel){
    val navController = rememberNavController()
    val navItemList = listOf(
        NavItemModel(route = "home", label = stringResource(id = R.string.home), icon = Icons.Default.Home, badgeCount = 0),
        NavItemModel(route = "point", label = stringResource(id = R.string.manager), icon = Icons.Default.List, badgeCount = 0),
        NavItemModel(route = "add", label = stringResource(id =R.string.add), icon = Icons.Default.Email, badgeCount = 0),
        NavItemModel(route = "oder", label = stringResource(R.string.oder), icon = Icons.Default.ExitToApp, badgeCount = 0),
        NavItemModel(route = "settings", label = stringResource(R.string.settings), icon = Icons.Default.Settings, badgeCount = 0),
    )
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    LaunchedEffect(navController.currentBackStackEntry) {
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        selectedIndex = navItemList.indexOfFirst { it.route == currentRoute }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentRoute != "detail/{itemsModel}" && currentRoute != "oder"
                && currentRoute != "category/{categoryid}/{categorytitle}" && currentRoute != "add"
                && currentRoute != "update_delete_category"&& currentRoute != "Products"
                && currentRoute != "Insert_Category" && currentRoute != "Category"
                && currentRoute != "Ingredient" && currentRoute != "Insert_Ingredient"
                && currentRoute != "edit_category/{idc}/{title}" && currentRoute != "oderscreen") { // Kiểm tra đăng nhập
                NavigationBar {
                    navItemList.forEachIndexed { index, navItem ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = {
                                selectedIndex = index
                                Log.d("Navigation", "Click vào: ${navItem.route}, selectedIndex: $selectedIndex")
                                navController.navigate(navItem.route) {
                                    popUpTo("cart") { inclusive = false } // Tránh lặp màn hình
                                    launchSingleTop = true
                                }
                            },
                            icon = {
                                BadgedBox(badge = {
                                    if (navItem.badgeCount > 0)
                                        Badge { Text(text = navItem.badgeCount.toString()) }
                                }) {
                                    Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                                }
                            },
                            label = { Text(text = navItem.label)
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        AdminNavigation(
            modifier = Modifier.padding(innerPadding),
            authViewModel = authViewModel,
            navController = navController // Truyền `navController` vào `MyAppNavigation`
        )
    }
}