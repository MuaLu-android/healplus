package com.example.healplus.acitivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core.model.bottomapp.NavItemModel
import com.example.core.tinydb.helper.ManagmentCart
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.R
import com.example.healplus.navigation.MyAppNavigation
import com.example.healplus.ui.theme.AppTheme

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authViewModel: AuthViewModel by viewModels()

        setContent {
            AppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    MainApp(authViewModel = authViewModel,
                        modifier = Modifier
                            .padding(innerPadding))
                }
            }
        }
    }
}
@Composable
fun MainApp(modifier: Modifier = Modifier, authViewModel: AuthViewModel){
    val navController = rememberNavController()
    val userId = authViewModel.getUserId().toString()
    val context = LocalContext.current
    val managementCart = remember { ManagmentCart(context, userId) }
    val navItemList = listOf(
        NavItemModel.DrawableResItem(route = "home", label = stringResource(id = R.string.home), R.drawable.home_24px, badgeCount = 0),
        NavItemModel.DrawableResItem(route = "point", label = stringResource(id = R.string.poit), R.drawable.rewarded_ads_24px, badgeCount = 0),
        NavItemModel.DrawableResItem(route = "add", label = "Email", R.drawable.mail_24px, badgeCount = 0),
        NavItemModel.DrawableResItem(route = "cart", label = stringResource(R.string.cart), R.drawable.shopping_cart_24px, badgeCount = managementCart.getItemCount()),
        NavItemModel.DrawableResItem(route = "settings", label = stringResource(R.string.settings), R.drawable.settings_24px, badgeCount = 0),
    )
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val routesToHideBottomBar = listOf(
        "detail/{itemsModel}",
        "cart",
        "add",
        "profile",
        "order_screen/{selectedProducts}/{itemTotal}/{tax}/{quantity}",
        "address",
        "editProfile/{userData}",
        "writeReview/{productId}",
        "allReviews/{productName}/{reviewItems}",
        "productDetail/{product}",
    )
    LaunchedEffect(navController.currentBackStackEntry) {
        val currentRoute1 = navController.currentBackStackEntry?.destination?.route
        selectedIndex = navItemList.indexOfFirst { it.route == currentRoute1 }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentRoute !in routesToHideBottomBar) {
                NavigationBar {
                    navItemList.forEachIndexed { index, navItem ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = {
                                selectedIndex = index
                                Log.d("Navigation", "Click vào: ${navItem.route}, selectedIndex: $selectedIndex")
                                navController.navigate(navItem.route) {
                                    popUpTo("cart") { inclusive = false }
                                    launchSingleTop = true
                                }
                            },
                            icon = {
                                BadgedBox(badge = {
                                    if (navItem.badgeCount > 0)
                                        Badge { Text(text = navItem.badgeCount.toString()) }
                                }) {
                                    Icon(painterResource(id = navItem.iconResId), contentDescription = navItem.label)
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
        MyAppNavigation(
            modifier = Modifier.padding(innerPadding),
            authViewModel = authViewModel,
            navController = navController
        )
    }
}


