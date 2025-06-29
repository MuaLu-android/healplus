package com.example.healplus.feature.personalization.profiles

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.R
import com.google.gson.Gson

@Composable
fun ProfileScreen(viewModel: AuthViewModel, navController: NavController) {
    val user by viewModel.user.observeAsState()
    LaunchedEffect(Unit) {
        viewModel.getCurrentUser()
    }
    Scaffold(
        topBar = {
            ProfileTopAppBar("Thông tin tài khoản", navController)
        }

    ) {paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)) {
            user?.let { userData ->
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = rememberAsyncImagePainter(userData.url),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray),
                        contentScale = ContentScale.Crop
                    )
                }
                InfoRow(label = "Họ và tên", value = userData.name)
                InfoRow(label = "Số điện thoại", value = userData.phone)
                InfoRow(label = "Giới tính", value = userData.gender)
                InfoRow(label = "Ngày sinh", value = userData.dateBirth)
                Spacer(modifier = Modifier.weight(1f))
                ProfileBottomAppBar(onClick = {
                    navController.navigate("editProfile/${Uri.encode(Gson().toJson(userData))}")
                })
            } ?: Text(text = "Không có dữ liệu người dùng")
        }
    }
}

@Composable
fun ProfileBottomAppBar(onClick: () -> Unit) {
        IconButton(
            onClick = {
                onClick()
            },
            modifier = Modifier
                .padding(top = 16.dp)
                .height(56.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.edit_24px),
                contentDescription = null
            )
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopAppBar(title: String, navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null)
            }
        }
    )
}

@Composable
fun InfoRow(label: String, value: String) {
    Column (
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, fontWeight = FontWeight.Bold)
            Text(text = value, fontWeight = FontWeight.Light)
        }
        Box{
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
        }
    }
}