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
import com.example.core.model.ingredients.IngredientsModel
import com.example.core.model.products.ProductsModel
import com.example.core.tinydb.helper.ManagmentCart
import com.example.core.viewmodel.apiviewmodel.ApiCallAdd
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import com.example.healplus.add.AddCategoryScreen
import com.example.healplus.add.AddIngredientsScreen
import com.example.healplus.add.AddProductScreen
import com.example.healplus.add.AddScreen
import com.example.healplus.add.EditCategoryScreen
import com.example.healplus.add.EditIngredientScreen
import com.example.healplus.add.UpdateDeleteCategory
import com.example.healplus.add.UpdateDeleteIngredient
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
    val viewModel: ApiCallViewModel = viewModel()
    val apiCallAdd: ApiCallAdd = viewModel()
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
        composable(route = "Insert_Category"){
            AddCategoryScreen(navController, viewModel)
        }
        composable(route = "Category"){
            UpdateDeleteCategory(navController, viewModel)
        }
        composable("edit_category/{idc}/{title}") { backStackEntry ->
            val idc = backStackEntry.arguments?.getString("idc") ?: ""
            val title = backStackEntry.arguments?.getString("title") ?: ""
            EditCategoryScreen(navController, viewModel, idc, title)
        }
        composable(route = "Ingredient"){
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
        composable("Products"){

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
                onBackClick = { navController.popBackStack() },
                navController = navController
            )
        }

        composable("cart") {
            CartScreen(
                navController
            )
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
        composable("updateProfile"){
            UpdateProfileScreen()
        }
    }
}