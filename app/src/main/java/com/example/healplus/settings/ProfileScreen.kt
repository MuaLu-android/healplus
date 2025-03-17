package com.example.healplus.settings

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.skydoves.landscapist.glide.GlideImage
import com.example.core.viewmodel.authviewmodel.AuthViewModel

@Composable
fun UserProfileScreen(viewModel: AuthViewModel) {
    val user by viewModel.user.observeAsState()

    // Gọi hàm lấy dữ liệu khi màn hình mở
    LaunchedEffect(Unit) {
        viewModel.getCurrentUser()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        user?.let { userData ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val imageUri = userData.localImageUrl?.let { Uri.parse(it) }
                Log.d("UserProfileScreen", "localImageUrl: ${userData.localImageUrl}")
                Log.d("UserProfileScreen", "Parsed imageUri: $imageUri")

                GlideImage(
                    imageModel = { imageUri }, // Glide hỗ trợ content://
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )
                Text(text = "Thay đổi ảnh đại diện", color = Color.Blue)
            }

            InfoRow(label = "Họ và tên", value = userData.fullName)
            InfoRow(label = "Số điện thoại", value = userData.phoneNumber)
//            InfoRow(label = "Giới tính", value = userData.gender)
//            InfoRow(label = "Ngày sinh", value = userData.birthDate)
        } ?: Text(text = "Không có dữ liệu người dùng")
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Bold)
        Text(text = value, fontWeight = FontWeight.Light)
    }
}