package com.example.healplus.oder
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.core.model.Oder.Order
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.R
@Composable
fun UsersOder(navController: NavController,
              apiCallViewModel: ApiCallViewModel = viewModel(),
              authViewModel: AuthViewModel = viewModel()){
    val userId = authViewModel.getUserId().toString()
    apiCallViewModel.getOderByUser(userId)
    val allOrders by apiCallViewModel.orders.observeAsState(initial = emptyList())
    var selectedStatus by remember { mutableStateOf<String?>(null) }
    val filteredOrders = remember(allOrders, selectedStatus) {
        if (selectedStatus == null) {
            allOrders
        } else {
            val filtered = allOrders.filter { it.status == selectedStatus.toString() }
            filtered
        }
    }
    LaunchedEffect(selectedStatus) {
        if (selectedStatus != null){
            apiCallViewModel.getOderByUserStatus(userId, selectedStatus.toString())
        }else{
            apiCallViewModel.getOderByUser(userId)
        }
    }
    Scaffold(
        topBar = {
            UserOderManagersTopAppBar(navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            val statusesToFilter = listOf("Tất cả", "Đang chờ xử lý", "Đang vận chuyển", "Đã giao hàng", "Đã hủy")
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
                        isSelected = selectedStatus == status || (selectedStatus == null && status == "Tất cả"),
                        onStatusSelected = { selected ->
                            selectedStatus = if (selected == "Tất cả") null else selected
                        }
                    )
                }
            }
            if (filteredOrders.isEmpty()) {
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
                    items(filteredOrders, key = { order -> order.id }) { order ->
                        UserOrderItemCard(navController, order)
                    }
                }
            }
        }
    }
}
@Composable
fun UserOrderItemCard(navController: NavController, order: Order) {
    var status1 by remember { mutableStateOf(order.status ?: "Đang chờ xử lí") }
    var showProducts by remember { mutableStateOf(false) }
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
            Text(text = "Đơn hàng số ${order.id}", fontWeight = FontWeight.Bold, fontSize = 18.sp,
                textAlign = TextAlign.Center, color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Họ và tên: ${order.name}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Số điện thoại: ${order.phone}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Địa chỉ giao hàng: ${order.address}", fontSize = 14.sp, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Tổng tiền: ${order.sumMoney} VND", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF4CAF50)) // Màu xanh lá cây
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Trạng thái: ${status1}", fontSize = 14.sp, color = Color.DarkGray)
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
                Text(text = "Danh sách sản phẩm:", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                Icon(
                    imageVector = if (showProducts) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (showProducts) "Thu gọn danh sách sản phẩm" else "Mở rộng danh sách sản phẩm"
                )
            }
            if (showProducts) {
                Spacer(modifier = Modifier.height(4.dp))
                order.items.forEach { item ->
                    ProductOrderItem(navController, item = item)
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserOderManagersTopAppBar(navController: NavController) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.userodermanages),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Quay lại"
                )
            }
        }
    )
}
