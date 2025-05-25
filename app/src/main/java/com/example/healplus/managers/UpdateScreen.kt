package com.example.healplus.managers

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.core.model.categories.CategoryModel
import com.example.core.model.ingredients.IngredientsModel
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import com.example.healplus.settings.ProfileTopAppBar
import com.example.healplus.ui.theme.inversePrimaryDark
import com.example.healplus.ui.theme.tertiaryDarkHighContrast
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateDeleteCategory(
    navController: NavController,
    apiCallViewModel: ApiCallViewModel
){
    val categories = remember { mutableStateListOf<CategoryModel>() }
    val snackbarHostState = remember { SnackbarHostState() }
    var selectedIndex by remember { mutableStateOf(-1) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(categories) {
        apiCallViewModel.loadCategory()
        apiCallViewModel.categories.observeForever{
            categories.clear()
            categories.addAll(it)
        }
    }
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Danh sách danh mục") }
            )
        },
        floatingActionButton = { // Thêm FloatingActionButton
            FloatingActionButton(onClick = {
                navController.navigate("Insert_Category") // Điều hướng đến màn hình thêm mới
            }) {
                Icon(Icons.Default.Add, contentDescription = "Thêm mới danh mục")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState,
            modifier = Modifier
                .fillMaxSize()  // Đảm bảo SnackbarHost chiếm toàn bộ chiều rộng
                .wrapContentSize(Alignment.Center)
        ){data ->
            Snackbar(
                snackbarData = data,
                containerColor = MaterialTheme.colorScheme.secondaryContainer, // Màu nền
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer, // Màu chữ
                actionColor = MaterialTheme.colorScheme.primary, // Màu nút
                shape = MaterialTheme.shapes.medium,
            )
        } } // Thê
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
                    navController.navigate("edit_category/${categories[index].idc}/${categories[index].title}")
                },
                onItemDeleteClick = {
                    apiCallViewModel.deleteCategory(categories[index].idc){response ->
                    }
                    scope.launch {
                        snackbarHostState.showSnackbar("Đã xóa thành công!")
                    }
                    navController.navigate("add")
                }
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
    Column (modifier = Modifier
        .padding(horizontal = 16.dp)) {
        Box(
            modifier = Modifier
                .height(80.dp)
                .background(
                    color = if (iSelected) inversePrimaryDark else tertiaryDarkHighContrast,
                    shape = RoundedCornerShape(8.dp)
                ),
        ){
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                )
            }
            Row(modifier = Modifier.align(Alignment.TopEnd)) {
                IconButton(
                    onClick = onItemUpdateClick,
                    modifier = Modifier.size(24.dp) // Giảm kích thước
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Cập nhật",
                        modifier = Modifier.size(16.dp) // Giảm kích thước icon
                    )
                }
                IconButton(
                    onClick = onItemDeleteClick,
                    modifier = Modifier.size(24.dp) // Giảm kích thước
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Xóa",
                        modifier = Modifier.size(16.dp) // Giảm kích thước icon
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}
@Composable
fun EditCategoryScreen(
    navController: NavController,
    apiCallViewModel: ApiCallViewModel,
    idc: String,
    oldTitle: String) {
    var title by remember { mutableStateOf(oldTitle) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { ProfileTopAppBar("Chỉnh sửa danh mục", navController) },
        snackbarHost = { SnackbarHost(snackbarHostState,
            modifier = Modifier
                .fillMaxSize()  // Đảm bảo SnackbarHost chiếm toàn bộ chiều rộng
                .wrapContentSize(Alignment.Center)
        ){data ->
            Snackbar(
                snackbarData = data,
                containerColor = MaterialTheme.colorScheme.secondaryContainer, // Màu nền
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer, // Màu chữ
                actionColor = MaterialTheme.colorScheme.primary, // Màu nút
                shape = MaterialTheme.shapes.medium,
            )
        } } //
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Nhập danh mục sản phẩm") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Button(
                    onClick = {
                        if (title.isNotEmpty()) {
                            apiCallViewModel.updateCategory(idc, title) { response ->
                                scope.launch {
                                    snackbarHostState.showSnackbar(response.message)
                                }
                            }
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar("Cập nhật thành công!")
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 60.dp)
                        .height(56.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Cập nhật", fontSize = 24.sp)
                }
            }
        }
    }
}
// Ingredient
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateDeleteIngredient(
    navController: NavController,
    apiCallViewModel: ApiCallViewModel
){
    val ingredients = remember { mutableStateListOf<IngredientsModel>() }
    val snackbarHostState = remember { SnackbarHostState() }
    var selectedIndex by remember { mutableStateOf(-1) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(ingredients) {
        apiCallViewModel.loadIngredients()
        apiCallViewModel.ingredient.observeForever{
            ingredients.clear()
            ingredients.addAll(it)
        }
    }
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Danh sách danh mục") }
            )
        },
        floatingActionButton = { // Thêm FloatingActionButton
            FloatingActionButton(onClick = {
                navController.navigate("Insert_Ingredient") // Điều hướng đến màn hình thêm mới
            }) {
                Icon(Icons.Default.Add, contentDescription = "Thêm mới danh mục")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState,
            modifier = Modifier
                .fillMaxSize()  // Đảm bảo SnackbarHost chiếm toàn bộ chiều rộng
                .wrapContentSize(Alignment.Center)
        ){data ->
            Snackbar(
                snackbarData = data,
                containerColor = MaterialTheme.colorScheme.secondaryContainer, // Màu nền
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer, // Màu chữ
                actionColor = MaterialTheme.colorScheme.primary, // Màu nút
                shape = MaterialTheme.shapes.medium,
            )
        } } // Thê
    ){paddingValues ->
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(ingredients.size) { index -> IngredientItemsAdd(
                item = ingredients[index],
                iSelected = selectedIndex == index,
                onItemUpdateClick = {
                    selectedIndex = index
                    Log.d("UpdateDeleteIngredient", "Navigating to edit ingredient with id: ${ingredients[index]}")
                    navController.navigate("edit_ingredient/${ingredients[index]}}")
                },
                onItemDeleteClick = {
                    apiCallViewModel.deleteCategory(ingredients[index].idc){response ->
                    }
                    scope.launch {
                        snackbarHostState.showSnackbar("Đã xóa thành công!")
                    }
                    navController.navigate("add")
                }
            )
            }
        }

    }
}
@Composable
fun IngredientItemsAdd(
    item: IngredientsModel,
    iSelected: Boolean,
    onItemUpdateClick: () -> Unit,
    onItemDeleteClick: () -> Unit) {
    Column(modifier = Modifier
        .padding(horizontal = 16.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = if (iSelected) inversePrimaryDark else tertiaryDarkHighContrast,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = item.url,
                    contentDescription = "Uploaded Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp) // Adjust size as needed
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )

            }
            Row(modifier = Modifier.align(Alignment.TopEnd)) {
                IconButton(
                    onClick = onItemUpdateClick,
                    modifier = Modifier.size(24.dp) // Giảm kích thước
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Cập nhật",
                        modifier = Modifier.size(16.dp) // Giảm kích thước icon
                    )
                }
                IconButton(
                    onClick = onItemDeleteClick,
                    modifier = Modifier.size(24.dp) // Giảm kích thước
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Xóa",
                        modifier = Modifier.size(16.dp) // Giảm kích thước icon
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}
