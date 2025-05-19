package com.example.core.viewmodel.apiviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.core.model.Api.ApiResponse
import com.example.core.model.products.ProductsModel
import com.example.core.model.products.Thanhphan
import com.example.core.network.retrofitclients.RetrofitClient
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate


class ApiCallAdd : ViewModel() {
    private val _submitReviewStatus = MutableStateFlow<Boolean?>(null)
    val submitReviewStatus: StateFlow<Boolean?> = _submitReviewStatus
    fun addReview(
        reviewerName: String,
        rating: Float,
        comment: String,
        date: String,
        profileImageUrl: String,
        idp: String
    ) {
        val call =
            RetrofitClient.instance.addReview(reviewerName, rating, comment, date, profileImageUrl, idp)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    Log.d("AddOrder", "Phản hồi từ server: ${response.body()}")
                    _submitReviewStatus.value = true
                } else {
                    Log.e("AddOrder", "Phản hồi thất bại với mã lỗi: ${response.code()}")
                    _submitReviewStatus.value = false
                }
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("AddOrder", "Lỗi kết nối: ${t.message}")
                _submitReviewStatus.value = false
            }
        })
    }
    fun resetSubmitReviewStatus() {
        _submitReviewStatus.value = null
    }
    fun addUser(
        name: String,
        email: String,
        password: String,
        phone: String,
        url: String,
        role: String
    ) {
        val call =
            RetrofitClient.instance.addUser(name, email, password, phone, url, role)
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

    fun addOrder(
        name: String,
        phone: String,
        email: String,
        idauth: String,
        address: String,
        datetime: LocalDate,
        note: String,
        quantity: Int,
        sumMoney: Float,
        status: String,// Trạng thái đơn hàng
        items: List<ProductsModel>
    ) {
        val gson = Gson()
        val call = RetrofitClient.instance.addOder(
            name,
            phone,
            email,
            idauth,
            address,
            datetime,
            note,
            quantity,
            sumMoney,
            status,
            gson.toJson(items)
        )
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