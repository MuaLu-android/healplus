package com.example.core.viewmodel.apiviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.core.model.Api.ApiResponse
import com.example.core.model.products.ProductsModel
import com.example.core.model.products.Thanhphan
import com.example.core.network.retrofitclients.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ApiCallAdd: ViewModel() {
    fun addOrder(
        name: String,
        phone: String,
        email: String,
        userId: String,
        address: String,
        quantity: String,
        sumMoney: String,
        bonusPoint: String,
        detail: List<ProductsModel>
    ) {
        val gson = Gson()
        val call = RetrofitClient.instance.addOder(name, phone, email, userId, address, quantity, sumMoney, bonusPoint, gson.toJson(detail))
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    Log.d("AddOrder", "Phản hồi từ server: ${response.body()}")
                } else {
                    Log.e("AddOrder", "Phản hồi thất bại với mã lỗi: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("AddOrder", "Lỗi kết nối: ${t.message}")
            }
        })
    }
    fun addProduct(
        name: String,
        trademark: String,
        rating: String,
        review: String,
        comment: String,
        price: String,
        preparation: String,
        specification: String,
        origin: String,
        manufacturer: String,
        ingredient: String,
        description: String,
        quantity: String,
        showRecommended: String,
        productiondate: String,
        congdung: String,
        cachdung: String,
        tacdungphu: String,
        ide: String,
        baoquan: String,
        thanhphan: List<Thanhphan>,
        productImages: List<String>,
        unitNames: List<String>,
    ) {
        val gson = Gson()
        RetrofitClient.instance.addProduct(
            name = name,
            trademark = trademark,
            rating = rating,
            review = review,
            comment = comment,
            price = price,
            preparation = preparation,
            specification = specification,
            origin = origin,
            manufacturer = manufacturer,
            ingredient = ingredient,
            description = description,
            quantity = quantity,
            showRecommended = showRecommended,
            ide = ide,
            productiondate = productiondate,
            congdung = congdung,
            cachdung = cachdung,
            tacdungphu = tacdungphu, // Truyền giá trị tacdungphu
            baoquan = baoquan,
            productImages = gson.toJson(productImages),
            thanhphan = gson.toJson(thanhphan),
            unitNames = gson.toJson(unitNames) // Truyền giá trị unitNames
        ).enqueue(object : Callback<ApiResponse?> {
            override fun onResponse(call: Call<ApiResponse?>, response: Response<ApiResponse?>) {
                if (response.isSuccessful) {
                    Log.d("AddOrder", "Phản hồi từ server: ${response.body()}")
                } else {
                    Log.e("AddOrder", "Phản hồi thất bại với mã lỗi: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                Log.e("AddOrder", "Lỗi kết nối: ${t.message}")
            }
        })
    }
}