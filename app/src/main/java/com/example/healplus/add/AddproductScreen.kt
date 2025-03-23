package com.example.healplus.add

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.core.viewmodel.authviewmodel.AuthViewModel


@Composable
fun AddProductScreen(modifier: Modifier = Modifier,
                     navController: NavController,
                     authViewModel: AuthViewModel) {
    var name by remember { mutableStateOf("") }
    var trademark by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var categoryItemName by remember { mutableStateOf("") }
    var preparation by remember { mutableStateOf("") }
    var specification by remember { mutableStateOf("") }
    var origin by remember { mutableStateOf("") }
    var manufacturer by remember { mutableStateOf("") }
    var production by remember { mutableStateOf("") }
    var ingredient by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var quanty by remember { mutableStateOf("") }
    var ptofile_images by remember { mutableStateOf("") }
    var units_name by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var localImageUrl by remember { mutableStateOf<String?>("") }
    val role by remember { mutableStateOf<String?>("user") }
    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        localImageUrl = uri?.toString()?:"" // Lưu đường dẫn ảnh cục bộ
    }
    Scaffold (
        topBar = {
        }
    ){paddingValues ->
        LazyColumn (
            modifier = Modifier
                .padding(horizontal = 16.dp),
            contentPadding = paddingValues
        ){
            item {
                TextFieldProduct("Nhập tên sản phẩm",
                    name){it ->
                    name = it
                }
            }
            item {
                TextFieldProduct("Thuộc thương hiệu",
                    trademark){it ->
                    trademark = it
                }
            }
            item {
                TextFieldNumberProduct("Nhập giá sản phẩm",
                    price){it ->
                    price = it
                }
            }
            item {
                TextFieldProduct("Dạng điều chế",
                    preparation){it ->
                    preparation = it
                }
            }
            item {
                TextFieldProduct("Đặc điểm",
                    specification){it ->
                    specification = it
                }
            }
            item {
                TextFieldProduct("Nguồn gốc",
                    origin){it ->
                    origin = it
                }
            }
            item {
                TextFieldProduct("Nhà sản xuất",
                    manufacturer){it ->
                    manufacturer = it
                }
            }
            item {
                TextFieldProduct("Xuất xứ",
                    production){it ->
                    production = it
                }
            }
            item {
                TextFieldProductMultiLine("Nguyên liệu",
                    ingredient){it ->
                    ingredient = it
                }
            }
            item {
                TextFieldProductMultiLine("Mô tả",
                    description){it ->
                    description = it
                }
            }
        }
    }
}

@Composable
fun TextFieldProductMultiLine(title: String,
                              content: String,
                              onValueChange: (String) -> Unit) {
    Column {
        TextField(
            value = content,
            onValueChange = { onValueChange(it) },
            label = { Text(title) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 10
        )
        Spacer(modifier = Modifier
            .padding(8.dp))
    }
}

@Composable
fun TextFieldNumberProduct(title: String,
                           content: String,
                           onValueChange: (String) -> Unit) {
    Column {
        TextField(
            value = content,
            onValueChange = { if (it.all { char -> char.isDigit() }) onValueChange(it) },
            label = { Text(title) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier
            .padding(8.dp))
    }
}

@Composable
fun TextFieldProduct(title: String, content: String,
                     onValueChange: (String) -> Unit) {
    Column {
        TextField(
            value = content,
            onValueChange = { onValueChange(it) },
            label = { Text(title) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier
            .padding(8.dp))
    }

}
