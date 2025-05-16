package com.example.core.model.products

data class ReviewItem(
    val reviewerName: String,
    val rating: Float, // Điểm đánh giá của người này
    val comment: String,
    val date: String, // Ngày đánh giá
    val profileImageUrl: String? = null // URL ảnh đại diện (tùy chọn)
)
