package com.example.healplus.settings

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healplus.R
import java.util.Calendar


@Composable
@Preview
fun UpdateProfileScreen(){
    var fullName by remember { mutableStateOf("Lu200312") }
    var gender by remember { mutableStateOf("Nam") }
    val phoneNumber = "0355 761 327" // Trường không cho chỉnh sửa
    var birthDate by remember { mutableStateOf("09/07/2003") }
    var showDatePicker by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePicker = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                birthDate = "$dayOfMonth/${month + 1}/$year"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Ảnh đại diện
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.intro_logo), // Thay bằng ảnh của bạn
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )
            Text(
                text = "Thay đổi",
                color = Color.Blue,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .clickable { /* Mở thư viện ảnh */ }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Họ và tên
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Họ và tên") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Giới tính
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Giới tính")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = gender == "Nam",
                onClick = { gender = "Nam" }
            )
            Text("Nam")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = gender == "Nữ",
                onClick = { gender = "Nữ" }
            )
            Text("Nữ")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Số điện thoại (chỉ hiển thị, không chỉnh sửa)
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {},
            label = { Text("Số điện thoại") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Ngày sinh
        OutlinedTextField(
            value = birthDate,
            onValueChange = {},
            label = { Text("Ngày sinh") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Chọn ngày",
                    modifier = Modifier.clickable { showDatePicker = true }
                )
            },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        // Nút cập nhật thông tin
        Button(
            onClick = { /* Xử lý cập nhật thông tin */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(Color.Blue)
        ) {
            Text("Cập nhật thông tin", color = Color.White)
        }
        // Hiển thị DatePicker khi người dùng chọn ngày
        if (showDatePicker) {
            datePicker.show()
            showDatePicker = false
        }
    }
}