package com.example.core.network.apis
import android.icu.text.StringSearch
import com.example.core.model.Api.ApiResponse
import com.example.core.model.Oder.Order
import com.example.core.model.banners.BannersModel
import com.example.core.model.categories.CategoryModel
import com.example.core.model.elements.ElementsModel
import com.example.core.model.ingredients.IngredientsModel
import com.example.core.model.products.ProductsModel
import com.example.core.model.revenue.RevenueResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.sql.Time
import java.time.LocalDate

interface ApiService {
    //GET
    @GET("get_ingredient_count.php")
    fun getIngredientCount(): Call<List<IngredientsModel>>
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
    @GET("get_products_by_category.php")
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
    @GET("get_oder.php")
    fun getOder(): Call<List<Order>>
    //POST
    @FormUrlEncoded
    @POST("add_user.php")
    fun addUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("phone") phone: String,
        @Field("url") url: String,
        @Field("role") role: String
    ): Call<ApiResponse>
    @FormUrlEncoded
    @POST("add_category.php")
    fun addCategory(
        @Field("title") title: String
    ): Call<ApiResponse>
    @FormUrlEncoded
    @POST("add_ingrident.php")
    fun addIngredient(
        @Field("title") title: String,
        @Field("url") url: String,
        @Field("idc") idc: String
    ): Call<ApiResponse>
    @FormUrlEncoded
    @POST("add_element.php")
    fun addElement(
        @Field("title") title: String,
        @Field("url") url: String,
        @Field("quantity") quantity: String,
        @Field("iding") iding: String
    ): Call<ApiResponse>
    @FormUrlEncoded
    @POST("update_category.php")
    fun updateCategory(
        @Field("idc") idc: String,
        @Field("title") title: String
    ): Call<ApiResponse>
    @FormUrlEncoded
    @POST("update_review.php")
    fun updateReview(
        @Field("idp") idc: String,
    ): Call<ApiResponse>
    @FormUrlEncoded
    @POST("update_ingredient.php")
    fun updateIngredient(
        @Field("iding") iding: String,
        @Field("title") title: String,
        @Field("url") url: String,
        @Field("idc") idc: String,
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("update_element.php")
    fun updateElement(
        @Field("ide") ide: String,
        @Field("title") title: String,
        @Field("url") url: String,
        @Field("quantity") quantity: String,
        @Field("iding") iding: String,
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("delete_category.php")
    fun deleteCategory(
        @Field("idc") idc: String
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("deldelete_ingredient.php")
    fun deleteIngnredient(
        @Field("iding") iding: String
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("update_element.php")
    fun deleteElement(
        @Field("ide") ide: String
    ): Call<ApiResponse>
    //oder
    @FormUrlEncoded
    @POST("oder.php")
    fun addOder(
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("idauth") idauth: String,
        @Field("address") address: String,
        @Field("datetime") datetime: LocalDate,
        @Field("note") note: String,
        @Field("quantity") quantity: Int,
        @Field("sumMoney") sumMoney: Float,
        @Field("status") status: String,
        @Field("detail") detail: String
    ): Call<ApiResponse>
    @FormUrlEncoded
    @POST("add_review.php")
    fun addReview(
        @Field("reviewerName") reviewerName: String,
        @Field("rating") rating: Float,
        @Field("comment") comment: String,
        @Field("date") date: String,
        @Field("profileImageUrl") profileImageUrl: String,
        @Field("idp") idp: String
    ): Call<ApiResponse>
    @FormUrlEncoded
    @POST("update_user.php")
    fun upDateUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("gender") gender: String,
        @Field("phone") phone: String,
        @Field("url") url: String,
        @Field("dateBirth") dateBirth: String,
        @Field("idauth") idauth: String
    ): Call<ApiResponse>
    @FormUrlEncoded
    @POST("update_idauth.php")
    fun upDateIdAuth(
        @Field("email") email: String,
        @Field("idauth") idauth: String
    ): Call<ApiResponse>
    @FormUrlEncoded
    @POST("add_product.php")
    fun addProduct(
        @Field("name") name: String,
        @Field("trademark") trademark: String,
        @Field("rating") rating: String,
        @Field("review") review: String,
        @Field("sold") sold: String,
        @Field("expiry") expiry: String,
        @Field("price") price: String,
        @Field("preparation") preparation: String,
        @Field("specification") specification: String,
        @Field("origin") origin: String,
        @Field("manufacturer") manufacturer: String,
        @Field("ingredient") ingredient: String,
        @Field("description") description: String,
        @Field("quantity") quantity: String,
        @Field("ide") ide: String,
        @Field("productiondate") productiondate: String,
        @Field("congdung") congdung: String,
        @Field("cachdung") cachdung: String,
        @Field("tacdungphu") tacdungphu: String,
        @Field("baoquan") baoquan: String,
        @Field("productImages") productImages: String,
        @Field("thanhphan") thanhphan: String,
        @Field("unitNames") unitNames: String
    ): Call<ApiResponse>
    @FormUrlEncoded
    @POST("update_oder_status.php")
    fun updateOrderStatus(
        @Field("id") orderId: Int,
        @Field("status") status: String
    ): Call<ApiResponse>
    @FormUrlEncoded
    @POST("get_oder_by_status.php")
    fun getOrderStatus(
        @Field("status") status: String
    ): Call<List<Order>>

    @FormUrlEncoded
    @POST("get_oder_by_user.php")
    fun getOderByUser(
        @Field("idauth") idauth: String
    ): Call<List<Order>>

    @FormUrlEncoded
    @POST("get_oder_by_userstatus.php")
    fun getOderByUserStatus(
        @Field("idauth") idauth: String,
        @Field("status") status: String
    ): Call<List<Order>>
    //revenue
    @GET("revenue_month.php")
    fun revenueMonth(
        @Query("month") month: Int,
        @Query("year") year: Int
    ): Call<RevenueResponse>
    @GET("revenue_week.php")
    fun revenueWeek(
        @Query("start_date") start_date: String
    ): Call<RevenueResponse>
    @GET("revenue_year.php")
    fun revenueYear(
        @Query("year") year: Int
    ): Call<RevenueResponse>
}