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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.core.model.products.ProductsModel
import com.example.core.model.products.ReviewItem
import com.example.core.tinydb.helper.ManagmentCart
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.R
import com.example.healplus.R.string.product
import com.example.healplus.settings.SpacerProduct
import com.google.gson.Gson
import java.text.NumberFormat
import java.util.Locale

@Composable
fun DetailScreen(
    item: ProductsModel,
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    apiCallViewModel: ApiCallViewModel = viewModel()
) {
    var selectedImageUrl by remember { mutableStateOf(item.product_images.first()) }
    var model by remember { mutableStateOf(item.unit_names.first()) }
    val managmentCart = ManagmentCart(LocalContext.current, authViewModel.getUserId().toString())
    val sampleReviews = listOf(
        ReviewItem("Nguyễn Văn A", 5f, "Sản phẩm tuyệt vời, chất lượng tốt, giao hàng nhanh!", "12/12/2023", "https://i.pravatar.cc/150?img=1"),
        ReviewItem("Trần Thị B", 4.5f, "Khá hài lòng với sản phẩm. Sẽ ủng hộ shop lần sau.", "10/12/2023", "https://i.pravatar.cc/150?img=2"),
        ReviewItem("Lê Văn C", 3f, "Chất lượng tạm ổn so với giá tiền.", "09/12/2023")
    )
    val individualReviews = remember { mutableStateOf(sampleReviews) }
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
            Text(
                text = item.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 4.dp, start = 16.dp)
            )
            Text(
                text = stringResource(R.string.trademark) + " ${item.trademark}",
                color = Color(0xFF007AFF),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable { /* Xử lý sự kiện */ }
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 8.dp, start = 16.dp)
            ) {
                Icon(Icons.Filled.Star, contentDescription = "Rating", tint = Color(0xFFFFC107), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = String.format(Locale.getDefault(), "%.1f", item.rating),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
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

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.rewarded_ads_24px),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "+ ${item.price / 1000} điểm thưởng",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 10.dp, bottom = 4.dp, start = 16.dp)
                )
            }

            SpacerProduct()
            ProductInfoView(item, navController, apiCallViewModel)
            ProductReviewsSection(
                averageRating = item.rating.toFloat(),
                totalReviews = item.review, // Giả sử item.review là tổng số đánh giá
                individualReviews = individualReviews.value, // Lấy từ state hoặc item.individualReviews
                onSeeAllReviewsClick = {
                    // TODO: Điều hướng đến màn hình xem tất cả đánh giá
                    // navController.navigate("allReviews/${item.idp}")
                    Log.d("DetailScreen", "Navigate to all reviews for ${item.idp}")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding())
        }

    }

}

@Composable
fun PriceText(price: Int, model: String) {
    val formattedPrice = NumberFormat.getCurrencyInstance(Locale("vi", "VN")).format(price).toString()
    Text(
        text = "$formattedPrice/$model",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF007AFF),
        modifier = Modifier.padding(top = 8.dp, start = 16.dp)
    )
}

@Composable
fun ProductInfoView(
    product: ProductsModel,
    navController: NavController,
    apiCallViewModel: ApiCallViewModel
) {
    Column(modifier = Modifier.padding(16.dp)) {
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
                color = Color(0xFF007AFF),
                fontSize = 14.sp,
                modifier = Modifier.clickable {
                    navController.navigate("productDetail/${Uri.encode(Gson().toJson(product))}")
                    apiCallViewModel.upDateReview(product.idp)
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        ProductInfoItem(stringResource(R.string.categories), product.element_names)
        ProductInfoItem(stringResource(R.string.dogam_from), product.preparation)
        ProductInfoItem(stringResource(R.string.origa), product.origin)
        ProductInfoItem(stringResource(R.string.Manufacturer), product.manufacturer)
        ProductInfoItem(stringResource(R.string.product), product.productiondate)
        ProductInfoItem(stringResource(R.string.expiry), product.expiry)
        ProductInfoItem(stringResource(R.string.Ingredient), product.ingredient)
        ProductInfoItem(stringResource(R.string.description), product.description)
        SeeAllButton(product, navController, apiCallViewModel)
        Divider()
    }
}

@Composable
fun SeeAllButton(
    item: ProductsModel,
    navController: NavController,
    apiCallViewModel: ApiCallViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("productDetail/${Uri.encode(Gson().toJson(item))}")
                apiCallViewModel.upDateReview(item.idp)
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
        .clip(CircleShape)
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
fun ProductReviewsSection(
    averageRating: Float,
    totalReviews: Int,
    individualReviews: List<ReviewItem>, // Danh sách các đánh giá chi tiết
    onSeeAllReviewsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 16.dp)) {
        Text(
            text = stringResource(R.string.product_reviews_title), // "Đánh giá sản phẩm"
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Phần tóm tắt đánh giá
        ReviewSummary(
            averageRating = averageRating,
            totalReviews = totalReviews,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Hiển thị một vài đánh giá nổi bật (ví dụ: 2-3 đánh giá)
        if (individualReviews.isNotEmpty()) {
            Text(
                text = stringResource(R.string.notable_reviews), // "Đánh giá nổi bật"
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            individualReviews.take(3).forEach { review -> // Lấy 3 đánh giá đầu tiên
                ReviewCard(review = review, modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
                Divider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
            }

            // Nút xem tất cả đánh giá
            if (totalReviews > 3) { // Chỉ hiển thị nếu có nhiều hơn 3 đánh giá
                TextButton(
                    onClick = onSeeAllReviewsClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(stringResource(R.string.see_all_reviews, totalReviews)) // "Xem tất cả (X) đánh giá"
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        } else {
            Text(
                text = stringResource(R.string.no_reviews_yet), // "Chưa có đánh giá nào."
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Cân nhắc thêm nút "Viết đánh giá" ở đây
        OutlinedButton(
            onClick = { /* TODO: Điều hướng đến màn hình viết đánh giá */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(stringResource(R.string.write_a_review)) // "Viết đánh giá"
        }
    }
}

@Composable
fun ReviewSummary(
    averageRating: Float,
    totalReviews: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = String.format(Locale.getDefault(), "%.1f", averageRating), // Định dạng 1 chữ số thập phân
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            RatingBar(rating = averageRating, starSize = 20.dp) // Composable RatingBar (xem bên dưới)
        }
        Text(
            text = stringResource(R.string.based_on_reviews, totalReviews), // "Dựa trên (X) đánh giá"
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
    // TODO: Cân nhắc thêm biểu đồ phân phối đánh giá (5 sao, 4 sao,...) nếu có dữ liệu
}

@Composable
fun ReviewCard(review: ReviewItem, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Ảnh đại diện (nếu có)
            review.profileImageUrl?.let { imageUrl ->
                Image(
                    painter = rememberAsyncImagePainter(model = imageUrl),
                    contentDescription = "${review.reviewerName} profile picture",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Column {
                Text(text = review.reviewerName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                RatingBar(rating = review.rating, starSize = 16.dp)
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(text = review.date, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = review.comment,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 3, // Giới hạn số dòng hiển thị ban đầu
            overflow = TextOverflow.Ellipsis // Thêm dấu "..." nếu dài quá
        )
        // TODO: Thêm nút "Đọc thêm" nếu comment quá dài
    }
}
@Composable
fun RatingBar(
    rating: Float,
    modifier: Modifier = Modifier,
    starCount: Int = 5,
    starSize: Dp = 24.dp, // Kích thước mặc định cho sao
    starColor: Color = Color(0xFFFFC107), // Màu vàng cho sao đầy
    emptyStarColor: Color = Color.LightGray // Màu cho sao rỗng/viền
) {
    Row(modifier = modifier) {
        val filledStars = kotlin.math.floor(rating).toInt()
        val halfStar = rating - filledStars >= 0.5f
        val emptyStars = starCount - filledStars - if (halfStar) 1 else 0

        // Sao đầy
        repeat(filledStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = starColor,
                modifier = Modifier.size(starSize)
            )
        }

        // Nửa sao (nếu có)
        if (halfStar) {
            Icon(
                painter = painterResource(R.drawable.star_half_24px), // Material Icons có sẵn
                contentDescription = null,
                tint = starColor,
                modifier = Modifier.size(starSize)
            )
        }

        // Sao rỗng
        repeat(emptyStars) {
            Icon(
                painter = painterResource(R.drawable.star_24px), // Material Icons có sẵn
                contentDescription = null,
                tint = emptyStarColor,
                modifier = Modifier.size(starSize)
            )
        }
    }
}