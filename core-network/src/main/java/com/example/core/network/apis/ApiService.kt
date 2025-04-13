package com.example.core.network.apis

import com.example.core.model.Api.ApiResponse
import com.example.core.model.banners.BannersModel
import com.example.core.model.categories.CategoryModel
import com.example.core.model.elements.ElementsModel
import com.example.core.model.ingredients.IngredientsModel
import com.example.core.model.products.ProductsModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
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
    @GET("getelemets.php")
    fun getElement(): Call<List<ElementsModel>>
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
    @POST("add_ingredient.php") // Đường dẫn file PHP trên server
    fun addIngredient(
        @Field("title") title: String,
        @Field("url") url: String,
        @Field("idc") idc: String
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("add_element.php") // Đường dẫn file PHP trên server
    fun addElement(
        @Field("title") title: String,
        @Field("url") url: String,
        @Field("quantity") quantity: String,
        @Field("iding") iding: String
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("update_category.php") // Đường dẫn API cập nhật danh mục
    fun updateCategory(
        @Field("idc") idc: String,
        @Field("title") title: String
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("update_ingredient.php") // Đường dẫn API cập nhật danh mục
    fun updateIngredient(
        @Field("iding") iding: String,
        @Field("title") title: String,
        @Field("url") url: String,
        @Field("idc") idc: String,
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("update_element.php") // Đường dẫn API cập nhật danh mục
    fun updateElement(
        @Field("ide") ide: String,
        @Field("title") title: String,
        @Field("url") url: String,
        @Field("quantity") quantity: String,
        @Field("iding") iding: String,
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("delete_category.php") // Đường dẫn API cập nhật danh mục
    fun deleteCategory(
        @Field("idc") idc: String
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("deldelete_ingredient.php") // Đường dẫn API cập nhật danh mục
    fun deleteIngnredient(
        @Field("iding") iding: String
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("update_element.php") // Đường dẫn API cập nhật danh mục
    fun deleteElement(
        @Field("ide") ide: String
    ): Call<ApiResponse>
    //oder
    @FormUrlEncoded
    @POST("oder.php") // Đường dẫn API cập nhật danh mục
    fun addOder(
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("userId") userId: String,
        @Field("address") address: String,
        @Field("quantity") quantity: String,
        @Field("sumMoney") sumMoney: String,
        @Field("bonuspoint") bonuspoint: String,
        @Field("detail") detail: String
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("add_product.php")
    fun addProduct(
        @Field("name") name: String,
        @Field("trademark") trademark: String,
        @Field("rating") rating: String, // Thay đổi kiểu dữ liệu thành Float
        @Field("review") review: String,
        @Field("comment") comment: String,
        @Field("price") price: String,
        @Field("preparation") preparation: String,
        @Field("specification") specification: String,
        @Field("origin") origin: String,
        @Field("manufacturer") manufacturer: String,
        @Field("ingredient") ingredient: String,
        @Field("description") description: String,
        @Field("quantity") quantity: String,
        @Field("showRecommended") showRecommended: String, // Thay đổi kiểu dữ liệu thành Int
        @Field("ide") ide: String,
        @Field("productiondate") productiondate: String, // Thêm trường productiondate
        @Field("congdung") congdung: String, // Thêm trường congdung
        @Field("cachdung") cachdung: String, // Thêm trường cachdung
        @Field("tacdungphu") tacdungphu: String, // Thêm trường tacdungphu
        @Field("baoquan") baoquan: String, // Thêm trường baoquan
        @Field("productImages") productImages: String, // Gửi dưới dạng String
        @Field("thanhphan") thanhphan: String, // Thêm trường thanhphan và gửi dưới dạng String
        @Field("unitNames") unitNames: String // Đổi tên và gửi dưới dạng String
    ): Call<ApiResponse>
}