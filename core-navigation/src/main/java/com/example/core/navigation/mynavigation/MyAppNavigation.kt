package com.example.core.navigation.mynavigation
import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.core.model.products.ProductsModel
import com.example.core.tinydb.helper.ManagmentCart
import com.example.core.ui.cart.CartScreen
import com.example.core.ui.home.DetailScreen
import com.example.core.ui.home.MainActivityScreen
import com.example.core.ui.settings.SettingScreen
import com.example.core.viewmodel.authviewmodel.AuthSate
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.core.ui.home.ListItemScreen
import com.example.core.ui.login.CreateAccountScreen
import com.example.core.ui.login.LoginScreen
import com.example.core.ui.login.LottieLoadingAnimation
import com.example.core.ui.login.OnboardingScreen
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
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
                val viewModel: ApiCallViewModel = viewModel()
                ListItemScreen(
                    title = categorytitle,
                    idc = categoryid,
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
//        composable(route = "cartScreen"){
//            CartScreen(
//                onBackClick = {
//                    navController.popBackStack()
//                }
//            )
//        }
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