package com.example.healplus.add

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.core.model.categories.CategoryModel
import com.example.core.ui.home.MediumTopAppBar
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import com.example.healplus.R
import com.example.healplus.category.ListItems
import com.example.healplus.home.Banners
import com.example.healplus.home.CategoryItem
import com.example.healplus.home.CategoryList
import com.example.healplus.home.IngredientScreen
import com.example.healplus.home.SectionTitle
import com.example.healplus.home.UnSectionTitle
import com.example.healplus.ui.theme.inversePrimaryDark
import com.example.healplus.ui.theme.tertiaryDarkHighContrast

@Composable
fun AddScreen(modifier: Modifier = Modifier, navController: NavController){
    Column (
        modifier = Modifier
            .fillMaxSize()
    ){
        Text(
            text = "Category",
            modifier = Modifier
                .clickable {
                    navController.navigate("update_delete_category")
                }
        )
        Text(
            text = "Insert_Category",
            modifier = Modifier
                .clickable {
                    navController.navigate("insert_category")
                }
        )
        Text(
            text = "Ingredient",
            modifier = Modifier
                .clickable {
                    navController.navigate("update_delete_ingredient")
                }
        )
        Text(
            text = "Insert_Ingredient",
            modifier = Modifier
                .clickable {
                    navController.navigate("insert_ingredient")
                }
        )
        Text(
            text = "Element",
            modifier = Modifier
                .clickable {
                    navController.navigate("update_delete_Element")
                }
        )
        Text(
            text = "Insert_Element",
            modifier = Modifier
                .clickable {
                    navController.navigate("insert_ingredient")
                }
        )
        Text(
            text = "Products",
            modifier = Modifier
                .clickable {
                    navController.navigate("insert_product")
                }
        )
    }
}
@Composable
fun UpdateDeleteCategory(
    navController: NavController
){
    val viewModel = ApiCallViewModel()
    val categories = remember { mutableStateListOf<CategoryModel>() }
    var showCategoryLoading by remember { mutableStateOf(true) }
    var selectedIndex by remember { mutableStateOf(-1) }
    LaunchedEffect(Unit) {
        viewModel.loadCategory()
        viewModel.categories.observeForever{
            categories.clear()
            categories.addAll(it)
            Log.d("LaunchedEffect", "item Categories nhân đc:${categories.size}")
            showCategoryLoading = false
        }
    }
    Scaffold (
        topBar = {  }
    ){paddingValues ->
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(categories.size) { index -> CategoryItemAdd(
                    item = categories[index],
                    iSelected = selectedIndex == index,
                    onItemUpdateClick = {
                        selectedIndex = index
//                oPenElements(categories[index].idc, categories[index].title)
                        navController.navigate("edit_category/${categories[index].idc}/${categories[index].title}")
//                navController.navigate("home")
                    },
                    onItemDeleteClick = { }
                )
                }
            }

    }
}
@Composable
fun CategoryItemAdd(
    item: CategoryModel,
    iSelected: Boolean,
    onItemUpdateClick: () -> Unit,
    onItemDeleteClick: () -> Unit) {
    Row (modifier = Modifier
        .fillMaxWidth()
        .background(
            color = if (iSelected) inversePrimaryDark else tertiaryDarkHighContrast,
            shape = RoundedCornerShape(8.dp)
        ),
        verticalAlignment = Alignment.CenterVertically){
        Text(
            text = item.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp),
        )
        Button(
            onClick = { onItemUpdateClick()}
        ) { Text(
            text = "Cap nhat"
        ) }
        Button(
            onClick = { onItemDeleteClick()}
        ) { Text(
            text = "Xoa"
        ) }
    }
}