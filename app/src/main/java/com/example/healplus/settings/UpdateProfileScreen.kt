package com.example.healplus.settings

import android.app.DatePickerDialog
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.core.model.users.UserAuthModel
import com.example.core.viewmodel.apiviewmodel.ApiCallAdd
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.R
import com.example.healplus.add.uploadImageToServer
import kotlinx.coroutines.launch
import java.util.Calendar


@Composable
fun UpdateProfileScreen(
    item: UserAuthModel,
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    apiCallViewModel: ApiCallViewModel = viewModel()
) {
    var fullName by remember { mutableStateOf(item.name) }
    var email by remember { mutableStateOf(item.email) }
    var gender by remember { mutableStateOf(item.gender) }
    val phoneNumber by remember { mutableStateOf(item.phone) }
    var urlimg by remember { mutableStateOf(item.url) }
    var birthDate by remember { mutableStateOf(item.dateBirth) }
    var showDatePicker by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
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
    val userid = authViewModel.getUserId()
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            coroutineScope.launch {
                val url = uploadImageToServer(uri, context)
                if (url != null) {
                    urlimg = url
                }
            }
        } else {
            Log.d("AddProductScreen", "No image selected (user cancelled).")
        }
    }
    Scaffold(
        topBar = {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(urlimg),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Thay đổi",
                        color = Color.Blue,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .clickable { imagePickerLauncher.launch("image/*") }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Họ và tên") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

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
            OutlinedTextField(
                value = email,
                onValueChange = {},
                label = { Text("Email: ") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
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
            Button(
                onClick = {
                    apiCallViewModel.updateUser(
                        name = fullName,
                        email = email,
                        gender = gender,
                        phone = phoneNumber,
                        url = urlimg,
                        dateBirth = birthDate,
                        idauth = userid.toString()
                    )
                    authViewModel.updateUserAccount(
                        name = fullName,
                        email = email,
                        gender = gender,
                        phone = phoneNumber,
                        uploadedImageUrl = urlimg,
                        dateBirth = birthDate,
                        onComplete = { success, message ->
                            if (success) {
                                Toast.makeText(
                                    context,
                                    "Cập nhật tài khoản thành công!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Lỗi cập nhật: $message",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )
                },
                modifier = Modifier
                    .height(50.dp)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(Color.Blue)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.save_24px),
                    contentDescription = "Save",
                    tint = Color.White
                )
            }
            if (showDatePicker) {
                datePicker.show()
                showDatePicker = false
            }
        }
    }
}