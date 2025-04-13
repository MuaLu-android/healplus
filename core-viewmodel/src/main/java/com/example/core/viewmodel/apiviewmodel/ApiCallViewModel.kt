package com.example.core.viewmodel.apiviewmodel

import retrofit2.Callback
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.Api.ApiResponse
import com.example.core.model.banners.BannersModel
import com.example.core.model.categories.CategoryModel
import com.example.core.model.elements.ElementsModel
import com.example.core.model.ingredients.IngredientsModel
import com.example.core.model.products.ProductsModel
import com.example.core.network.retrofitclients.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class ApiCallViewModel: ViewModel() {
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


    fun loadProductBySearch(search: String) {
        Log.d("API_REQUEST1", "Gửi yêu cầu API để lấy sản phẩm của danh mục $search")
        Log.d("API_DEBUG", "Thời gian gửi request: ${System.currentTimeMillis()}")
        // Hủy request cũ nếu có
        currentCall?.cancel()

        // Tạo request mới
        currentCall = RetrofitClient.instance.getSearchProduct(search)
        currentCall?.enqueue(object : Callback<List<ProductsModel>> {
            override fun onResponse(call: Call<List<ProductsModel>>, response: Response<List<ProductsModel>>) {
                Log.d("API_RESPONSE1", "Nhận phản hồi từ API - Code: ${response.code()}")

                if (response.isSuccessful) {
                    val productList = response.body()?.toMutableList() ?: mutableListOf()
                    _recommended.value = productList
                    Log.d("API_RESPONSE1", "Số lượng sản phẩm nhận được: ${productList.size}")
                    productList.forEachIndexed { index, item ->
                        Log.d("API_RESPONSE1", "Sản phẩm [$index]: $item")
                    }
                } else {
                    Log.e("API_ERROR1", "Lỗi Response Code: ${response.code()} - ${response.errorBody()?.string()}")
                }
            }
            override fun onFailure(call: Call<List<ProductsModel>>, t: Throwable) {
                Log.e("API_ERROR1", "Lỗi khi gọi API: ${t.message}")
            }
        })
    }
    fun loadProductByCategory(idc: String) {
        Log.d("API_REQUEST", "Gửi yêu cầu API để lấy sản phẩm của danh mục $idc...")
        RetrofitClient.instance.getProductsByCategory(idc).enqueue(object : Callback<List<ProductsModel>> {
            override fun onResponse(call: Call<List<ProductsModel>>, response: Response<List<ProductsModel>>) {
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
    fun loadProductByIngredient(iding: String){
        Log.d("API_Ingredient", "Star")
        RetrofitClient.instance.getProductsByIngredient(iding).enqueue(object : Callback<List<ProductsModel>>{
            override fun onResponse(
                call: Call<List<ProductsModel>>,
                response: Response<List<ProductsModel>>
            ) {
                if (response.isSuccessful){
                    val productsList = response.body()?.toMutableList() ?: mutableListOf()
                    _recommended.value = productsList
                    Log.d("API_Ingredient", "Số lượng sản phẩm nhận được: ${productsList.size}")
                    productsList.forEachIndexed { index, item ->
                        Log.d("API_Ingredient", "Sản phẩm [$index]: $item")
                    }

                }else {
                    Log.e("API_ERROR", "Lỗi Response Code: ${response.code()}")
            }}
            override fun onFailure(call: Call<List<ProductsModel>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
    fun loadIngredientByCategory(idc: String){
        RetrofitClient.instance.getIngredientByCategory(idc).enqueue(object :
            Callback<List<IngredientsModel>> {
            override fun onResponse(
                call: Call<List<IngredientsModel>>,
                response: Response<List<IngredientsModel>>
            ) {
                if (response.isSuccessful){
                    val ingredientsList = response.body()?.toMutableList() ?: mutableListOf()
                    _ingredient.value = ingredientsList
                }else{
                    Log.d("API_Ingredient", "Loi Renpose : ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<IngredientsModel>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
    fun loadElementByIngredient(iding: String){
        RetrofitClient.instance.getElementByIngredient(iding).enqueue(object : Callback<List<ElementsModel>>{
            override fun onResponse(
                call: Call<List<ElementsModel>>,
                response: Response<List<ElementsModel>>
            ) {
                if (response.isSuccessful){
                    val ElementsList = response.body()?.toMutableList() ?: mutableListOf()
                    _element.value = ElementsList
                }else{
                    Log.d("API_Elment", "Loi elemnet: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<ElementsModel>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
    fun loadProductByElement(ide: String){
        RetrofitClient.instance.getProductsByElement(ide).enqueue(object : Callback<List<ProductsModel>>{
            override fun onResponse(
                call: Call<List<ProductsModel>>,
                response: Response<List<ProductsModel>>
            ) {
                if (response.isSuccessful){
                    val productsList = response.body()?.toMutableList() ?: mutableListOf()
                    _recommended.value = productsList
                }else{
                    Log.d("API_Element", "Lỗi response code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<ProductsModel>>, t: Throwable) {
                TODO("Not yet implemented")
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
    fun loadRecommended() {
        Log.d("API_REQUEST", "Gửi yêu cầu API để lấy sản phẩm được đề xuất...")

        RetrofitClient.instance.getRecommendedProducts(1).enqueue(object : Callback<List<ProductsModel>> {
            override fun onResponse(call: Call<List<ProductsModel>>, response: Response<List<ProductsModel>>) {
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
    fun loadBanners(){
        RetrofitClient.instance.getBanners().enqueue(object : Callback<List<BannersModel>> {
            override fun onResponse(
                call: Call<List<BannersModel>>,
                response: Response<List<BannersModel>>
            ) {
                if (response.isSuccessful) {
                    // Gán danh sách banner vào LiveData
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
            override fun onResponse(call: Call<ApiResponse>, response:Response<ApiResponse>) {
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
        onResult: (ApiResponse) -> Unit) {
        Log.d("AddCategory", "Đang gửi request với title: $title")
        val call = RetrofitClient.instance.addIngredient(title, url, idc)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response:Response<ApiResponse>) {
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
        onResult: (ApiResponse) -> Unit) {
        Log.d("AddCategory", "Đang gửi request với title: $title")
        val call = RetrofitClient.instance.addElement(title, url, quantity, iding)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response:Response<ApiResponse>) {
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
    fun updateElement(ide: String,
                      title: String,
                      url: String,
                      quantity: String,
                      iding: String,
                      onResult: (ApiResponse) -> Unit) {
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
    fun updateIngredient(iding: String,
                       title: String,
                       url: String,
                       idc: String,
                       onResult: (ApiResponse) -> Unit) {
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

}