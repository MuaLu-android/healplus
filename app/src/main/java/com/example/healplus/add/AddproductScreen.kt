package com.example.healplus.add

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.core.model.elements.ElementsModel
import com.example.core.model.products.Thanhphan
import com.example.core.viewmodel.apiviewmodel.ApiCallAdd
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(modifier: Modifier = Modifier,
                     navController: NavController,
                     viewModel: ApiCallViewModel,
                     apiCallAdd: ApiCallAdd) {
    var name by remember { mutableStateOf("") }
    var trademark by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(5.0) }
    var review by remember { mutableStateOf<Int?>(0) }
    val showRecommended by remember { mutableStateOf<Int?>(0) }
    var comment by remember { mutableStateOf<Int?>(0) }
    var price by remember { mutableStateOf("") }
    var preparation by remember { mutableStateOf("") }
    var specification by remember { mutableStateOf("") }
    var origin by remember { mutableStateOf("") }
    var manufacturer by remember { mutableStateOf("") }
    var ingredient by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var productionDate by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var congdung by remember { mutableStateOf("") }
    var cachdung by remember { mutableStateOf("") }
    var tacdungphu by remember { mutableStateOf("") }
    var baoquan by remember { mutableStateOf("") }
    var thanhphan by remember { mutableStateOf<List<Thanhphan>>(emptyList()) }
    var unitInfo by remember { mutableStateOf<List<String>>(emptyList()) }
    var uploadedImageUrls by remember { mutableStateOf<List<String>>(emptyList()) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var selectedImages by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val elementsList = remember { mutableStateListOf<ElementsModel>() }
    var selectedElementId by remember { mutableStateOf<String>("") }
    var selectedElementName by remember { mutableStateOf<String>("Chọn danh mục") }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        Log.d("AddProductScreen", "Selected image URIs: $uris")
        selectedImages = uris
        if (uris.isNotEmpty()) {
            Log.d("AddProductScreen", "Starting to upload images.")
        } else {
            Log.d("AddProductScreen", "No images selected.")
        }
        coroutineScope.launch {
            val urls = uris.mapNotNull {uri ->
                Log.d("AddProductScreen", "Uploading image: $uri")
                val url = uploadImageToServer(uri, context)
                Log.d("AddProductScreen", "Uploaded image URL: $url")
                url
                }
            uploadedImageUrls = urls
            Log.d("AddProductScreen", "Uploaded image URLs: $uploadedImageUrls")
        }
    }
    var expanded by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
            viewModel.loadElement()
            viewModel.element.observeForever {
                elementsList.clear()
                elementsList.addAll(it)
        }
    }
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "App Product"
                    )
                }
            )
        }
    ){paddingValues ->

        Column (modifier = Modifier
            .padding(paddingValues)){
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    TextFieldProduct(
                        "Nhập tên sản phẩm", name) { name = it }
                }
                item {
                    TextFieldProduct(
                        "Thuộc thương hiệu",
                        trademark
                    ) { it ->
                        trademark = it
                    }
                }
                item {
                    TextFieldNumberProduct(
                        "Nhập giá sản phẩm",
                        price
                    ) { it ->
                        price = it
                    }
                }
                item {
                    TextFieldNumberProduct(
                        "Số lượng",
                        quantity
                    ) { it ->
                        quantity = it
                    }
                }
                item {
                    Column(
                        modifier = Modifier
                    ) {
                        Text("Chọn danh mục sản phẩm")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = {
                            expanded = true
                            Log.d("DropdownMenu", "Button clicked, expanded = $expanded")
                        }) {
                            Text(selectedElementName)
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = {
                                expanded = false
                            },
                            modifier = Modifier.zIndex(1f)
                        ) {
                            elementsList.forEach { elements ->
                                DropdownMenuItem(
                                    text = { Text(elements.title) },
                                    onClick = {
                                        selectedElementId = elements.ide
                                        selectedElementName = elements.title
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
                item {
                    TextFieldProduct(
                        "Dạng điều chế",
                        preparation
                    ) { it ->
                        preparation = it
                    }
                }
                item {
                    ThanhPhanInput(
                        thanhphanList = thanhphan,
                        onThanhPhanChange = { thanhphan = it }
                    )
                }
                item {
                    TextFieldProduct(
                        "Đặc điểm",
                        specification
                    ) { it ->
                        specification = it
                    }
                }
                item {
                    TextFieldProduct(
                        "Nguồn gốc",
                        origin
                    ) { it ->
                        origin = it
                    }
                }
                item {
                    TextFieldProduct(
                        "Nhà sản xuất",
                        manufacturer
                    ) { it ->
                        manufacturer = it
                    }
                }
                item {
                    TextFieldProduct(
                        "Ngày sản xuất",
                        productionDate
                    ) { it ->
                        productionDate = it
                    }
                }
                item {
                    TextFieldProductMultiLine(
                        "Nguyên liệu",
                        ingredient
                    ) { it ->
                        ingredient = it
                    }
                }
                item {
                    TextFieldProductMultiLine(
                        "Mô tả",
                        description
                    ) { it ->
                        description = it
                    }
                }
                item {
                    TextFieldProductMultiLine(
                        "Công dụng",
                        congdung
                    ) { it ->
                        congdung = it
                    }
                }
                item {
                    TextFieldProductMultiLine(
                        "Cách dùng",
                        cachdung
                    ) { it ->
                        cachdung = it
                    }
                }
                item {
                    TextFieldProductMultiLine(
                        "Tác dụng phụ",
                        tacdungphu
                    ) { it ->
                        tacdungphu = it
                    }
                }
                item {
                    TextFieldProductMultiLine(
                        "Cách bảo quản",
                        baoquan
                    ) { it ->
                        baoquan = it
                    }
                }
                item {
                    Text("Chọn hình ảnh sản phẩm")
                    Spacer(modifier = Modifier.padding(8.dp))
                    Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                        Text("Chọn ảnh")
                    }
                }
                item {
                    Text("Danh sách ảnh đã chọn")
                    ImageListFromUrls(uploadedImageUrls)
                }


            }
            Button(onClick = {
                apiCallAdd.addProduct(
                    name = name,
                    trademark = trademark,
                    rating = rating.toString(),
                    review = review.toString(),
                    comment = comment.toString(),
                    price = price,
                    preparation = preparation,
                    specification = specification,
                    origin = origin,
                    manufacturer = manufacturer,
                    ingredient = ingredient,
                    description = description,
                    quantity = quantity,
                    showRecommended = showRecommended.toString(),
                    ide = selectedElementId,
                    productiondate = productionDate,
                    congdung = congdung,
                    cachdung = cachdung,
                    tacdungphu = tacdungphu,
                    baoquan = baoquan,
                    productImages = uploadedImageUrls,
                    thanhphan = thanhphan,
                    unitNames = unitInfo
                )
                navController.navigate("add")

            },
                modifier = Modifier
                    .fillMaxWidth()) {
                Text("Add")
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldProduct(title: String, content: String,
                     onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = content,
            onValueChange = { onValueChange(it) },
            label = { Text(title) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent, RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Gray,
                unfocusedIndicatorColor = Color.Gray
            ),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

}
@Composable
fun ImageListFromUrls(imageUrls: List<String>) {
    LazyRow {
        items(imageUrls.size) { index ->
            AsyncImage(
                model = imageUrls[index],
                contentDescription = "Uploaded Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(8.dp)
            )
        }
    }
}
@Composable
fun ThanhPhanInput(
    thanhphanList: List<Thanhphan>,
    onThanhPhanChange: (List<Thanhphan>) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var newThanhPhan by remember { mutableStateOf(Thanhphan("", "")) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Thành Phần", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        Column { // Thay LazyColumn bằng Column
            thanhphanList.forEach { tp -> // Sử dụng forEach thay vì items
                ThanhPhanItem(tp) {
                    onThanhPhanChange(thanhphanList.filter { it != tp })
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { showDialog = true }) {
            Icon(Icons.Default.Add, contentDescription = "Thêm thành phần")
            Text("Thêm Thành Phần")
        }

        if (showDialog) {
            ThanhPhanDialog(
                thanhphan = newThanhPhan,
                onThanhPhanChange = { newThanhPhan = it },
                onDismiss = { showDialog = false },
                onSave = {
                    onThanhPhanChange(thanhphanList + newThanhPhan)
                    newThanhPhan = Thanhphan("", "")
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun ThanhPhanItem(thanhphan: Thanhphan, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier
            .weight(0.6f)
            .padding(horizontal = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = thanhphan.title, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa title và vạch
            VerticalDivider() // Thêm vạch ngăn cách
            Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa vạch và body
            Text(text = thanhphan.body, style = MaterialTheme.typography.bodyMedium)
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Close, contentDescription = "Xóa")
        }
    }
}
@Composable
fun VerticalDivider() {
    Canvas(modifier = Modifier
        .height(24.dp) // Chiều cao của vạch
        .width(1.dp)) { // Độ dày của vạch
        drawLine(
            color = Color.Gray, // Màu của vạch
            start = Offset(0f, 0f),
            end = Offset(0f, size.height),
            strokeWidth = 1f
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThanhPhanDialog(
    thanhphan: Thanhphan,
    onThanhPhanChange: (Thanhphan) -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Thêm Thành Phần") },
        text = {
            Column {
                TextField(
                    value = thanhphan.title,
                    onValueChange = { onThanhPhanChange(thanhphan.copy(title = it)) },
                    label = { Text("Tên Thành Phần") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = thanhphan.body,
                    onValueChange = { onThanhPhanChange(thanhphan.copy(body = it)) },
                    label = { Text("Mô Tả") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = onSave) {
                Text("Lưu")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Hủy")
            }
        }
    )
}
