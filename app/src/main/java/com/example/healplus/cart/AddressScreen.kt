package com.example.healplus.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.core.model.address.AddressModel
import com.example.core.tinydb.helper.AddAddress
import com.example.core.tinydb.helper.ManagmentCart
import com.example.core.viewmodel.authviewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreen(navController: NavController,
                  authViewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current
    val user by authViewModel.user.observeAsState()
    val userId = authViewModel.getUserId().toString()
    val addAddress = remember { AddAddress(context, userId) }
    var province by remember { mutableStateOf("") }
    var addressDetail by remember { mutableStateOf("") }
    var isDefault by remember { mutableStateOf(false) }
    var selectedType by remember { mutableStateOf("Nhà") } // Loại địa chỉ
    LaunchedEffect(Unit) {
        authViewModel.getCurrentUser()
    }
    Scaffold(
    topBar = {
        TopAppBar(
            title = { Text("Thêm địa chỉ mới") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại", tint = Color.White)
                }
            }
        )
    }
    ) { paddingValues ->
        user?.let { user ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text("Thông tin liên hệ", fontWeight = FontWeight.Bold, fontSize = 16.sp)


                OutlinedTextField(
                    value = user.fullName,
                    onValueChange = { },
                    label = { Text("Họ và tên") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = user.phoneNumber,
                    onValueChange = { },
                    label = { Text("Số điện thoại") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth()
                )


                Spacer(modifier = Modifier.height(16.dp))

                Text("Địa chỉ nhận hàng", fontWeight = FontWeight.Bold, fontSize = 16.sp)

                OutlinedTextField(
                    value = province,
                    onValueChange = { province = it },
                    label = { Text("Chọn Tỉnh/Thành phố, Quận/Huyện...") },
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Chọn địa chỉ")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = addressDetail,
                    onValueChange = { addressDetail = it },
                    label = { Text("Nhập tên đường, tòa nhà, số nhà") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Loại địa chỉ", fontWeight = FontWeight.Bold, fontSize = 16.sp)

                Row {
                    Button(
                        onClick = { selectedType = "Nhà" },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedType == "Nhà") Color.Blue else Color.LightGray
                        ),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Nhà", color = Color.White)
                    }

                    Button(
                        onClick = { selectedType = "Văn phòng" },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedType == "Văn phòng") Color.Blue else Color.LightGray
                        )
                    ) {
                        Text("Văn phòng", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Đặt làm địa chỉ mặc định")
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(checked = isDefault, onCheckedChange = { isDefault = it })
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val newAddress = AddressModel(
                            user.fullName,
                            user.phoneNumber,
                            user.email,
                            province,
                            addressDetail,
                            selectedType,
                            isDefault
                        )
                        addAddress.insertFood(newAddress)
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text("Hoàn tất", color = Color.White)
                }
            }
        }
    }
}