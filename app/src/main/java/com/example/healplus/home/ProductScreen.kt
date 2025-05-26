package com.example.healplus.home
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
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
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.healplus.settings.SpacerProduct
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
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
    val itemCount by remember { mutableStateOf(managmentCart.getItemCount()) }
    Scaffold(
        topBar = {
            ProductTopAppBar(navController, itemCount)
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
                modifier = Modifier.padding(start = 16.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(50.dp),
                modifier = Modifier.padding(top = 4.dp, start = 16.dp)
            ) {
                Row ( verticalAlignment = Alignment.Bottom) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = String.format(Locale.getDefault(), "%.1f", item.rating),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(text = "${item.review} " + stringResource(R.string.review), color = Color.Gray)
                Text(
                    text = "${item.sold} " + stringResource(R.string.comment),
                    color = Color.Gray
                )
            }

            PriceText(item.price, model)
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0E0E0)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.icon_point),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                    )
                }
                Text(
                    text ="+ ${item.price / 1000} điểm",
                    modifier = Modifier.padding(top = 10.dp, start = 2.dp)
                )
            }

            SpacerProduct()
            ProductInfoView(item, navController, apiCallViewModel)
            SpacerProduct()
            ProductReviewsSection(
                averageRating = item.rating.toFloat(),
                totalReviews = item.review,
                individualReviews = item.reviewitems,
                onSeeAllReviewsClick = {
                    navController.navigate(
                        "allReviews/${item.name}/${
                            URLEncoder.encode(
                                Gson().toJson(item.reviewitems),
                                StandardCharsets.UTF_8.toString()
                            )
                        }"
                    )
                },
                onWriteReviewClick = {
                    navController.navigate("writeReview/${item.idp}")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding())
        }

    }

}

@Composable
fun PriceText(price: Int, model: String) {
    val formattedPrice =
        NumberFormat.getCurrencyInstance(Locale("vi", "VN")).format(price).toString()
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
    var showProducts by rememberSaveable  { mutableStateOf(false) }
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
        Text(
            text = stringResource(R.string.trademark) + " ${product.trademark}",
            color = Color(0xFF007AFF),
            modifier = Modifier
                .clickable { /* Xử lý sự kiện */ }
        )
        ProductInfoItem(stringResource(R.string.categories), product.element_names)
        AnimatedVisibility(visible = showProducts) {
            Column {
                ProductInfoItem(stringResource(R.string.dogam_from), product.preparation)
                ProductInfoItem(stringResource(R.string.origa), product.origin)
                ProductInfoItem(stringResource(R.string.Manufacturer), product.manufacturer)
                ProductInfoItem(stringResource(R.string.product), product.productiondate)
                ProductInfoItem(stringResource(R.string.expiry), product.expiry)
                ProductInfoItem(stringResource(R.string.Ingredient), product.ingredient)
                ProductInfoItem(stringResource(R.string.description), product.description)
                SeeAllButton(product, navController, apiCallViewModel)
            }
        }
        BouncingIconButton(showProducts = showProducts) {
            showProducts = !showProducts
        }
    }
}
@Composable
fun BouncingIconButton(showProducts: Boolean, onToggle: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "BouncingTransition")

    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "OffsetY"
    )

    IconButton(
        onClick = onToggle,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = if (showProducts) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = if (showProducts) "Thu gọn" else "Mở rộng",
            modifier = Modifier
                .size(32.dp)
                .offset(y = offsetY.dp)
        )
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
        )
    }
}

@Composable
fun ProductInfoItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
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
                onClick = { navController.navigate("add") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.comment_24px),
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
fun ProductTopAppBar(navController: NavController, itemCount: Int) {
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
                BadgedBox(
                    badge = {
                        if (itemCount > 0) {
                            Badge {
                                Text(itemCount.toString())
                            }
                        }
                    }
                ){
                    Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = null)

                }
            }
        }
    )
}

@Composable
fun ProductReviewsSection(
    averageRating: Float,
    totalReviews: Int,
    individualReviews: List<ReviewItem>,
    onSeeAllReviewsClick: () -> Unit,
    onWriteReviewClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding()) {
        Text(
            text = stringResource(R.string.product_reviews_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        ReviewSummary(
            averageRating = averageRating,
            totalReviews = totalReviews,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (individualReviews.isNotEmpty()) {
            Text(
                text = stringResource(R.string.notable_reviews),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            individualReviews.take(3).forEach { review ->
                ReviewCard(
                    review = review,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
                Divider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
            }
            if (totalReviews > 3) {
                TextButton(
                    onClick = { onSeeAllReviewsClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        stringResource(
                            R.string.see_all_reviews,
                            totalReviews
                        )
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        } else {
            Text(
                text = stringResource(R.string.no_reviews_yet),
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            onClick = {
                onWriteReviewClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(stringResource(R.string.write_a_review))
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
                text = String.format(Locale.getDefault(), "%.1f", averageRating),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            RatingBar(rating = averageRating, starSize = 20.dp)
        }
        Text(
            text = stringResource(R.string.based_on_reviews, totalReviews),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

@Composable
fun ReviewCard(review: ReviewItem, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
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
                Text(
                    text = review.reviewerName,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                RatingBar(rating = review.rating, starSize = 16.dp)
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(text = review.date, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = review.comment,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun RatingBar(
    rating: Float,
    modifier: Modifier = Modifier,
    starCount: Int = 5,
    starSize: Dp = 24.dp,
    starColor: Color = Color(0xFFFFC107),
    emptyStarColor: Color = Color.LightGray
) {
    Row(modifier = modifier) {
        val filledStars = kotlin.math.floor(rating).toInt()
        val halfStar = rating - filledStars >= 0.5f
        val emptyStars = starCount - filledStars - if (halfStar) 1 else 0
        repeat(filledStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = starColor,
                modifier = Modifier.size(starSize)
            )
        }
        if (halfStar) {
            Icon(
                painter = painterResource(R.drawable.star_half_24px),
                contentDescription = null,
                tint = starColor,
                modifier = Modifier.size(starSize)
            )
        }
        repeat(emptyStars) {
            Icon(
                painter = painterResource(R.drawable.star_24px),
                contentDescription = null,
                tint = emptyStarColor,
                modifier = Modifier.size(starSize)
            )
        }
    }
}