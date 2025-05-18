package com.example.core.model.revenue

import java.time.LocalDate

data class RevenueData(
    val order_year: Int? = null,
    val order_month: Int? = null,
    val order_day: Int? = null,
    val total_revenue: Float
)
data class DetailedOrder(
     val id: Int,
     val datetime: String,
    val sumMoney: Int
)
data class RevenueResponse(
     val revenue: List<RevenueData>,
     val detaily_orders: List<DetailedOrder>
)
