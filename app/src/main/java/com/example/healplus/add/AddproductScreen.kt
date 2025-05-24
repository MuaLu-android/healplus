package com.example.healplus.add

import android.app.DatePickerDialog
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.core.model.elements.ElementsModel
import com.example.core.model.products.Thanhphan
import com.example.core.model.products.UnitInfo
import com.example.core.viewmodel.apiviewmodel.ApiCallAdd
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import kotlinx.coroutines.launch
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(modifier: Modifier = Modifier,
                     navController: NavController,
                     viewModel: ApiCallViewModel,
                     apiCallAdd: ApiCallAdd) {
    var name by remember { mutableStateOf("") }
    var trademark by remember { mutableStateOf("") }
    val rating by remember { mutableStateOf(5.0) }
    val review by remember { mutableStateOf<Int?>(0) }
    val sold by remember { mutableStateOf<Int?>(0) }
    var expiry by remember { mutableStateOf("") }
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
    var unitInfo by remember { mutableStateOf<List<UnitInfo>>(emptyList()) }
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
        selectedImages = uris
        coroutineScope.launch {
            val urls = uris.mapNotNull {uri ->
                val url = uploadImageToServer(uri, context)
                url
                }
            uploadedImageUrls = urls
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
                        text = "Thêm mới sản phẩm"
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
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text(
                                "Thông tin cơ bản",
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("Tên sản phẩm") },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    unfocusedBorderColor = Color.Gray
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                value = trademark,
                                onValueChange = { trademark = it },
                                label = { Text("Thương hiệu") },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    unfocusedBorderColor = Color.Gray
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                value = preparation,
                                onValueChange = { preparation = it },
                                label = { Text("Dạng điều chế") },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    unfocusedBorderColor = Color.Gray
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                value = specification,
                                onValueChange = { specification = it },
                                label = { Text("Quy cách trên 1 sản phẩm") },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    unfocusedBorderColor = Color.Gray
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                value = quantity,
                                onValueChange = { quantity = it },
                                label = { Text("Số lượng") },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    unfocusedBorderColor = Color.Gray
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                    }

                }
                item {
                    PriceAndUnitSection(
                        price = price,
                        onPriceChange = { price = it },
                        unitInfo = unitInfo,
                        onUnitInfoChange = { unitInfo = it }
                    )
                }
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                "Danh mục sản phẩm",
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = { expanded = true },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp),
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(selectedElementName)
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "Dropdown"
                                    )
                                }
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                            ) {
                                elementsList.forEach { elements ->
                                    DropdownMenuItem(
                                        onClick = {
                                            selectedElementId = elements.ide
                                            selectedElementName = elements.title
                                            expanded = false
                                        },
                                        text = {
                                            Text(elements.title)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                "Hình ảnh sản phẩm",
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = { imagePickerLauncher.launch("image/*") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add Image"
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Chọn ảnh")
                                }
                            }
                            if (uploadedImageUrls.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                ImageListFromUrls(uploadedImageUrls)
                            }
                        }
                    }
                }
                item {
                    ThanhPhanInput(
                        thanhphanList = thanhphan,
                        onThanhPhanChange = { thanhphan = it }
                    )
                }
                item {
                    DatePickerField(
                        "Ngày sản xuất",
                        productionDate,
                        onValueChange = { productionDate = it }
                    )
                }
                item {
                    ProductDetailsCard(
                        "Nguồn gốc",
                        origin,
                        onValueChange = { origin = it }
                    )
                }
                item {
                    ProductDetailsCard(
                        "Nhà sản xuất",
                        manufacturer,
                        onValueChange = { manufacturer = it }
                    )

                }
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                "Hạn sử dụng",
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = expiry,
                                onValueChange = { expiry = it },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text("Thời hạn sử dụng") }
                            )
                        }
                    }
                }
                item {
                    ProductDetailsCard(
                        "Nguyên liệu",
                        ingredient,
                        onValueChange = { ingredient = it },
                        isMultiLine = true
                    )

                }
                item {
                    ProductDetailsCard(
                        "Mô tả ngắn về sản phẩm",
                        description,
                        onValueChange = { description = it },
                        isMultiLine = true
                    )

                }
                item {
                    ProductDetailsCard(
                        "Công dụng",
                        congdung,
                        onValueChange = { congdung = it },
                        isMultiLine = true
                    )

                }
                item {
                    ProductDetailsCard(
                        "Cách dùng",
                        cachdung,
                        onValueChange = { cachdung = it },
                        isMultiLine = true
                    )
                }
                item {
                    ProductDetailsCard(
                        "Tác dụng phụ",
                        tacdungphu,
                        onValueChange = { tacdungphu = it },
                        isMultiLine = true
                    )
                }

                item {
                    ProductDetailsCard(
                        "Cách bảo quản",
                        baoquan,
                        onValueChange = { baoquan = it },
                        isMultiLine = true
                    )
                }
            }
            Button(onClick = {
                apiCallAdd.addProduct(
                    name = name,
                    trademark = trademark,
                    rating = rating.toString(),
                    review = review.toString(),
                    comment = sold.toString(),
                    price = price,
                    expiry = expiry,
                    preparation = preparation,
                    specification = specification,
                    origin = origin,
                    manufacturer = manufacturer,
                    ingredient = ingredient,
                    description = description,
                    quantity = quantity,
                    ide = selectedElementId,
                    productiondate = productionDate,
                    congdung = congdung,
                    cachdung = cachdung,
                    tacdungphu = tacdungphu,
                    baoquan = baoquan,
                    productImages = uploadedImageUrls,
                    thanhphan = thanhphan,
                    unitNames = unitInfo
                ){ it ->
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                navController.navigate("add")

            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    "Thêm sản phẩm",
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

        }
    }
}

@Composable
fun ProductDetailsCard(
    title: String,
    content: String,
    onValueChange: (String) -> Unit,
    isMultiLine: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = content,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                maxLines = if (isMultiLine) 5 else 1,
                shape = RoundedCornerShape(8.dp)
            )
        }
    }
}

@Composable
fun DatePickerField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = value,
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select date"
                        )
                    }
                }
            )
        }
    }

    if (showDatePicker) {
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                onValueChange("$dayOfMonth/${month + 1}/$year")
                showDatePicker = false
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
}

@Composable
fun PriceAndUnitSection(
    price: String,
    onPriceChange: (String) -> Unit,
    unitInfo: List<UnitInfo>,
    onUnitInfoChange: (List<UnitInfo>) -> Unit
) {
    var expandedUnit by remember { mutableStateOf(false) }
    val units = listOf("Hộp", "Viên", "Ống")
    var selectedUnit by remember { mutableStateOf(unitInfo.find { it.isSelected }?.unit_name ?: "Chọn đơn vị") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                "Giá & Đơn vị tính",
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Trường nhập giá
                OutlinedTextField(
                    value = price,
                    onValueChange = onPriceChange,
                    label = { Text("Giá") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(8.dp)
                )

                // Box chứa Dropdown
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(TextFieldDefaults.MinHeight)
                ) {
                    // Nút hiển thị đơn vị đã chọn
                    OutlinedButton(
                        onClick = { expandedUnit = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(TextFieldDefaults.MinHeight),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = selectedUnit,
                                color = if (selectedUnit == "Chọn đơn vị")
                                    Color.Gray else Color.Black
                            )
                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = "Dropdown Arrow"
                            )
                        }
                    }

                    // Menu dropdown
                    DropdownMenu(
                        expanded = expandedUnit,
                        onDismissRequest = { expandedUnit = false },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        units.forEach { unit ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedUnit = unit
                                    // Tạo danh sách mới với đơn vị được chọn
                                    val newUnitInfo = listOf(
                                        UnitInfo(
                                            unit_name = unit,
                                            isSelected = true
                                        )
                                    )
                                    onUnitInfoChange(newUnitInfo)
                                    expandedUnit = false
                                },
                                text = {
                                    Text(
                                        text = unit,
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
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

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()
            .padding(16.dp)) {
            Text("Thành Phần", style = MaterialTheme.typography.titleMedium)
            Button(onClick = { showDialog = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ){
                Icon(Icons.Default.Add, contentDescription = "Thêm thành phần")
                Text("Thêm Thành Phần")
            }
            Column (
                modifier = Modifier.fillMaxWidth()
            ){
                thanhphanList.forEach { tp ->
                    ThanhPhanItem(tp) {
                        onThanhPhanChange(thanhphanList.filter { it != tp })
                    }
                }
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
