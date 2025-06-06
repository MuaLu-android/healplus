package com.example.core.model.Oder

import com.example.core.model.products.ProductsModel

data class Order(
    val id: Int,
    val idauth: String,
    val name: String,
    val email: String,
    val address: String,
    val phone: String,
    val quantity: Int,
    val datetime: String ?= null,
    val sumMoney: Float,
    val note: String ?= null,
    val status: String,
    val items: List<ProductsModel>
)