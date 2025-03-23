package com.example.core.network.apis

import android.icu.text.StringSearch
import com.example.core.model.Api.ApiResponse
import com.example.core.model.banners.BannersModel
import com.example.core.model.categories.CategoryModel
import com.example.core.model.elements.ElementsModel
import com.example.core.model.ingredients.IngredientsModel
import com.example.core.model.products.ProductsModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
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

    @GET("getsearch.php")
    fun getSearchProduct(@Query("search") search: String): Call<List<ProductsModel>>


    @FormUrlEncoded
    @POST("add_category.php") // Đường dẫn file PHP trên server
    fun addCategory(
        @Field("title") title: String
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("update_category.php") // Đường dẫn API cập nhật danh mục
    fun updateCategory(
        @Field("idc") idc: String,
        @Field("title") title: String
    ): Call<ApiResponse>
}