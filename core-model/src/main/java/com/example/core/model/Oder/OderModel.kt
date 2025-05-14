package com.example.core.model.Oder

import com.example.core.model.products.ProductsModel

data class Order(
    val id: Int,
    val userId: String,
    val name: String,
    val email: String,
    val address: String,
    val phone: String,
    val datetime: String ?= null,
    val sumMoney: Int,
    val status: String,
    val items: List<ProductsModel>
)