package com.example.healplus.feature.shop.managers

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.core.model.categories.CategoryModel
import com.example.core.model.ingredients.IngredientsModel
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import com.example.healplus.feature.personalization.profiles.ProfileTopAppBar
import kotlinx.coroutines.launch

@Composable
fun EditIngredientScreen(
    navController: NavController,
    apiCallViewModel: ApiCallViewModel,
    item: IngredientsModel
) {
    var title by remember { mutableStateOf(item.title) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val ingredient = remember { mutableStateListOf<IngredientsModel>() }
    val context = LocalContext.current
    var selectedImageUri: Uri? by remember { mutableStateOf(null) }
    var uploadedImageUrl: String? by remember { mutableStateOf(null) }
    val categoryList = remember { mutableStateListOf<CategoryModel>() }
    var selectedIngredientId by remember { mutableStateOf<String>("") }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        if (uri != null) {
            scope.launch {
                val url = uploadImageToServer(uri, context)
                uploadedImageUrl = url
            }
        } else {
            Log.d("YourScreen", "No image selected.")
        }
    }
    var expanded by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        apiCallViewModel.loadCategory()
        apiCallViewModel.categories.observeForever {
            categoryList.clear()
            categoryList.addAll(it)
        }
    }
    Log.d("YourScreen", "Selected Image URI: $selectedImageUri")
    Scaffold(
        topBar = { ProfileTopAppBar("Chỉnh sửa danh mục", navController) },
        snackbarHost = { SnackbarHost(snackbarHostState,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ){data ->
            Snackbar(
                snackbarData = data,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                actionColor = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium,
            )
        } }
    ) { paddingValues ->
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally){
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Nhập danh mục sản phẩm") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1
            )

            Column(
                modifier = Modifier
            ) {
                Text("Chọn danh mục sản phẩm")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    expanded = true
                    Log.d("DropdownMenu", "Button clicked, expanded = $expanded")
                }) {
                    Text("Chon danh mục")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    },
                    modifier = Modifier.zIndex(1f)
                ) {
                    categoryList.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.title) },
                            onClick = {
                                selectedIngredientId = category.idc
                                expanded = false
                            }
                        )
                    }
                }
            }

            Text("Chọn hình ảnh sản phẩm")
            Spacer(modifier = Modifier.padding(8.dp))
            Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                Text("Chọn ảnh")
            }

            Text("Danh sách ảnh đã chọn")
            AsyncImage(
                model = uploadedImageUrl,
                contentDescription = "Selected or uploaded image",
                modifier = Modifier.size(100.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Button(
                    onClick = {
                        if (title.isNotEmpty()) {
                            apiCallViewModel.updateIngredient(item.iding, title, uploadedImageUrl.toString(), selectedIngredientId) { response ->
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