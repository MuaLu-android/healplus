package com.example.healplus.navigation
import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
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
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import com.example.healplus.add.AddCategoryScreen
import com.example.healplus.add.AddProductScreen
import com.example.healplus.add.AddScreen
import com.example.healplus.add.EditCategoryScreen
import com.example.healplus.add.UpdateDeleteCategory
import com.example.healplus.cart.CartScreen
import com.example.healplus.category.CategoryScreen
import com.example.healplus.home.DetailScreen
import com.example.healplus.home.MainActivityScreen
import com.example.healplus.search.SearchScreen
import com.example.healplus.settings.ProfileScreen
import com.example.healplus.settings.SettingScreen
import com.example.healplus.settings.UpdateProfileScreen
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel, navController: NavHostController) {

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            MainActivityScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable(route = "point"){

        }
        //Admin
        composable(route = "add"){
            AddScreen(modifier = modifier,
                navController)
        }
        composable(route = "insert_category"){
            val viewModel: ApiCallViewModel = viewModel()
            AddCategoryScreen(navController, viewModel)
        }
        composable(route = "update_delete_category"){
            UpdateDeleteCategory(navController)
        }
        composable("edit_category/{idc}/{title}") { backStackEntry ->
            val viewModel: ApiCallViewModel = viewModel()
            val idc = backStackEntry.arguments?.getString("idc") ?: ""
            val title = backStackEntry.arguments?.getString("title") ?: ""
            EditCategoryScreen(navController, viewModel, idc, title)
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
                CategoryScreen(
                    title = categorytitle,
                    id = categoryid,
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
        composable("search"){
            val viewModel: ApiCallViewModel = viewModel()
            SearchScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable("profile"){
            val viewModel: AuthViewModel = viewModel()
            ProfileScreen(
                viewModel = viewModel,
                navController
            )
        }
//        composable("add"){
//            val viewModel: AuthViewModel = viewModel()
//            AddProductScreen(navController = navController,
//                authViewModel = viewModel)
//        }
        composable("updateProfile"){
            UpdateProfileScreen()
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