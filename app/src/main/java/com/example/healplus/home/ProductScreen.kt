package com.example.healplus.home

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import coil.compose.rememberAsyncImagePainter
import com.example.core.model.products.ProductsModel
import com.example.core.tinydb.helper.ManagmentCart
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.R
import com.example.healplus.R.string.product
import com.example.healplus.settings.SpacerProduct
import com.google.gson.Gson
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductTopAppBar(navController: NavController) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Thông tin sản phẩm",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = { navController.navigate("cart") }) {
                Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = null)
            }
        }
    )
}

@Composable
fun DetailScreen(
    item: ProductsModel,
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    var selectedImageUrl by remember { mutableStateOf(item.product_images.first()) }
    var selectedModelIndex by remember { mutableStateOf(-1) }
    var model by remember { mutableStateOf(item.unit_names.first()) }
    val managmentCart = ManagmentCart(LocalContext.current, authViewModel.getUserId().toString())
    Scaffold(
        topBar = {
            ProductTopAppBar(navController)
        },
        bottomBar = {
            BottomAppBarView(onAddCartClick = {
                item.quantity = 1
                managmentCart.insertFood(item)
            }, navController)
        })
    { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = selectedImageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .padding(top = 16.dp, end = 16.dp, start = 16.dp),
                contentScale = ContentScale.Crop
            )
            LazyRow(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
            ) {
                items(item.product_images) { imageUrl ->
                    ImageThumbnail(
                        imageUrl = imageUrl,
                        isSelected = selectedImageUrl == imageUrl,
                        onClick = { selectedImageUrl = imageUrl }
                    )
                }
            }
            // Tên sản phẩm
            Text(
                text = item.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 4.dp, start = 16.dp)
            )
            Text(
                text = stringResource(R.string.trademark) + " ${item.trademark}",
                color = Color(0xFF007AFF), // Màu xanh dương
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable { /* Xử lý sự kiện */ }
            )
            // Đánh giá & bình luận
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 8.dp, start = 16.dp)
            ) {
                Text(text = "⭐ ${item.rating}", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${item.review} " + stringResource(R.string.review), color = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${item.comment} " + stringResource(R.string.comment),
                    color = Color.Gray
                )
            }

            // Giá sản phẩm
            PriceText(item.price, model)

            Text(
                text = "+ ${item.price / 1000} diem thuong",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 10.dp, bottom = 4.dp, start = 16.dp)
            )
            ModelSelector(
                item.unit_names,
                selectedModelIndex,
                onModelSelected = { it ->
                    model = it
                }
            )

            SpacerProduct()
            ProductInfoView(item, navController)
            Spacer(modifier = Modifier.padding(bottom = 100.dp))
        }

    }

}

@Composable
fun PriceText(price: Int, model: String) {
    val formattedPrice = NumberFormat.getNumberInstance(Locale("vi", "VN")).format(price) + " VND"

    Text(
        text = "$formattedPrice/ $model",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF007AFF), // Màu xanh đậm
        modifier = Modifier.padding(top = 8.dp, start = 16.dp)
    )
}

@Composable
fun ProductInfoView(product: ProductsModel, navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Tiêu đề "Thông tin sản phẩm"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.inpomation),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = stringResource(R.string.see_all),
                color = Color(0xFF007AFF), // Màu xanh dương
                fontSize = 14.sp,
                modifier = Modifier.clickable {
                    navController.navigate("productDetail/${Uri.encode(Gson().toJson(product))}")
                    Log.d("ProductInfoView", "Đã điều hướng đến: ${Uri.encode(Gson().toJson(product))}")
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Danh sách thông tin sản phẩm
        ProductInfoItem(stringResource(R.string.categories), product.element_names)
        ProductInfoItem(stringResource(R.string.dogam_from), product.preparation)
        ProductInfoItem(stringResource(R.string.origa), product.origin)
        ProductInfoItem(stringResource(R.string.Manufacturer), product.manufacturer)
        ProductInfoItem(stringResource(R.string.product), product.productiondate)
        ProductInfoItem(stringResource(R.string.expiry), product.expiry)
        ProductInfoItem(stringResource(R.string.Ingredient), product.ingredient)
        ProductInfoItem(stringResource(R.string.description), product.description)
        SeeAllButton(product, navController)
        SpacerProduct()

    }
}

@Composable
fun SeeAllButton(item: ProductsModel, navController: NavController) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navController.navigate("productDetail/${Uri.encode(Gson().toJson(item))}")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.see_all),
            color = Color(0xFF007AFF),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Arrow",
            tint = Color(0xFF007AFF),
            modifier = Modifier
                .size(16.dp)
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun ProductInfoItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun BottomAppBarView(
    onAddCartClick: () -> Unit,
    navController: NavController
) {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.navigate("chat") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.comment_24px), // icon tai nghe/chat
                    contentDescription = null,
                    tint = Color(0xFF0066CC),
                    modifier = Modifier.size(20.dp)
                )
            }

            Button(
                onClick = {
                    onAddCartClick()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0055FF))
            ) {
                Text(stringResource(R.string.addcart), color = Color.White)
            }
        }
    }
}

@Composable
fun ModelSelector(
    models: List<String>,
    selectedModelIndex: Int,
    onModelSelected: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        itemsIndexed(models) { index, model ->
            Box(modifier = Modifier
                .padding(end = 8.dp)
                .height(38.dp)
                .then(
                    if (index == selectedModelIndex) {
                        Modifier.border(1.dp, colorResource(R.color.purple_700))
                    } else {
                        Modifier
                    }
                )
                .background(
                    if (index == selectedModelIndex) colorResource(R.color.purple_500) else
                        colorResource(R.color.grey)
                )
                .clickable { onModelSelected(model) }
                .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = model,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = if (index == selectedModelIndex) colorResource(R.color.white) else
                        colorResource(R.color.black),
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun ImageThumbnail(
    imageUrl: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    var backColor = if (isSelected) colorResource(R.color.purple_700) else
        colorResource(R.color.grey)
    Box(modifier = Modifier
        .padding(4.dp)
        .size(40.dp)
        .then(
            if (isSelected) {
                Modifier.border(
                    1.dp,
                    colorResource(R.color.purple_500),
                    RoundedCornerShape(10.dp)
                )
            } else {
                Modifier
            }
        )
        .clip(CircleShape) // Bo tròn thành hình tròn
        .background(backColor)
        .clickable { onClick() }
        .padding(4.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = imageUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .padding(4.dp)
        )
    }
}
