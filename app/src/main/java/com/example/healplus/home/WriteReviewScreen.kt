package com.example.healplus.home

import android.util.Log
import android.widget.Toast
import androidx.activity.result.launch
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import com.example.healplus.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteReviewScreen(
    navController: NavController,
    productId: String, // ID sản phẩm cần đánh giá
    apiCallViewModel: ApiCallViewModel = viewModel()
) {
    var rating by remember { mutableStateOf(0) } // 0 sao ban đầu
    var reviewTitle by remember { mutableStateOf("") } // Tiêu đề đánh giá (tùy chọn)
    var reviewComment by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Trạng thái từ ViewModel (ví dụ: sau khi gửi thành công)
    // val submitStatus by apiCallViewModel.submitReviewStatus.collectAsStateWithLifecycle()
    // LaunchedEffect(submitStatus) {
    //     if (submitStatus == true) { // Giả sử true là thành công
    //         Toast.makeText(context, "Đánh giá của bạn đã được gửi!", Toast.LENGTH_SHORT).show()
    //         navController.popBackStack()
    //         apiCallViewModel.resetSubmitReviewStatus() // Reset lại trạng thái
    //     } else if (submitStatus == false) { // Giả sử false là thất bại
    //          Toast.makeText(context, "Gửi đánh giá thất bại. Vui lòng thử lại.", Toast.LENGTH_SHORT).show()
    //          isLoading = false
    //          apiCallViewModel.resetSubmitReviewStatus()
    //     }
    // }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.write_your_review_title)) }, // "Viết đánh giá của bạn"
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.back_button_desc))
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .imePadding(), // Để nội dung không bị che bởi bàn phím
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.how_would_you_rate_product), // "Bạn đánh giá sản phẩm này thế nào?"
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Chọn sao đánh giá
            RatingInput(
                currentRating = rating,
                onRatingChange = { newRating -> rating = newRating },
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Tiêu đề đánh giá (tùy chọn)
            OutlinedTextField(
                value = reviewTitle,
                onValueChange = { reviewTitle = it },
                label = { Text(stringResource(R.string.review_title_optional)) }, // "Tiêu đề (tùy chọn)"
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Nội dung bình luận
            OutlinedTextField(
                value = reviewComment,
                onValueChange = { reviewComment = it },
                label = { Text(stringResource(R.string.your_comment_label)) }, // "Bình luận của bạn*"
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp), // Chiều cao tối thiểu
                placeholder = { Text(stringResource(R.string.share_your_thoughts_placeholder)) } // "Chia sẻ cảm nghĩ của bạn về sản phẩm..."
            )
            Text(
                text = stringResource(R.string.min_characters_required, 20), // "*Tối thiểu 20 ký tự"
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.weight(1f)) // Đẩy nút xuống dưới

            Button(
                onClick = {
                    if (rating == 0) {
                        Toast.makeText(context, context.getString(R.string.please_select_rating_toast), Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (reviewComment.length < 20) {
                        Toast.makeText(context, context.getString(R.string.comment_too_short_toast, 20), Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    isLoading = true
                    // Gọi ViewModel để gửi đánh giá
                    // apiCallViewModel.submitProductReview(productId, rating, reviewTitle, reviewComment)
                    Log.d("WriteReview", "Submit: $productId, $rating, $reviewTitle, $reviewComment")
                    // Giả lập thành công sau 2 giây
                    kotlinx.coroutines.GlobalScope.launch { // Sử dụng coroutine scope phù hợp
                        kotlinx.coroutines.delay(2000)
                        isLoading = false
                        // Giả sử thành công
                        Toast.makeText(context, "Đánh giá đã được gửi (mô phỏng)!", Toast.LENGTH_LONG).show()
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(stringResource(R.string.submit_review_button)) // "Gửi đánh giá"
                }
            }
        }
    }
}

@Composable
fun RatingInput(
    currentRating: Int,
    onRatingChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    starCount: Int = 5,
    starSize: Dp = 40.dp, // Sao to hơn cho việc nhập liệu
    selectedColor: Color = Color(0xFFFFC107),
    defaultColor: Color = Color.Gray
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..starCount) {
            Icon(
                imageVector = if (i <= currentRating) Icons.Filled.Star else Icons.Outlined.StarOutline,
                contentDescription = stringResource(R.string.rate_star_desc, i), // "Đánh giá %d sao"
                tint = if (i <= currentRating) selectedColor else defaultColor,
                modifier = Modifier
                    .size(starSize)
                    .padding(horizontal = 4.dp)
                    .clickable { onRatingChange(i) }
            )
        }
    }
}