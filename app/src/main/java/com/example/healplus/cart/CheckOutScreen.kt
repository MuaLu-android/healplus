package com.example.healplus.cart

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healplus.R
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.core.model.address.PaymentMethod
import com.example.core.model.products.ProductsModel
import com.example.core.tinydb.helper.AddAddress
import com.example.core.tinydb.helper.ChangeNumberItemsListener
import com.example.core.tinydb.helper.ManagmentCart
import com.example.core.viewmodel.apiviewmodel.ApiCallAdd
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.google.gson.Gson
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun CheckOutScreen(
    navController: NavController,
    selectedProducts: List<ProductsModel>,
    totalAmount: Double,
    tax: Double,
    quantity: Int,
    authViewModel: AuthViewModel = viewModel(),
    apiCallOder: ApiCallAdd = viewModel()
) {
    val userId = authViewModel.getUserId().toString()
    val context = LocalContext.current
    var address = remember { AddAddress(context, userId) }
    val managementCart = remember { ManagmentCart(context, userId) }
    var addressItems = remember { mutableStateOf(address.getListAddressOder() ?: arrayListOf()) }
    val paymentMethods = listOf(
        PaymentMethod("Thanh toán tiền mặt khi nhận hàng", R.drawable.payments_24px),
        PaymentMethod("Thanh toán bằng ví ZaloPay", R.drawable.pay_zalo),
        PaymentMethod("Thanh toán bằng ví MoMo", R.drawable.pay_momo)
    )
    var selectedMethod by rememberSaveable { mutableStateOf(paymentMethods[0]) }
    var note by remember { mutableStateOf("") }
    var saleAmount by remember { mutableStateOf(0) }
    val currentDateAndTime = LocalDate.now()
    var email by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(Unit) {
        email = authViewModel.getSuspendingEmail()
    }
    Scaffold(
        topBar = {
            OderTopAppBar(navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Giao hàng tới", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        IconButton(onClick = { navController.navigate("address") }) {
                            Icon(Icons.Filled.Add, contentDescription = "Edit Address")
                        }
                    }
                }
                items(addressItems.value) { item ->
                    Column {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = item.addressType,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(text = "${item.addressDetail}, ${item.province}")
                        Text("${item.fullName} | ${item.phoneNumber}")
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                item {
                    OutlinedTextField(
                        value = note,
                        onValueChange = { note = it },
                        placeholder = { Text("Thêm ghi chú...") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Danh sách sản phẩm", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Column {
                        selectedProducts.forEach { product ->
                            ProductItem(product)
                        }
                    }
                }
                item {
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))
                        DiscountSection(authViewModel,
                            onClick = { discount ->
                                saleAmount = discount
                            })
                    }
                }
                item {
                    Text(
                        text = "Lựa chọn phương thước thanh toán",
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                        fontWeight = FontWeight.Bold,
                    )

                }
                items(paymentMethods) { method ->
                    PaymentMethodItem(
                        method = method,
                        isSelected = method == selectedMethod,
                        onSelect = { selectedMethod = it }
                    )
                }

            }
            PaymentSummary(totalAmount, tax, saleAmount,
                onClick = { points ->
                    val currentTotalSelected = totalAmount + tax - saleAmount
                    addressItems.value.forEach { item ->
                        val Address = "${item.addressDetail}, ${item.province}"
                        try {
                            apiCallOder.addOrder(
                                item.fullName,
                                item.phoneNumber,
                                email.toString(),
                                userId,
                                Address,
                                currentDateAndTime,
                                note,
                                quantity,
                                currentTotalSelected.toFloat(),
                                "Đang chờ xử lý",
                                selectedProducts.toList()
                            )
                        } catch (e: Exception) {
                            Log.e("PaymentSummary", "Lỗi khi gọi API: ${e.localizedMessage}")
                        }
                    }
                    Toast.makeText(context, "Đặt hàng thành công", Toast.LENGTH_SHORT).show()
                    selectedProducts.forEach { product ->
                        managementCart.removeItemByProduct(
                            product,
                            object : ChangeNumberItemsListener {
                                override fun onChanged() {
                                    Log.d("Cart", "Sản phẩm đã được xóa: ${product.name}")
                                }
                            })
                    }
                    navController.navigate("cart")
                })
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OderTopAppBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.check),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = null)
            }
        }
    )

}

@Composable
fun ProductItem(
    item: ProductsModel
) {
    Column {
        Card(shape = RoundedCornerShape(8.dp)) {
            Row(modifier = Modifier.padding(8.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(item.product_images[0]),
                    contentDescription = "Product Image",
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = item.name, fontWeight = FontWeight.Bold)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
                                .format(item.price).toString(),
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                        Text(text = "x ${item.quantity}", fontWeight = FontWeight.Bold)
                    }
                }
            }

        }
        Spacer(modifier = Modifier.padding(4.dp))
    }
}

@Composable
fun PaymentMethodItem(
    method: PaymentMethod,
    isSelected: Boolean,
    onSelect: (PaymentMethod) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp)
            .clickable { onSelect(method) }
            .background(Color.White, shape = RoundedCornerShape(12.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = { onSelect(method) })
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(id = method.icon),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = method.name, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun DiscountSection(authViewModel: AuthViewModel, onClick: (Int) -> Unit) {
    var applyDiscount by remember { mutableStateOf(false) }
    var userPoints by remember { mutableStateOf<String?>(null) }
    var discountAmount by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        userPoints = authViewModel.getSuspendingPoint()
        discountAmount = (userPoints?.toIntOrNull() ?: 0) * 10
    }
    Card(shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Áp dụng ưu đãi để được giảm giá",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Blue
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (userPoints != null ) {
                    Text(
                        "Đổi $userPoints điểm (~${
                            NumberFormat.getCurrencyInstance(
                                Locale(
                                    "vi",
                                    "VN"
                                )
                            ).format(discountAmount)
                        })"
                    )
                } else {
                    Text("Đổi 0 điểm (~0đ)")
                }
                Spacer(modifier = Modifier.weight(1f))
                Switch(checked = applyDiscount,
                    onCheckedChange = { checked ->
                        applyDiscount = checked
                        if (checked && userPoints != null) {
                            discountAmount = (userPoints?.toIntOrNull() ?: 0) * 10
                        } else {
                            discountAmount = 0
                        }
                        onClick(discountAmount)
                    }
                )
            }
        }
    }
}

@Composable
fun PaymentSummary(
    totalAmount: Double,
    tax: Double,
    saleAmount: Int,
    onClick: (Double) -> Unit
) {
    val totalSelected = totalAmount + tax - saleAmount
    val points by remember(totalSelected) { mutableStateOf(totalSelected / 1000) }
    Card(shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Thông tin thanh toán", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(text = "Tổng cộng")
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = NumberFormat.getCurrencyInstance(Locale("vi", "VN")).format(totalAmount)
                        .toString()
                )
            }
            Row {
                Text("Giảm giá")
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
                        .format(saleAmount).toString()
                )
            }
            Row {
                Text("Phí vận chuyển")
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = NumberFormat.getCurrencyInstance(Locale("vi", "VN")).format(tax)
                        .toString(), color = Color.Blue
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
                            .format(totalSelected),
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                    val displayPoints = points.roundToInt()
                    Text(
                        "+ ${
                            NumberFormat.getNumberInstance(Locale("vi", "VN")).format(displayPoints)
                        } điểm",
                        color = Color.Blue
                    )
                }
                Button(
                    onClick = {
                        onClick(points)
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                        colorResource(R.color.purple_700)
                    ),
                    modifier = Modifier
                        .height(40.dp)
                ) {
                    Text(
                        text = stringResource(R.string.pay),
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}