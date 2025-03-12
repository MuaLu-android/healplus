package com.example.core.ui.cart
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.core.model.products.ProductsModel
import com.example.core.tinydb.helper.ChangeNumberItemsListener
import com.example.core.tinydb.helper.ManagmentCart
import com.example.core.ui.R
@Composable
fun CartScreen(
    managementCart: ManagmentCart ? = null,
    navController: NavController,
){
    val context = LocalContext.current
    val managementCart = remember { managementCart ?: ManagmentCart(context) }
    val cartItems = remember { mutableStateOf(managementCart.getListCart() ?: arrayListOf())  }
    val tax = remember { mutableStateOf(0.0) }
    calculatorCart(managementCart,tax)
    Scaffold(
        topBar = {
            CartTopAppBar(navController)
        },
    ){paddingValues ->
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
                        .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                    , // Chiếm toàn bộ không gian còn lại
                    contentPadding = PaddingValues(bottom = 80.dp) // Tránh bị che bởi BottomAppBar
                ) {
                    items(cartItems.value) { item ->
                        CartItems(
                            CartItems = cartItems.value,
                            item = item,
                            managementCart = managementCart
                        ) {
                            cartItems.value = managementCart.getListCart()
                            calculatorCart(managementCart, tax)
                        }
                    }
                }

                // Hiển thị tổng kết giỏ hàng bên dưới

            }
            CartSummary(
                itemTotal = managementCart.getTotalFee(),
                tax = tax.value,
                delivery = 10.0
            )
        }




//        // Tiêu đề và nút quay lại
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Kiểm tra giỏ hàng rỗng
//        if (cartItems.value.isEmpty()) {
//            Text(
//                text = "Cart Is Empty",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 32.dp),
//                textAlign = TextAlign.Center,
//                fontSize = 18.sp
//            )
//        } else {
//            // Danh sách sản phẩm
//            LazyColumn(
//                modifier = Modifier.weight(1f) // Để chiếm hết không gian còn lại
//            ) {
//                items(cartItems.value) { item ->
//                    CartItems(
//                        CartItems = cartItems.value,
//                        item = item,
//                        managementCart = managementCart) {
//                        cartItems.value = managementCart.getListCart()
//                        calculatorCart(managementCart, tax)
//                    }
//                }
//            }
//
//            // Tổng kết giỏ hàng
//            CartSummary(
//                itemTotal = managementCart.getTotalFee(),
//                tax = tax.value,
//                delivery = 10.0
//            )
//        }0
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
            IconButton(onClick = {navController.popBackStack() }) {
                Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = null)
            }
        }
    )
}

@Composable
fun CartSummary(itemTotal: Double, tax: Double, delivery: Double) {
    val total = (1*tax)+itemTotal+delivery
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
                text = "$itemTotal"
            )
        }
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
        ){
            Text(
                text = stringResource(R.string.tax),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.grey),
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = "$$tax"
            )
        }
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
        ){
            Text(
                text = stringResource(R.string.delivery),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.grey),
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = "$$delivery"
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
                    text = "$total"
                )
            }
        Button(
            onClick = {},
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

fun calculatorCart(managementCart: ManagmentCart, tax: MutableState<Double>) {
    var percentText = 0.02
    tax.value = Math.round((managementCart.getTotalFee()*percentText)*100)/100.0
}

@Composable
fun CartList(cartItems: ArrayList<ProductsModel>,
             managementCart: ManagmentCart,
             onItemChange: () -> Unit
             ) {
    LazyColumn (modifier = Modifier
        .padding(top = 16.dp)
    ) {
        items(cartItems){
            items ->
//            CartItems(
//                CartItems = ,
//                item = items,
//                managementCart = managementCart,
//                onItemChange = onItemChange)
        }
    }
}

@Composable
fun CartItems(
    CartItems: ArrayList<ProductsModel>,
    item: ProductsModel, managementCart: ManagmentCart,
    onItemChange: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(bottom = 16.dp)
    ) {
        val(pic, titleTxt, feeEachTime, totalEachItem, Quantity, bottom12)=createRefs()
        val horizontalGuideline = createGuidelineFromStart(0.2f)
        Image(
            painter = rememberAsyncImagePainter(item.product_images[0]),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .padding(8.dp)
                .constrainAs(pic) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        )
        Text(
            text = item.name,
            modifier = Modifier
                .constrainAs(titleTxt) {
                    start.linkTo(parent.start, margin = 16.dp)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end, margin = 4.dp)
                }
                .fillMaxWidth() // Đảm bảo text có thể mở rộng chiều ngang
                .wrapContentHeight(), // Cho phép text mở rộng theo nội dung
            maxLines = 4, // Cho phép tối đa 3 dòng, có thể thay đổi
            overflow = TextOverflow.Visible // Nếu quá dài, hiển thị "..."
        )
         Text(
             text = "$${item.price}",
             color = colorResource(R.color.purple_200),
             modifier = Modifier
                 .constrainAs(feeEachTime){
                     start.linkTo(horizontalGuideline)
                     top.linkTo(titleTxt.bottom, margin = 4.dp)
                 }
            )
        Text(
            text = "$${item.quantity * item.price}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(totalEachItem){
                    start.linkTo(horizontalGuideline)
                    top.linkTo(feeEachTime.bottom, margin = 4.dp)
                }
        )
        ConstraintLayout(
            modifier = Modifier
                .width(80.dp)
                .constrainAs(Quantity) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
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
                    .constrainAs(numberItemTxt){
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            Box(modifier = Modifier
                .padding(2.dp)
                .size(20.dp)
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
                    managementCart.plusItem(
                        CartItems,
                        CartItems.indexOf(item),
                        object : ChangeNumberItemsListener {
                            override fun onChanged() {
                                onItemChange()
                            }

                        })
                }
            ){
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
                .size(20.dp)
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
                    managementCart.minusItem(CartItems,
                        CartItems.indexOf(item),
                        object : ChangeNumberItemsListener {
                            override fun onChanged() {
                                onItemChange()
                            }

                        })
                }
            ){
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
