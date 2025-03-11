package com.example.healplus.Screen.HomeScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.healplus.Model.CategoryModel
import com.example.healplus.R
import com.example.healplus.ViewModel.CategoryProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediumTopAppBar(navController: NavController,
                    categories: List<CategoryModel>,
                    showCategoryLoading: Boolean) {
    var expanded by remember { mutableStateOf(false) }
    var seach by remember { mutableStateOf("") }

    TopAppBar(
        title = {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(30.dp))
                    .background(Color.White, shape = RoundedCornerShape(30.dp))
            ) {
                val (tvIcon, tvTxt) = createRefs()
                IconButton(onClick = { /* Mở danh sách quốc gia */ },
                    modifier = Modifier
                        .constrainAs(tvIcon) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start, margin = 2.dp)
                        }
                ) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Select Country"
                    )
                }
                BasicTextField(
                    value = seach,
                    onValueChange = { seach = it },
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .fillMaxWidth()
                        .constrainAs(tvTxt) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(tvIcon.end, margin = 2.dp)
                        },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Start // Căn
                    )
                )
            }
        },
        navigationIcon = {
            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
                }
            }
        },
        actions = {
            IconButton(onClick = { }) {
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
            .offset(x = 0.dp, y = (56).dp) // Tùy chỉnh vị trí nếu cần
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
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.title) },
                    onClick = {
                        navController.navigate("home")
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryListTopBar(categories: List<CategoryModel>, navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 16.dp, end = 16.dp, top = 8.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.
            fillMaxSize()
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
    Row (modifier = Modifier
        .padding(top = 8.dp)){
        Text(
            text = item.title
        )
        IconButton(
            onItemClick
        ) {
            Icon(imageVector = Icons.Default.Check,
                contentDescription = null)
        }
    }
}

