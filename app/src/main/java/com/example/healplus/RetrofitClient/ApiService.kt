package com.example.healplus.RetrofitClient

import com.example.healplus.Model.ProductsModel
import com.example.healplus.Model.BannersModel
import com.example.healplus.Model.CategoryModel
import com.example.healplus.Model.ElementsModel
import com.example.healplus.Model.IngredientsModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("getbanner.php")
    fun getBanners(): Call<List<BannersModel>>

    @GET("getIngredient.php")
    fun getIngredient(): Call<List<IngredientsModel>>
    @GET("get_product_showRecomment.php")
    fun getRecommendedProducts(
        @Query("showRecommended") showRecommended: Int = 0
    ): Call<List<ProductsModel>>

    @GET("getcategory.php")
    fun getCategories(): Call<List<CategoryModel>>

    @GET("get_products_by_category.php") // Đường dẫn API PHP trên server
    fun getProductsByCategory(@Query("idc") idc: String): Call<List<ProductsModel>>
    @GET("get_products_by_ingredient.php")
    fun getProductsByIngredient(@Query("iding") iding: String): Call<List<ProductsModel>>

    @GET("get_products_by_element.php")
    fun getProductsByElement(@Query("ide") ide: String): Call<List<ProductsModel>>

    @GET("get_ingredient_by_category.php")
    fun getIngredientByCategory(@Query("idc") idc: String): Call<List<IngredientsModel>>

    @GET("get_elements_by_ingredient.php")
    fun getElementByIngredient(@Query("iding") iding: String): Call<List<ElementsModel>>
}