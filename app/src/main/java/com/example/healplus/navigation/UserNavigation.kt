package com.example.healplus.navigation
import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.core.model.products.ProductsModel
import com.example.core.model.products.ReviewItem
import com.example.core.model.users.UserAuthModel
import com.example.core.tinydb.helper.ManagmentCart
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import com.example.healplus.add.AddProductScreen
import com.example.healplus.cart.AddressScreen
import com.example.healplus.cart.CartScreen
import com.example.healplus.cart.CheckOutScreen
import com.example.healplus.category.CategoryScreen
import com.example.healplus.chat.UserChatScreen
import com.example.healplus.home.AllReviewsScreen
import com.example.healplus.home.DetailScreen
import com.example.healplus.home.MainActivityScreen
import com.example.healplus.home.ProductDetailScreen
import com.example.healplus.home.WriteReviewScreen
import com.example.healplus.oder.UsersOder
import com.example.healplus.search.SearchScreen
import com.example.healplus.settings.ProfileScreen
import com.example.healplus.settings.SettingScreen
import com.example.healplus.settings.UpdateProfileScreen
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel, navController: NavHostController) {

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            MainActivityScreen(
                navController = navController
            )
        }
        composable(route = "point"){

        }
        composable(route = "oderscreen"){
            UsersOder(navController)
        }
        composable(route = "add"){
            UserChatScreen()
        }
        composable("detail/{itemsModel}") {
                backStackEntry ->
            val jsonItem = backStackEntry.arguments?.getString("itemsModel")

            val item = Gson().fromJson(jsonItem, ProductsModel::class.java)
            DetailScreen(
                item = item,
                navController = navController
            )
        }
        composable("productDetail/{product}") {
                backStackEntry ->
            val jsonItem = backStackEntry.arguments?.getString("product")

            val item = Gson().fromJson(jsonItem, ProductsModel::class.java)
            ProductDetailScreen(
                item = item,
                navController = navController
            )
        }
        composable("allReviews/{productName}/{reviewItems}") { backStackEntry ->
            val productName = backStackEntry.arguments?.getString("productName")?: ""
            val encodedJsonReviews = backStackEntry.arguments?.getString("reviewItems")
            val jsonReviews = URLDecoder.decode(encodedJsonReviews, StandardCharsets.UTF_8.toString())
            val typeToken = object : TypeToken<List<ReviewItem>>() {}.type
            val reviewList: List<ReviewItem> = Gson().fromJson(jsonReviews, typeToken)
            AllReviewsScreen(navController, productName, reviewList)
        }
        composable("writeReview/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            WriteReviewScreen(navController, productId)
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
                    Log.e("Navigation123", "Error: Missing categoryid or categorytitle")
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
        composable("editProfile/{userData}"){
                backStackEntry ->
            val jsonItem = backStackEntry.arguments?.getString("userData")

            val item = Gson().fromJson(jsonItem, UserAuthModel::class.java)
            UpdateProfileScreen(item, navController)
        }
    }
}