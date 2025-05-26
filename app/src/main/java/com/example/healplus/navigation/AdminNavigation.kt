package com.example.healplus.navigation
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.core.model.ingredients.IngredientsModel
import com.example.core.model.products.ProductsModel
import com.example.core.model.users.UserAuthModel
import com.example.core.viewmodel.apiviewmodel.ApiCallAdd
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import com.example.healplus.managers.AddCategoryScreen
import com.example.healplus.managers.AddIngredientsScreen
import com.example.healplus.managers.AddProductScreen
import com.example.healplus.managers.AddScreen
import com.example.healplus.managers.EditCategoryScreen
import com.example.healplus.managers.EditIngredientScreen
import com.example.healplus.managers.UpdateDeleteCategory
import com.example.healplus.managers.UpdateDeleteIngredient
import com.example.healplus.admin.MonthlyRevenueBarChart
import com.example.healplus.category.CategoryScreen
import com.example.healplus.chat.AdminChatScreen
import com.example.healplus.chat.ChatDetailScreen
import com.example.healplus.home.DetailScreen
import com.example.healplus.home.MainActivityScreen
import com.example.healplus.home.ProductDetailScreen
import com.example.healplus.oder.OderManagers
import com.example.healplus.search.SearchScreen
import com.example.healplus.settings.ProfileScreen
import com.example.healplus.settings.SettingScreen
import com.example.healplus.settings.UpdateProfileScreen
import com.google.gson.Gson
@Composable
fun AdminNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel, navController: NavHostController) {
    val viewModel: ApiCallViewModel = viewModel()
    val apiCallAdd: ApiCallAdd = viewModel()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            MainActivityScreen(
                navController = navController
            )
        }
        composable("oder"){
            OderManagers(
                navController = navController
            )
        }
        
        composable(route = "point"){
            AddScreen(modifier = modifier,
                navController)
        }
        composable(route = "oderscreen"){
            MonthlyRevenueBarChart(navController)
        }
        composable(route = "Insert_Category"){
            AddCategoryScreen(navController, viewModel)
        }
        composable(route = "Danh mục chính"){
            UpdateDeleteCategory(navController, viewModel)
        }
        composable("edit_category/{idc}/{title}") { backStackEntry ->
            val idc = backStackEntry.arguments?.getString("idc") ?: ""
            val title = backStackEntry.arguments?.getString("title") ?: ""
            EditCategoryScreen(navController, viewModel, idc, title)
        }
        composable(route = "Danh mục phụ"){
            UpdateDeleteIngredient(navController, viewModel)
        }
        composable(route = "Insert_Ingredient"){
            AddIngredientsScreen(navController, viewModel)
        }
        composable("edit_ingredient/{ingredient}") {
            backStackEntry ->
            val jsonItem = backStackEntry.arguments?.getString("ingredient")

            val item = Gson().fromJson(jsonItem, IngredientsModel::class.java)
            EditIngredientScreen(
                navController = navController,
                apiCallViewModel = viewModel,
                item = item
            )
        }
        composable("Sản phẩm"){

            AddProductScreen(navController = navController,
                viewModel = viewModel,
                apiCallAdd = apiCallAdd)
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
            Log.d("ProductDetailScreen", "Received item: $item")
            ProductDetailScreen(
                item = item,
                navController = navController
            )
        }
        composable("add"){
            AdminChatScreen(navController = navController)
        }
        composable("chat_detail/{roomId}") { backStackEntry ->
            val roomId = backStackEntry.arguments?.getString("roomId")
            if (roomId != null) {
                ChatDetailScreen(
                    roomId = roomId,
                    viewModel = authViewModel, 
                    navController = navController
                )
            } else {
                
                Text("Error: Chat Room ID not found")
            }
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
            SearchScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable("profile"){
            ProfileScreen(
                viewModel = authViewModel,
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