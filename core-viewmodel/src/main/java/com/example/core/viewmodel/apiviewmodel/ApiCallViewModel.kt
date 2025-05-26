package com.example.core.viewmodel.apiviewmodel

import retrofit2.Callback
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.model.api.ApiResponse
import com.example.core.model.Oder.Order
import com.example.core.model.banners.BannersModel
import com.example.core.model.categories.CategoryModel
import com.example.core.model.elements.ElementsModel
import com.example.core.model.ingredients.IngredientsModel
import com.example.core.model.products.ProductsModel
import com.example.core.model.revenue.DetailedOrder
import com.example.core.model.revenue.RevenueData
import com.example.core.model.revenue.RevenueResponse
import com.example.core.network.retrofitclients.RetrofitClient
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
class ApiCallViewModel : ViewModel() {
    private val _banner = MutableLiveData<List<BannersModel>>()
    private val _category = MutableLiveData<MutableList<CategoryModel>>()
    private val _ingredient = MutableLiveData<MutableList<IngredientsModel>>()
    private val _recommended = MutableLiveData<MutableList<ProductsModel>>()
    private val _element = MutableLiveData<MutableList<ElementsModel>>()
    val banners: LiveData<List<BannersModel>> = _banner
    val categories: LiveData<MutableList<CategoryModel>> = _category
    val ingredient: LiveData<MutableList<IngredientsModel>> = _ingredient
    val recommended: LiveData<MutableList<ProductsModel>> = _recommended
    val element: LiveData<MutableList<ElementsModel>> = _element
    private var currentCall: Call<List<ProductsModel>>? = null
    private val _orders = MutableLiveData<MutableList<Order>>()
    val orders: LiveData<MutableList<Order>> = _orders
    private val _revenueWeek = MutableLiveData<List<RevenueData>>(emptyList())
    val revenueWeek: LiveData<List<RevenueData>> = _revenueWeek
    private val _revenueMonth = MutableLiveData<List<RevenueData>>(emptyList())
    val revenueMonth: LiveData<List<RevenueData>> = _revenueMonth
    private val _revenueYear = MutableLiveData<List<RevenueData>>(emptyList())
    val revenueYear: LiveData<List<RevenueData>> = _revenueYear
    private val _isDataEmptyAfterLoad = MutableLiveData(false)
    val isDataEmptyAfterLoad: LiveData<Boolean> = _isDataEmptyAfterLoad
    private val _historyWeek = MutableLiveData<List<DetailedOrder>>(emptyList())
    val historyWeek: LiveData<List<DetailedOrder>> = _historyWeek
    private val _historyMonth = MutableLiveData<List<DetailedOrder>>(emptyList())
    val historyMonth: LiveData<List<DetailedOrder>> = _historyMonth
    private val _historyYear = MutableLiveData<List<DetailedOrder>>(emptyList())
    val historyYear: LiveData<List<DetailedOrder>> = _historyYear
    fun clearRevenueData() {
        _revenueWeek.value = emptyList()
        _revenueMonth.value = emptyList()
        _revenueYear.value = emptyList()
        _isDataEmptyAfterLoad.value = false
    }

    fun revenueYear(year: Int) {
        RetrofitClient.instance.revenueYear(year).enqueue(object : Callback<RevenueResponse> {
            override fun onResponse(
                call: Call<RevenueResponse>,
                response: Response<RevenueResponse>
            ) {
                if (response.isSuccessful) {
                    val revenueList = response.body()
                    if (revenueList != null) {
                        _revenueYear.value = revenueList.revenue ?: emptyList()
                        _historyYear.value = revenueList.detaily_orders ?: emptyList()
                        _historyYear.value?.forEachIndexed { index, order ->
                        }
                        _isDataEmptyAfterLoad.value = revenueList.revenue.isNotEmpty()
                    }
                } else {
                    _revenueYear.value = emptyList()
                    _isDataEmptyAfterLoad.value = false
                }
            }

            override fun onFailure(call: Call<RevenueResponse>, t: Throwable) {
                _revenueYear.value = emptyList()
                _historyYear.value = emptyList()
                Log.e("ApiCallViewModel", "revenueYear: API call failed", t)
                _isDataEmptyAfterLoad.value = false
            }
        })
    }

    fun revenueWeek(start_date: LocalDate) {
        val startDateString = start_date.format(DateTimeFormatter.ISO_LOCAL_DATE)
        RetrofitClient.instance.revenueWeek(startDateString)
            .enqueue(object : Callback<RevenueResponse> {
                override fun onResponse(
                    call: Call<RevenueResponse>,
                    response: Response<RevenueResponse>
                ) {
                    if (response.isSuccessful) {
                        val revenueList = response.body()
                        if (revenueList != null) {
                            _revenueWeek.value = revenueList.revenue ?: emptyList()
                            _historyWeek.value = revenueList.detaily_orders ?: emptyList()
                            _isDataEmptyAfterLoad.value = revenueList.revenue.isNotEmpty()
                        }
                    } else {
                        _revenueWeek.value = emptyList()
                        _isDataEmptyAfterLoad.value = false
                    }
                }

                override fun onFailure(call: Call<RevenueResponse>, t: Throwable) {
                    _revenueWeek.value = emptyList()
                    _isDataEmptyAfterLoad.value = false
                }
            })
    }

    fun revenueMonth(month: Int, year: Int) {
        RetrofitClient.instance.revenueMonth(month, year)
            .enqueue(object : Callback<RevenueResponse> {
                override fun onResponse(
                    call: Call<RevenueResponse>,
                    response: Response<RevenueResponse>
                ) {
                    if (response.isSuccessful) {
                        val revenueList = response.body()
                        if (revenueList != null) {
                            _revenueMonth.value = revenueList.revenue ?: emptyList()
                            _historyMonth.value = revenueList.detaily_orders ?: emptyList()
                            _isDataEmptyAfterLoad.value = revenueList.revenue.isNotEmpty()
                        }
                    } else {
                        _revenueMonth.value = emptyList()
                        _isDataEmptyAfterLoad.value = false
                    }
                }

                override fun onFailure(call: Call<RevenueResponse>, t: Throwable) {
                    _revenueMonth.value = emptyList()
                    _isDataEmptyAfterLoad.value = false
                }
            })
    }

    fun loadOder() {
        Log.d("API_ORDER_REQUEST1234", "Gửi yêu cầu API để lấy danh sách đơn hàng...")
        RetrofitClient.instance.getOder().enqueue(object : Callback<List<Order>> {
            override fun onResponse(
                call: Call<List<Order>>,
                response: Response<List<Order>>
            ) {
                Log.d("API_ORDER_RESPONSE2", "Nhận phản hồi từ API - Code: ${response.code()}")
                if (response.isSuccessful) {
                    val orderList = response.body()?.toMutableList() ?: mutableListOf()
                    _orders.value = orderList
                    Log.d("API_ORDER_RESPONSE2", "Đã tải thành công ${orderList.size} đơn hàng.")
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Log.e("API_ORDER_ERROR1234", "Lỗi khi gọi API đơn hàng: ${t.message}")
            }
        })
    }

    fun getOderByUser(idauth: String) {
        RetrofitClient.instance.getOderByUser(idauth).enqueue(object : Callback<List<Order>> {
            override fun onResponse(
                call: Call<List<Order>>,
                response: Response<List<Order>>
            ) {
                if (response.isSuccessful) {
                    val orderList = response.body()?.toMutableList() ?: mutableListOf()
                    _orders.value = orderList
                    Log.d("API_ORDER_RESPONSE27890", "Đã tải thành công ${orderList.size} đơn hàng.")
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Log.e("API_ORDER_ERROR123456", "Lỗi khi gọi API đơn hàng: ${t.message}")
            }
        })
    }

    fun getOderByUserStatus(idauth: String, status: String) {
        RetrofitClient.instance.getOderByUserStatus(idauth, status)
            .enqueue(object : Callback<List<Order>> {
                override fun onResponse(
                    call: Call<List<Order>>,
                    response: Response<List<Order>>
                ) {
                    if (response.isSuccessful) {
                        val orderList = response.body()?.toMutableList() ?: mutableListOf()
                        _orders.value = orderList
                    }
                }

                override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                    Log.e("API_ORDER_ERROR1234", "Lỗi khi gọi API đơn hàng: ${t.message}")
                }
            })
    }

    fun loadOderStatus(status: String) {
        Log.d("API_ORDER_REQUEST1", "Gửi yêu cầu API để lấy danh sách đơn hàng...")
        RetrofitClient.instance.getOrderStatus(status).enqueue(object : Callback<List<Order>> {
            override fun onResponse(
                call: Call<List<Order>>,
                response: Response<List<Order>>
            ) {
                Log.d("API_ORDER_RESPONSE1", "Nhận phản hồi từ API - Code: ${response.code()}")
                if (response.isSuccessful) {
                    val orderList = response.body()?.toMutableList() ?: mutableListOf()
                    _orders.value = orderList
                    Log.d("API_ORDER_RESPONSE1", "Đã tải thành công ${orderList.size} đơn hàng.")
                    
                } else {
                    
                    Log.e(
                        "API_ORDER_ERROR",
                        "Lỗi Response Code: ${response.code()} - ${response.errorBody()?.string()}"
                    )
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Log.e("API_ORDER_ERROR", "Lỗi khi gọi API đơn hàng: ${t.message}")
            }
        })
    }

    fun updateOrderStatus(orderId: Int, status: String) {
        RetrofitClient.instance.updateOrderStatus(orderId, status)
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        if (apiResponse != null) {
                            Log.d(
                                "API_UPDATE_STATUS_RESPONSE",
                                "Cập nhật trạng thái thành công: ${apiResponse.message}"
                            )
                            val currentOrders = _orders.value?.toMutableList() ?: mutableListOf()
                            val updatedOrderIndex = currentOrders?.indexOfFirst { it.id == orderId }
                            if (updatedOrderIndex != null && updatedOrderIndex != -1) {
                                val updatedOrder =
                                    currentOrders[updatedOrderIndex].copy(status = status)
                                currentOrders[updatedOrderIndex] = updatedOrder
                                _orders.value = currentOrders
                            }
                        } else {
                            val errorResponse = ApiResponse(
                                success = false,
                                message = "Phản hồi cập nhật trạng thái rỗng."
                            )
                            Log.e("API_UPDATE_STATUS_ERROR", "Phản hồi cập nhật trạng thái rỗng.")
                        }
                    } else {
                        val errorMessage = response.errorBody()?.string() ?: "Lỗi không xác định"
                        val errorResponse = ApiResponse(
                            success = false,
                            message = "Lỗi Response Code: ${response.code()} - $errorMessage"
                        )
                        Log.e(
                            "API_UPDATE_STATUS_ERROR",
                            "Lỗi Response Code: ${response.code()} - $errorMessage"
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    val errorResponse = ApiResponse(
                        success = false,
                        message = "Lỗi khi gọi API cập nhật trạng thái: ${t.message}"
                    )
                    Log.e(
                        "API_UPDATE_STATUS_ERROR",
                        "Lỗi khi gọi API cập nhật trạng thái: ${t.message}"
                    )
                }
            })
    }

    fun loadProductBySearch(search: String) {
        currentCall?.cancel()
        currentCall = RetrofitClient.instance.getSearchProduct(search)
        currentCall?.enqueue(object : Callback<List<ProductsModel>> {
            override fun onResponse(
                call: Call<List<ProductsModel>>,
                response: Response<List<ProductsModel>>
            ) {
                if (response.isSuccessful) {
                    val productList = response.body()?.toMutableList() ?: mutableListOf()
                    _recommended.value = productList
                } else {
                    Log.e(
                        "API_ERROR1",
                        "Lỗi Response Code: ${response.code()} - ${response.errorBody()?.string()}"
                    )
                }
            }

            override fun onFailure(call: Call<List<ProductsModel>>, t: Throwable) {
                Log.e("API_ERROR1", "Lỗi khi gọi API: ${t.message}")
            }
        })
    }

    fun loadProductByCategory(idc: String) {
        RetrofitClient.instance.getProductsByCategory(idc)
            .enqueue(object : Callback<List<ProductsModel>> {
                override fun onResponse(
                    call: Call<List<ProductsModel>>,
                    response: Response<List<ProductsModel>>
                ) {
                    if (response.isSuccessful) {
                        val productList = response.body()?.toMutableList() ?: mutableListOf()
                        _recommended.value = productList
                    } else {
                        Log.e("API_ERROR", "Lỗi Response Code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<ProductsModel>>, t: Throwable) {
                    Log.e("API_ERROR", "Lỗi khi gọi API: ${t.message}")
                }
            })
    }

    fun loadProductByIngredient(iding: String) {
        Log.d("API_Ingredient", "Star")
        RetrofitClient.instance.getProductsByIngredient(iding)
            .enqueue(object : Callback<List<ProductsModel>> {
                override fun onResponse(
                    call: Call<List<ProductsModel>>,
                    response: Response<List<ProductsModel>>
                ) {
                    if (response.isSuccessful) {
                        val productsList = response.body()?.toMutableList() ?: mutableListOf()
                        _recommended.value = productsList

                    } else {
                        Log.e("API_ERROR", "Lỗi Response Code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<ProductsModel>>, t: Throwable) {
                    Log.e("API_ERROR", "Yêu cầu mạng thất bại: ${t.message}", t)
                }
            })
    }

    fun loadIngredientByCategory(idc: String) {
        RetrofitClient.instance.getIngredientByCategory(idc).enqueue(object :
            Callback<List<IngredientsModel>> {
            override fun onResponse(
                call: Call<List<IngredientsModel>>,
                response: Response<List<IngredientsModel>>
            ) {
                if (response.isSuccessful) {
                    val ingredientsList = response.body()?.toMutableList() ?: mutableListOf()
                    _ingredient.value = ingredientsList
                } else {
                    Log.d("API_Ingredient", "Loi Renpose : ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<IngredientsModel>>, t: Throwable) {
                Log.e("API_ERROR", "Lỗi khi gọi API: ${t.message}")
            }
        })
    }

    fun loadElementByIngredient(iding: String) {
        RetrofitClient.instance.getElementByIngredient(iding)
            .enqueue(object : Callback<List<ElementsModel>> {
                override fun onResponse(
                    call: Call<List<ElementsModel>>,
                    response: Response<List<ElementsModel>>
                ) {
                    if (response.isSuccessful) {
                        val ElementsList = response.body()?.toMutableList() ?: mutableListOf()
                        _element.value = ElementsList
                    } else {
                        Log.d("API_Elment", "Loi elemnet: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<ElementsModel>>, t: Throwable) {
                    Log.e("API_ERROR", "Lỗi khi gọi API: ${t.message}")
                }

            })
    }

    fun loadProductByElement(ide: String) {
        RetrofitClient.instance.getProductsByElement(ide)
            .enqueue(object : Callback<List<ProductsModel>> {
                override fun onResponse(
                    call: Call<List<ProductsModel>>,
                    response: Response<List<ProductsModel>>
                ) {
                    if (response.isSuccessful) {
                        val productsList = response.body()?.toMutableList() ?: mutableListOf()
                        _recommended.value = productsList
                    } else {
                        Log.d("API_Element", "Lỗi response code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<ProductsModel>>, t: Throwable) {
                    Log.e("API_ERROR", "Lỗi khi gọi API: ${t.message}")
                }

            })
    }

    fun loadIngredients() {
        RetrofitClient.instance.getIngredient()
            .enqueue(object : Callback<List<IngredientsModel>> {
                override fun onResponse(
                    call: Call<List<IngredientsModel>>,
                    response: Response<List<IngredientsModel>>
                ) {
                    Log.d("API_Category", "Nhận phản hồi từ API - Code: ${response.code()}")
                    if (response.isSuccessful) {
                        val categorys = response.body()?.toMutableList() ?: mutableListOf()
                        _ingredient.value = categorys
                        Log.d("API_Category", "Số lượng sản phẩm nhận được: ${categorys.size}")
                        categorys.forEachIndexed { index, item ->
                            Log.d("API_Category", "Sản phẩm [$index]: $item")
                        }
                    } else {
                        Log.e("API_ERROR", "Lỗi Response Code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<IngredientsModel>>, t: Throwable) {
                    Log.e("API_ERROR", "Error: ${t.message}")
                }
            })

    }

    fun loadIngredientCount() {
        RetrofitClient.instance.getIngredientCount()
            .enqueue(object : Callback<List<IngredientsModel>> {
                override fun onResponse(
                    call: Call<List<IngredientsModel>>,
                    response: Response<List<IngredientsModel>>
                ) {
                    if (response.isSuccessful) {
                        val categorys = response.body()?.toMutableList() ?: mutableListOf()
                        _ingredient.value = categorys
                    } else {
                        Log.e("API_ERROR", "Lỗi Response Code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<IngredientsModel>>, t: Throwable) {
                    Log.e("API_ERROR", "Error: ${t.message}")
                }
            })

    }

    fun loadRecommended() {
        Log.d("API_REQUEST", "Gửi yêu cầu API để lấy sản phẩm được đề xuất...")

        RetrofitClient.instance.getRecommendedProducts(1)
            .enqueue(object : Callback<List<ProductsModel>> {
                override fun onResponse(
                    call: Call<List<ProductsModel>>,
                    response: Response<List<ProductsModel>>
                ) {
                    Log.d("API_RESPONSE", "Nhận phản hồi từ API - Code: ${response.code()}")
                    if (response.isSuccessful) {
                        val productList = response.body()?.toMutableList() ?: mutableListOf()
                        _recommended.value = productList
                        Log.d("API_RESPONSE", "Số lượng sản phẩm nhận được: ${productList.size}")
                        productList.forEachIndexed { index, item ->
                            Log.d("API_RESPONSE", "Sản phẩm [$index]: $item")
                        }

                    } else {
                        Log.e("API_ERROR", "Lỗi Response Code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<ProductsModel>>, t: Throwable) {
                    Log.e("API_ERROR", "Lỗi khi gọi API: ${t.message}")
                }
            })
    }

    fun loadBanners() {
        RetrofitClient.instance.getBanners().enqueue(object : Callback<List<BannersModel>> {
            override fun onResponse(
                call: Call<List<BannersModel>>,
                response: Response<List<BannersModel>>
            ) {
                if (response.isSuccessful) {
                    
                    _banner.value = response.body() ?: emptyList()
                    _banner.value?.forEachIndexed { index, banner ->
                    }
                } else {
                    Log.e("API_ERROR", "Lỗi Response Code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<BannersModel>>, t: Throwable) {
                Log.e("API_ERROR", "Lỗi khi gọi API: ${t.message}")
            }
        })
    }

    fun loadElement() {
        RetrofitClient.instance.getElement()
            .enqueue(object : Callback<List<ElementsModel>> {
                override fun onResponse(
                    call: Call<List<ElementsModel>>,
                    response: Response<List<ElementsModel>>
                ) {
                    Log.d("API_Category", "Nhận phản hồi từ API - Code: ${response.code()}")
                    if (response.isSuccessful) {
                        val categorys = response.body()?.toMutableList() ?: mutableListOf()
                        _element.value = categorys
                        Log.d("API_Category", "Số lượng sản phẩm nhận được: ${categorys.size}")
                        categorys.forEachIndexed { index, item ->
                            Log.d("API_Category", "Sản phẩm [$index]: $item")
                        }
                    } else {
                        Log.e("API_ERROR", "Lỗi Response Code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<ElementsModel>>, t: Throwable) {
                    Log.e("API_ERROR", "Error: ${t.message}")
                }
            })

    }

    fun loadCategory() {
        RetrofitClient.instance.getCategories()
            .enqueue(object : Callback<List<CategoryModel>> {
                override fun onResponse(
                    call: Call<List<CategoryModel>>,
                    response: Response<List<CategoryModel>>
                ) {
                    Log.d("API_Category", "Nhận phản hồi từ API - Code: ${response.code()}")
                    if (response.isSuccessful) {
                        val categorys = response.body()?.toMutableList() ?: mutableListOf()
                        _category.value = categorys
                        Log.d("API_Category", "Số lượng sản phẩm nhận được: ${categorys.size}")
                        categorys.forEachIndexed { index, item ->
                            Log.d("API_Category", "Sản phẩm [$index]: $item")
                        }
                    } else {
                        Log.e("API_ERROR", "Lỗi Response Code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<CategoryModel>>, t: Throwable) {
                    Log.e("API_ERROR", "Error: ${t.message}")
                }
            })

    }

    fun addCategory(title: String, onResult: (ApiResponse) -> Unit) {
        Log.d("AddCategory", "Đang gửi request với title: $title")
        val call = RetrofitClient.instance.addCategory(title)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("AddCategory", "Phản hồi thành công: $it")
                        onResult(it)
                        Log.e("AddCategory", "Phản hồi rỗng!")
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("AddCategory", "Lỗi kết nối: ${t.message}")
                onResult(ApiResponse(false, "Lỗi kết nối!"))
            }
        })
    }

    fun addIngredient(
        title: String,
        url: String,
        idc: String,
        onResult: (ApiResponse) -> Unit
    ) {
        Log.d("AddCategory", "Đang gửi request với title: $title")
        val call = RetrofitClient.instance.addIngredient(title, url, idc)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("AddCategory", "Phản hồi thành công: $it")
                        onResult(it)
                        Log.e("AddCategory", "Phản hồi rỗng!")
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("AddCategory", "Lỗi kết nối: ${t.message}")
                onResult(ApiResponse(false, "Lỗi kết nối!"))
            }
        })
    }

    fun addElment(
        title: String,
        url: String,
        quantity: String,
        iding: String,
        onResult: (ApiResponse) -> Unit
    ) {
        Log.d("AddCategory", "Đang gửi request với title: $title")
        val call = RetrofitClient.instance.addElement(title, url, quantity, iding)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("AddCategory", "Phản hồi thành công: $it")
                        onResult(it)
                        Log.e("AddCategory", "Phản hồi rỗng!")
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("AddCategory", "Lỗi kết nối: ${t.message}")
                onResult(ApiResponse(false, "Lỗi kết nối!"))
            }
        })
    }

    fun updateIdAuth(email: String, idauth: String) {
        val call = RetrofitClient.instance.upDateIdAuth(email, idauth)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    Log.d("AddCategory", "Cap nhat thanh cong")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("AddCategory", "Lỗi kết nối: ${t.message}")
            }
        })
    }

    fun updateUser(
        name: String,
        email: String,
        gender: String,
        phone: String,
        url: String,
        dateBirth: String,
        idauth: String
    ) {
        val call = RetrofitClient.instance.upDateUser(
            name,
            email,
            gender,
            phone,
            url,
            dateBirth,
            idauth
        )
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    Log.d("AddCategory", "Cap nhat thanh cong")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("AddCategory", "Lỗi kết nối: ${t.message}")
            }
        })
    }
    fun updateCategory(idc: String, title: String, onResult: (ApiResponse) -> Unit) {
        val call = RetrofitClient.instance.updateCategory(idc, title)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onResult(it)
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                onResult(ApiResponse(false, "Lỗi kết nối!"))
            }
        })
    }

    fun updateElement(
        ide: String,
        title: String,
        url: String,
        quantity: String,
        iding: String,
        onResult: (ApiResponse) -> Unit
    ) {
        val call = RetrofitClient.instance.updateElement(ide, title, url, quantity, iding)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onResult(it)
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                onResult(ApiResponse(false, "Lỗi kết nối!"))
            }
        })
    }

    fun updateIngredient(
        iding: String,
        title: String,
        url: String,
        idc: String,
        onResult: (ApiResponse) -> Unit
    ) {
        val call = RetrofitClient.instance.updateIngredient(iding, title, url, idc)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onResult(it)
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                onResult(ApiResponse(false, "Lỗi kết nối!"))
            }
        })
    }

    fun deleteCategory(idc: String, onResult: (ApiResponse) -> Unit) {
        val call = RetrofitClient.instance.deleteCategory(idc)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onResult(it)
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                onResult(ApiResponse(false, "Lỗi kết nối!"))
            }
        })
    }

    fun deleteIngredient(iding: String, onResult: (ApiResponse) -> Unit) {
        val call = RetrofitClient.instance.deleteIngnredient(iding)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onResult(it)
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                onResult(ApiResponse(false, "Lỗi kết nối!"))
            }
        })
    }

    fun deleteElement(ide: String, onResult: (ApiResponse) -> Unit) {
        val call = RetrofitClient.instance.deleteElement(ide)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onResult(it)
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                onResult(ApiResponse(false, "Lỗi kết nối!"))
            }
        })
    }

    fun upDateReview(idp: String) {
        val call = RetrofitClient.instance.updateReview(idp)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    Log.e("AddCategory", "Thanh cong")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("AddCategory", "Lỗi kết nối: ${t.message}")
            }
        })
    }

}