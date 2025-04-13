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
import com.example.healplus.add.AddProductScreen
import com.example.healplus.cart.AddressScreen
import com.example.healplus.cart.CartScreen
import com.example.healplus.cart.CheckOutScreen
import com.example.healplus.category.CategoryScreen
import com.example.healplus.home.DetailScreen
import com.example.healplus.home.MainActivityScreen
import com.example.healplus.search.SearchScreen
import com.example.healplus.settings.ProfileScreen
import com.example.healplus.settings.SettingScreen
import com.example.healplus.settings.UpdateProfileScreen
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URLDecoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel, navController: NavHostController) {

    NavHost(navController = navController, startDestination = "home") {
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
            navController
            )
        }
        composable("order_screen/{selectedProducts}/{itemTotal}/{tax}/{quantity}") { backStackEntry ->
            val selectedProductsJson = backStackEntry.arguments?.getString("selectedProducts") ?: "[]"
            val totalAmount = backStackEntry.arguments?.getString("itemTotal")?.toDoubleOrNull() ?: 0.0
            val tax = backStackEntry.arguments?.getString("tax")?.toDoubleOrNull() ?: 0.0
            val quantity = backStackEntry.arguments?.getString("quantity")?.toInt() ?: 0
            val selectedProducts: List<ProductsModel> = Gson().fromJson(
                URLDecoder.decode(selectedProductsJson, "UTF-8"),
                object : TypeToken<List<ProductsModel>>() {}.type
            )

            CheckOutScreen(navController, selectedProducts, totalAmount, tax, quantity)
        }
        composable("address"){
            AddressScreen(navController)
        }

        composable("settings") {
            SettingScreen(authViewModel = authViewModel, navController = navController)
        }
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