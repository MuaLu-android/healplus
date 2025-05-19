package com.example.healplus.oder

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.core.model.Oder.Order
import com.example.core.model.products.ProductsModel
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import com.example.healplus.R
import com.google.gson.Gson
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

@Composable
fun OderManagers(
    navController: NavController
) {
    val apiCallViewModel = remember { ApiCallViewModel() }
    val allOrders by apiCallViewModel.orders.observeAsState(initial = emptyList())
    var selectedStatus by remember { mutableStateOf<String?>("Tất cả") }
    LaunchedEffect(selectedStatus) {
        if (selectedStatus == "Tất cả" || selectedStatus == null) {
            apiCallViewModel.loadOder()
        } else {
            apiCallViewModel.loadOderStatus(selectedStatus.toString())
        }
    }
    Scaffold(
        topBar = {
            OderManagersTopAppBar(navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            val statusesToFilter =
                listOf("Tất cả", "Đang chờ xử lý", "Đang vận chuyển", "Đã giao hàng", "Đã hủy")
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(statusesToFilter) { status ->
                    StatusFilterChip(
                        status = status,
                        isSelected = selectedStatus == status,
                        onStatusSelected = { selected ->
                            selectedStatus =  selected
                        }
                    )
                }
            }
            if (allOrders.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Không có đơn hàng nào.", fontSize = 18.sp, color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(allOrders, key = { order -> order.id }) { order ->
                        OrderItemCard(navController, order, apiCallViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun StatusFilterChip(
    status: String,
    isSelected: Boolean,
    onStatusSelected: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .clickable { onStatusSelected(status) }
            .border(
                border = BorderStroke(
                    1.dp,
                    if (isSelected) Color.Blue else Color.Gray
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = if (isSelected) Color.Blue.copy(alpha = 0.1f) else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .height(30.dp)
    ) {
        Text(
            text = status,
            color = if (isSelected) Color.Blue else Color.Gray,
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun OrderItemCard(
    navController: NavController, order: Order,
    apiCallViewModel: ApiCallViewModel
) {
    var expanded by remember { mutableStateOf(false) }
    var status1 by remember { mutableStateOf(order.status ?: "Đang chờ xử lí") }
    var showProducts by remember { mutableStateOf(false) }
    val statuses = listOf("Đang chờ xử lý", "Đang vận chuyển", "Đã giao hàng", "Đã hủy")
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = "Đơn hàng số ${order.id}", fontWeight = FontWeight.Bold, fontSize = 18.sp,
                textAlign = TextAlign.Center, color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = order.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = order.phone, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(
                    text = order.datetime.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Đ/c: ${order.address}", fontSize = 14.sp, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Tiền: ${NumberFormat.getCurrencyInstance(Locale("vi", "VN")).format(order.sumMoney)}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Blue,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Trạng thái: ${status1}", fontSize = 14.sp, color = Color.DarkGray)
                Box {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Tùy chọn đơn hàng"
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        statuses.forEach { status ->
                            DropdownMenuItem(
                                text = { Text(status) },
                                onClick = {
                                    apiCallViewModel.updateOrderStatus(order.id, status)
                                    status1 = status
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider(thickness = 0.5.dp, color = Color.LightGray)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showProducts = !showProducts }
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Danh sách sản phẩm:",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Icon(
                    imageVector = if (showProducts) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (showProducts) "Thu gọn danh sách sản phẩm" else "Mở rộng danh sách sản phẩm"
                )
            }
            if (showProducts) {
                Spacer(modifier = Modifier.height(8.dp))
                order.items.forEach { item ->
                    ProductOrderItem(navController, item = item)
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Composable
fun ProductOrderItem(navController: NavController, item: ProductsModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("detail/${Uri.encode(Gson().toJson(item))}") },
        verticalAlignment = Alignment.CenterVertically
    ) {
        val imageUrl = item.product_images?.firstOrNull()
        if (!imageUrl.isNullOrEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = item.name,
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 8.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 8.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text("No Image", fontSize = 10.sp)
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.name, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = NumberFormat.getCurrencyInstance(Locale("vi", "VN")).format(item.price), fontSize = 12.sp, color = Color.DarkGray)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OderManagersTopAppBar(navController: NavController) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.odermanages),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) { // Sử dụng IconButton cho navigationIcon
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Quay lại" // Thêm contentDescription cho khả năng tiếp cận
                )
            }
        }
    )
}
