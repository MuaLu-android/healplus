package com.example.healplus.cart
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.core.model.address.PaymentMethod
import com.example.core.model.products.ProductsModel
import com.example.core.tinydb.helper.ChangeNumberItemsListener
import com.example.core.tinydb.helper.ManagmentCart
import com.example.core.viewmodel.apiviewmodel.OderViewModel
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.R
import com.google.gson.Gson
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CartScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    orderViewModel: OderViewModel = viewModel()
) {
    val userId = authViewModel.getUserId().toString()
    val context = LocalContext.current
    val managementCart = remember { ManagmentCart(context, userId) }
    val cartItems = remember { mutableStateOf(managementCart.getListCart() ?: arrayListOf()) }
    val selectedItems = rememberSaveable { mutableStateOf(mutableSetOf<ProductsModel>()) }
    val tax = remember { mutableStateOf(0.0) }
    calculatorCart(selectedItems.value.toList(), tax)
    Scaffold(
        topBar = {
            CartTopAppBar(navController)
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            if (cartItems.value.isEmpty()) {
                Text(
                    text = stringResource(R.string.emnitys),
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 32.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 20.dp
                        ), // Chiếm toàn bộ không gian còn lại
                    contentPadding = PaddingValues(bottom = 80.dp) // Tránh bị che bởi BottomAppBar
                ) {
                    items(cartItems.value) { item ->
                        CartItems(
                            CartItems = cartItems.value,
                            item = item,
                            selectedItems = selectedItems.value,
                            managementCart = managementCart
                        ) {
                            cartItems.value = managementCart.getListCart()
                            calculatorCart(selectedItems.value.toList(), tax)
                        }
                    }
                }

                // Hiển thị tổng kết giỏ hàng bên dưới

            }
            CartSummary(
                itemTotal = selectedItems.value.sumOf { it.price * it.quantity },
                quantity = selectedItems.value.sumOf { it.quantity },
                tax = tax.value,
                onClick = { itemTotal, tax, quantity ->
                    val selectedProductsJson = Uri.encode(Gson().toJson(selectedItems.value.toList()))
                    navController.navigate("order_screen/$selectedProductsJson/$itemTotal/$tax/$quantity")
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartTopAppBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.yourcart),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
            )
        },
        navigationIcon = {
            IconButton(onClick = {navController.navigate("home") }) {
                Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = null)
            }
        }
    )
}

@Composable
fun CartSummary(itemTotal: Int, tax: Double, quantity: Int, onClick: (Int, Double, Int) -> Unit) {
    val total = (1*tax)+itemTotal
    val formattedTotal = NumberFormat.getNumberInstance(Locale("vi", "VN")).format(total) + " VND"
    val formattedItemTotal = NumberFormat.getNumberInstance(Locale("vi", "VN")).format(itemTotal) + " VND"
    val formattedTax = NumberFormat.getNumberInstance(Locale("vi", "VN")).format(tax) + " VND"
    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp, start = 16.dp, end = 16.dp)

    ){
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
        ){
            Text(
                text = stringResource(R.string.item_total),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.grey),
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = formattedItemTotal
            )
        }
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
        ){
            Text(
                text = stringResource(R.string.delivery),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.grey),
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = formattedTax
            )
        }
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(colorResource(R.color.grey))
                .padding(vertical = 8.dp)
        )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.total),
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.grey),
                    modifier = Modifier
                        .weight(1f),
                )
                Text(
                    text = formattedTotal
                )
            }
        Button(
            onClick = {onClick(itemTotal, tax, quantity)},
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor =
            colorResource(R.color.purple_700)
            ),
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .padding(bottom = 30.dp, start = 40.dp, end = 40.dp)
                .height(40.dp)
        ) {
            Text(
                text = stringResource(R.string.checkout),
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
}

fun calculatorCart(selectedItems: List<ProductsModel>, tax: MutableState<Double>) {
    var percentTax = 0.02
    val totalSelected = selectedItems.sumOf { it.price * it.quantity }
    tax.value = Math.round((totalSelected * percentTax) * 100) / 100.0
}

@Composable
fun CartItems(
    CartItems: ArrayList<ProductsModel>,
    selectedItems: MutableSet<ProductsModel>,
    item: ProductsModel,
    managementCart: ManagmentCart,
    onItemChange: () -> Unit
) {
    val isSelected = remember { mutableStateOf(item in selectedItems) }
    val totalPrice = item.price * item.quantity
    val formattedPrice = NumberFormat.getNumberInstance(Locale("vi", "VN")).format(item.price)
    val formattedTotalPrice = NumberFormat.getNumberInstance(Locale("vi", "VN")).format(totalPrice)
    Box(modifier = Modifier
        .padding(bottom = 16.dp)) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {

                Column(
                    modifier = Modifier
                        .padding(end = 16.dp)
                ) {
                    Text(
                        text = item.name,
                        modifier = Modifier

                            .fillMaxWidth() // Đảm bảo text có thể mở rộng chiều ngang
                            .wrapContentHeight(), // Cho phép text mở rộng theo nội dung
                        maxLines = 4, // Cho phép tối đa 3 dòng, có thể thay đổi
                        overflow = TextOverflow.Visible, // Nếu quá dài, hiển thị "..."
                        textAlign = TextAlign.Start
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Image(
                                painter = rememberAsyncImagePainter(item.product_images[0]),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)

                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Column {
                                Text(
                                    text = formattedPrice,
                                    color = colorResource(R.color.purple_200),
                                    modifier = Modifier
                                )
                                Spacer(modifier = Modifier.padding(2.dp))
                                Text(
                                    text = formattedTotalPrice,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier

                                )
                            }
                        }
                        ConstraintLayout(
                            modifier = Modifier
                                .width(60.dp)
                                .background(
                                    colorResource(R.color.grey),
                                    shape = RoundedCornerShape(10.dp)
                                )
                        ) {
                            val (plusCartBtn, minusCartBtn, numberItemTxt) = createRefs()
                            Text(
                                text = item.quantity.toString(),
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .constrainAs(numberItemTxt) {
                                        top.linkTo(parent.top)
                                        bottom.linkTo(parent.bottom)
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                    }
                            )
                            Box(modifier = Modifier
                                .padding(2.dp)
                                .size(15.dp)
                                .background(
                                    colorResource(R.color.purple_500),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .constrainAs(plusCartBtn) {
                                    end.linkTo(parent.end)
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                }
                                .clickable {
                                    if (isSelected.value) {
                                        managementCart.plusItem(
                                            CartItems,
                                            CartItems.indexOf(item),
                                            object : ChangeNumberItemsListener {
                                                override fun onChanged() {
                                                    onItemChange()
                                                }

                                            })
                                    }
                                }
                            ) {
                                Text(
                                    text = "+",
                                    color = Color.White,
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    textAlign = TextAlign.Center
                                )
                            }
                            Box(modifier = Modifier
                                .padding(2.dp)
                                .size(15.dp)
                                .background(
                                    colorResource(R.color.white),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .constrainAs(minusCartBtn) {
                                    start.linkTo(parent.start)
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                }
                                .clickable {
                                    if (isSelected.value) {
                                        val index = CartItems.indexOf(item)
                                        Log.d("CartDebug", "Index of item: $index")
                                        managementCart.minusItem(CartItems,
                                            index,
                                            object : ChangeNumberItemsListener {
                                                override fun onChanged() {
                                                    onItemChange()
                                                }

                                            })
                                    }
                                }
                            ) {
                                Text(
                                    text = "-",
                                    color = Color.Black,
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
                Checkbox(
                    checked = isSelected.value,
                    onCheckedChange = { checked ->
                        isSelected.value = checked
                        if (checked) {
                            selectedItems.add(item)
                        } else {
                            selectedItems.remove(item)
                            item.quantity = 0
                        }
                        onItemChange()
                    }
                )
            }
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(1.dp)
                        .background(colorResource(R.color.grey))
                        .padding(top = 12.dp)
                )
                IconButton(
                    onClick = {
                        managementCart.removeItemByProduct(
                            item,
                            object : ChangeNumberItemsListener {
                                override fun onChanged() {
                                    Log.d("Cart", "Sản phẩm đã được xóa: ${item.name}")
                                }
                            })
                    },
                    modifier = Modifier
                        .size(24.dp)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                    )
                }
            }
        }
    }
}
