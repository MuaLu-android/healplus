package com.example.healplus.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.isEmpty
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.error
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.core.model.products.ReviewItem
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import com.example.healplus.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllReviewsScreen(
    navController: NavController,
    productId: String,
    productName: String, // Tên sản phẩm để hiển thị trên TopAppBar
    apiCallViewModel: ApiCallViewModel = viewModel()
) {
    // Trạng thái lưu trữ danh sách đánh giá, trạng thái tải, lỗi
    var reviews by remember { mutableStateOf<List<ReviewItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    // Lấy dữ liệu khi màn hình được tạo
    LaunchedEffect(productId) {
        isLoading = true
        errorMessage = null
        try {
            // Giả sử ViewModel có hàm fetchAllReviewsForProduct
            // reviews = apiCallViewModel.fetchAllReviewsForProduct(productId)
            // Mô phỏng tải dữ liệu:
            kotlinx.coroutines.delay(1500)
            reviews = listOf(
                ReviewItem("Nguyễn Văn A", 5f, "Sản phẩm tuyệt vời, chất lượng tốt, giao hàng nhanh! Rất đáng tiền.", "12/12/2023", "https://i.pravatar.cc/150?img=1"),
                ReviewItem("Trần Thị B", 4.0f, "Khá hài lòng với sản phẩm. Sẽ ủng hộ shop lần sau. Đóng gói cẩn thận.", "10/12/2023", "https://i.pravatar.cc/150?img=2"),
                ReviewItem("Lê Văn C", 3f, "Chất lượng tạm ổn so với giá tiền. Giao hàng hơi chậm một chút.", "09/12/2023"),
                ReviewItem("Phạm Thị D", 4.5f, "Sản phẩm dùng tốt, mùi thơm dễ chịu. Mình rất thích.", "08/12/2023", "https://i.pravatar.cc/150?img=4"),
                ReviewItem("Hoàng Văn E", 2f, "Không như mong đợi, chất lượng chưa thực sự tốt lắm.", "05/12/2023", "https://i.pravatar.cc/150?img=5")
            )
        } catch (e: Exception) {
            errorMessage = context.getString(R.string.failed_to_load_reviews_error, e.localizedMessage ?: "Unknown error")
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.all_reviews_for_product_title, productName)) }, // "Đánh giá cho [Tên sản phẩm]"
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.back_button_desc))
                    }
                },
                actions = {
                    // TODO: Thêm nút Filter và Sort nếu cần
                    IconButton(onClick = { /* TODO: Mở dialog filter */ }) {
                        Icon(Icons.Filled.FilterList, contentDescription = stringResource(R.string.filter_reviews_desc))
                    }
                    IconButton(onClick = { /* TODO: Mở dialog sort */ }) {
                        Icon(Icons.Filled.Sort, contentDescription = stringResource(R.string.sort_reviews_desc))
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            } else if (reviews.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_reviews_for_this_product_yet), // "Sản phẩm này chưa có đánh giá nào."
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // TODO: Có thể thêm phần tổng quan (ReviewSummary) ở đây nếu muốn
                    // item {
                    //     val averageRating = reviews.map { it.rating }.average().toFloat()
                    //     ReviewSummary(averageRating = averageRating, totalReviews = reviews.size)
                    //     Spacer(modifier = Modifier.height(16.dp))
                    // }

                    items(reviews) { reviewItem ->
                        // Sử dụng ReviewCard đã tạo ở màn hình chi tiết sản phẩm
                        // Bạn cần đảm bảo ReviewCard có thể truy cập được từ đây
                        // (ví dụ: đặt nó trong một package chung về UI components)
                        ReviewCard(review = reviewItem, modifier = Modifier.fillMaxWidth())
                        Divider(modifier = Modifier.padding(top = 12.dp))
                    }
                }
            }
        }
    }
}