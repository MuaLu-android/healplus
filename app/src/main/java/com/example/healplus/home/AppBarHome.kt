package com.example.core.ui.home
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.core.model.categories.CategoryModel
import com.example.core.viewmodel.authviewmodel.AuthSate
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.R
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun AddProducts(title: String,
                onItemClick: () -> Unit
){
    Row {
        Text(title)
        IconButton(
            onClick = onItemClick
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediumTopAppBar(navController: NavController,
                    categories: List<CategoryModel>,
                    showCategoryLoading: Boolean,
                    viewModel: AuthViewModel) {
    var expanded by remember { mutableStateOf(false) }


    // Gọi hàm lấy dữ liệu khi màn hình mở
    LaunchedEffect(Unit) {
        viewModel.getCurrentUser()
    }
    TopAppBar(
        title = {

        },
        navigationIcon = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),// Thêm padding để tránh sát mép
                verticalAlignment = Alignment.CenterVertically // Căn giữa theo trục dọc
            ) {
                IconButton(
                    onClick = { expanded = true },
                    modifier = Modifier
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu, contentDescription = null,
                        modifier = Modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.logo_group_app1),
                    contentDescription = "logo_app",
                    modifier = Modifier
                        .size(120.dp)
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start)
                )
            }

        },
        actions = {
            IconButton(onClick = {
                navController.navigate("search")
            }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
            }
            IconButton(onClick = {
                navController.navigate("add")
            }) {
                Icon(imageVector = Icons.Filled.Notifications, contentDescription = null)
            }
        }
    )
    // DropdownMenu nằm ngoài TopAppBar để hiển thị lên trên
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .zIndex(2f) // Đưa menu lên trên các thành phần khác
            .fillMaxHeight()
            .offset(x = 0.dp, y = -5.dp) // Tùy chỉnh vị trí nếu cần
    ) {
        if (showCategoryLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(Color.Yellow)
                ) {
                    Column {
                        UserView(viewModel, navController)
                        Notification1("Thông báo", navController)
                    }
                }
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.title) },
                        onClick = {
                            navController.navigate("category/${category.idc}/${category.title}")
                            expanded = false
                        }
                    )
                }
            }

        }
    }
}

@Composable
fun Notification1(tile: String,
                  navController: NavController) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(tile)
        IconButton(
            onClick = {navController.navigate("notification")}
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
            )
        }
    }
}

@Composable
fun UserView(viewModel: AuthViewModel, navController: NavController) {
    val user by viewModel.user.observeAsState()
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(top = 40.dp)
        .clickable {
            navController.navigate("profile")
        },
        verticalAlignment = Alignment.CenterVertically) {
        user?.let { userData ->
            val imageUri = userData.url?.let { Uri.parse(it) }
            Log.d("UserProfileScreen", "localImageUrl: ${userData.url}")
            Log.d("UserProfileScreen", "Parsed imageUri: $imageUri")
            GlideImage(
                imageModel = { imageUri }, // Glide hỗ trợ content://
                modifier = Modifier
                    .padding(10.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )
        Column (
            modifier = Modifier
                .padding(horizontal = 10.dp)
        ){
            InfoRow(value = userData.name)
            InfoRow(value = userData.phone)
            }
        }
    }
}
@Composable
fun InfoRow(value: String) {
    Text(text = value, fontWeight = FontWeight.Light,
        modifier = Modifier.padding(bottom = 4.dp))
}
@Composable
fun CategoryListTopBar(categories: List<CategoryModel>, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(categories.size) { index ->
                val item = categories[index]
                CategoryItemTopBar(
                    item = item,
                    onItemClick = {
//                            selectedIndex = index
//                            oPenListCategory(item.iding ?: "unknown", item.title ?: "No title")
                        navController.navigate("home")
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryItemTopBar(item: CategoryModel, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(top = 8.dp)
    ) {
        Text(
            text = item.title
        )
        IconButton(
            onItemClick
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null
            )
        }
    }
}


