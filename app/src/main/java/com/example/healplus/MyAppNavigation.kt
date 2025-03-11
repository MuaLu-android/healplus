package com.example.healplus
import ManagmentCart
import android.net.Uri
import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.healplus.Screen.HomeScreen.MainActivityScreen
import com.example.healplus.Login.CreateAccountScreen
import com.example.healplus.Login.LoginScreen
import com.example.healplus.Login.LottieLoadingAnimation
import com.example.healplus.Login.OnboardingScreen
import com.example.healplus.Model.ProductsModel
import com.example.healplus.Screen.CartScreen
import com.example.healplus.Screen.HomeScreen.DetailScreen
import com.example.healplus.Screen.HomeScreen.ListItemScreen
import com.example.healplus.Screen.SettingsScreen.SettingScreen
import com.example.healplus.ViewModel.AuthSate
import com.example.healplus.ViewModel.AuthViewModel
import com.example.healplus.ViewModel.CategoryProductViewModel
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel, navController: NavHostController) {
    val authState by authViewModel.authSate.observeAsState()
    val startDestination = when (authState) {
        is AuthSate.Authenticated -> "home"
        else -> "introApp"
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable("introApp") { LottieLoadingAnimation(modifier, navController, authViewModel) }
        composable("onboarding") { OnboardingScreen(modifier, navController, authViewModel) }
        composable("signup") { CreateAccountScreen(modifier, navController, authViewModel) }
        composable("login") { LoginScreen(modifier, navController, authViewModel) }

        composable("home") {
            MainActivityScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable(route = "point"){

        }
        composable(route = "add"){

        }
        composable(route = "cart"){

        }
        composable("detail/{itemsModel}") {
                backStackEntry ->
            val jsonItem = backStackEntry.arguments?.getString("itemsModel")

            val item = Gson().fromJson(jsonItem, ProductsModel::class.java)
            DetailScreen(
                item = item,
                onBackClick = { navController.popBackStack() },
                navController = navController
            )
        }

        composable("cart") {
            CartScreen(
                managementCart = ManagmentCart(LocalContext.current),
                navController
            )
        }

        composable("settings") {
            SettingScreen(authViewModel = authViewModel, navController = navController)
        }
//    val authState by authViewModel.authSate.observeAsState()
//    NavHost(navController = navController, startDestination = "introApp", builder = {
//        composable("introApp"){
//            LottieLoadingAnimation(modifier, navController, authViewModel)
//        }
//        composable("onboarding"){
//            OnboardingScreen(modifier, navController, authViewModel)
//        }
//        composable("signup"){
//            CreateAccountScreen(modifier, navController, authViewModel)
//        }
//        composable("login"){
//            LoginScreen(modifier,navController, authViewModel)
//        }
//        composable(route = "home"){
//            MainActivityScreen(
//                onCartClick = {
//                    navController.navigate("cartScreen")
//                },
//                openItem = {
//                        itemsModel ->
//                    navController.navigate("detail/${Uri.encode(Gson().toJson(itemsModel))}")
//                },
//                oPenListCategory = {
//                        categoryid, categorytitle ->
//                    navController.navigate("category/$categoryid/$categorytitle")
//                },
//                oPenListCategoryItems = {
//                        categoryid, categorytitle ->
//                    navController.navigate("category/$categoryid/$categorytitle")
//                },
//                navController,
//                authViewModel
//            )
//        }
        composable(route = "category/{categoryid}/{categorytitle}",
            arguments = listOf(
                navArgument(name = "categoryid"){
                    type = NavType.StringType
                },
                navArgument(name = "categorytitle"){
                    type = NavType.StringType
                }
            )
        ){navBackStackEntry ->
            navBackStackEntry.arguments?.let {
                    argument ->
                val categoryid = argument.getString("categoryid")
                val categorytitle = argument.getString("categorytitle")
                if (categoryid.isNullOrBlank() || categorytitle.isNullOrBlank()) {
                    Log.e("Navigation", "Error: Missing categoryid or categorytitle")
                    return@composable
                }
                val viewModel: CategoryProductViewModel = viewModel()
                ListItemScreen(
                    title = categorytitle,
                    idc = categoryid,
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
////        composable(route = "cartScreen"){
////            CartScreen(
////                onBackClick = {
////                    navController.popBackStack()
////                }
////            )
////        }
//        composable("detail/{itemsModel}") {
//                backStackEntry ->
//            val jsonItem = backStackEntry.arguments?.getString("itemsModel")
//            val item = Gson().fromJson(jsonItem, ItemsModel::class.java)
//            val context = LocalContext.current
//
//            DetailScreen(
//                item = item,
//                onBackClick = { navController.popBackStack() },
//                context = context,
//                onAddToCartClick = {},
//                onCartClick = {
//                    navController.navigate("cartScreen")
//                }
//            )
//        }
//    })
    }
}