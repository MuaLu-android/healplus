package com.example.core.model.api

import com.example.core.model.products.ProductsModel

data class OrderRequest(
    val name: String,
    val phone: String,
    val email: String,
    val userId: String,
    val address: String,
    val quantity: String,
    val sumMoney: String,
    val bonuspoint: String,
    val detail: List<ProductsModel>
)
