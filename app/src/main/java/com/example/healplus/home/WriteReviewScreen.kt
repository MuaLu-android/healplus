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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.core.viewmodel.apiviewmodel.ApiCallAdd
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.R
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteReviewScreen(
    navController: NavController,
    productId: String, // ID sản phẩm cần đánh giá
    apiCallAdd: ApiCallAdd = viewModel(),
    authViewModel: AuthViewModel = viewModel(),
) {
    var rating by remember { mutableStateOf(0) }
    var reviewTitle by remember { mutableStateOf("") }
    var reviewComment by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val submitStatus by apiCallAdd.submitReviewStatus.collectAsStateWithLifecycle()
    LaunchedEffect(submitStatus) {
        if (submitStatus == true) {
            Toast.makeText(context, "Đánh giá của bạn đã được gửi!", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
            apiCallAdd.resetSubmitReviewStatus()
        } else if (submitStatus == false) {
            Toast.makeText(context, "Gửi đánh giá thất bại. Vui lòng thử lại.", Toast.LENGTH_SHORT)
                .show()
            isLoading = false
            apiCallAdd.resetSubmitReviewStatus()
        }
    }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.write_your_review_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button_desc)
                        )
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
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.how_would_you_rate_product), // "Bạn đánh giá sản phẩm này thế nào?"
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            RatingInput(
                currentRating = rating,
                onRatingChange = { newRating -> rating = newRating },
                modifier = Modifier.padding(bottom = 24.dp)
            )
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
                text = stringResource(
                    R.string.min_characters_required,
                    20
                ), // "*Tối thiểu 20 ký tự"
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.weight(1f)) // Đẩy nút xuống dưới

            Button(
                onClick = {
                    if (rating == 0) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.please_select_rating_toast),
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    if (reviewComment.length < 20) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.comment_too_short_toast, 20),
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    coroutineScope.launch {
                        isLoading = true // Bắt đầu loading
                        try {
                            val fetchedUsername = authViewModel.getSuspendingUserFullName()
                            val reviewerNameToSubmit = fetchedUsername ?: "Người dùng ẩn danh"

                            val fetchedImageUrl = authViewModel.getSuspendingUrl()
                            val imageUrlToSubmit = fetchedImageUrl ?: ""

                            val sdf = SimpleDateFormat(
                                "dd/MM/yyyy",
                                Locale.getDefault()
                            )
                            val currentDateAndTime = sdf.format(Date())

                            apiCallAdd.addReview(
                                reviewerName = reviewerNameToSubmit,
                                rating = rating.toFloat(),
                                comment = reviewComment, // Nên thêm reviewTitle nếu có
                                date = currentDateAndTime,
                                profileImageUrl = imageUrlToSubmit,
                                idp = productId
                            )
                            // Không cần xử lý thành công/thất bại ở đây nữa vì LaunchedEffect sẽ làm điều đó
                            // isLoading sẽ được đặt thành false trong LaunchedEffect khi submitStatus thay đổi
                        } catch (e: Exception) {
                            // Xử lý lỗi nếu getSuspendingUserFullName hoặc getSuspendingUrl thất bại
                            Log.e("WriteReviewScreen", "Error fetching user data: ${e.message}", e)
                            Toast.makeText(
                                context,
                                "Lỗi lấy thông tin người dùng. Vui lòng thử lại.",
                                Toast.LENGTH_LONG
                            ).show()
                            isLoading = false
                        }
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
                painter = if (i <= currentRating) painterResource(R.drawable.star) else painterResource(
                    R.drawable.star_24px
                ),
                contentDescription = stringResource(R.string.rate_star_desc, i),
                tint = if (i <= currentRating) selectedColor else defaultColor,
                modifier = Modifier
                    .size(starSize)
                    .padding(horizontal = 4.dp)
                    .clickable { onRatingChange(i) }
            )
        }
    }
}