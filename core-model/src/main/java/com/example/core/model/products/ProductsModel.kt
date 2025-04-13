package com.example.core.model.products

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.Gson

data class ProductsModel(
    val idp: String = "",
    val name: String = "",
    val trademark: String = "",
    var rating: Double = 0.0,
    var review: Int = 0,
    var comment: Int = 0,
    var price: Int = 0,
    val preparation: String = "",
    val origin: String = "",
    val manufacturer: String = "",
    val description: String = "",
    val showRecommended: Int = 0,
    val ide: String = "",
    val productiondate: String = "",
    val specification: String = "",
    val ingredient: String = "",
    var quantity: Int = 0, // Thay đổi kiểu dữ liệu thành String?
    val congdung: String = "",
    val cachdung: String = "",
    val tacdungphu: String = "",
    val baoquan: String = "",
    var product_images: ArrayList<String> = ArrayList(),
    var unit_names: ArrayList<String> = ArrayList(),
    var element_names: String = "", // Thêm trường element_names
    var ingredients: ArrayList<IngredientDetail> = ArrayList() // Thêm trường ingredients
) : Parcelable {

    constructor(parcel: Parcel) : this(
        idp = parcel.readString() ?: "",
        name = parcel.readString() ?: "",
        trademark = parcel.readString() ?: "",
        rating = parcel.readDouble(),
        review = parcel.readInt(),
        comment = parcel.readInt(),
        price = parcel.readInt(),
        preparation = parcel.readString() ?: "",
        origin = parcel.readString() ?: "",
        manufacturer = parcel.readString() ?: "",
        description = parcel.readString() ?: "",
        showRecommended = parcel.readInt(),
        ide = parcel.readString() ?: "",
        productiondate = parcel.readString() ?: "",
        specification = parcel.readString() ?: "",
        ingredient = parcel.readString() ?: "",
        quantity = parcel.readInt(),
        congdung = parcel.readString() ?: "",
        cachdung = parcel.readString() ?: "",
        tacdungphu = parcel.readString() ?: "",
        baoquan = parcel.readString() ?: "",
        product_images = parcel.createStringArrayList() ?: arrayListOf(),
        unit_names = parcel.createStringArrayList() ?: arrayListOf(),
        element_names = parcel.readString() ?: "",
        ingredients = parcel.createTypedArrayList(IngredientDetail) ?: arrayListOf()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idp)
        parcel.writeString(name)
        parcel.writeString(trademark)
        parcel.writeDouble(rating)
        parcel.writeInt(review)
        parcel.writeInt(comment)
        parcel.writeInt(price)
        parcel.writeString(preparation)
        parcel.writeString(origin)
        parcel.writeString(manufacturer)
        parcel.writeString(description)
        parcel.writeInt(showRecommended)
        parcel.writeString(ide)
        parcel.writeString(productiondate)
        parcel.writeString(specification)
        parcel.writeString(ingredient)
        parcel.writeInt(quantity)
        parcel.writeString(congdung)
        parcel.writeString(cachdung)
        parcel.writeString(tacdungphu)
        parcel.writeString(baoquan)
        parcel.writeStringList(product_images)
        parcel.writeStringList(unit_names)
        parcel.writeString(element_names)
        parcel.writeTypedList(ingredients)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductsModel> {
        override fun createFromParcel(parcel: Parcel): ProductsModel {
            return ProductsModel(parcel)
        }

        override fun newArray(size: Int): Array<ProductsModel?> {
            return arrayOfNulls(size)
        }

        fun fromJson(json: String): ProductsModel {
            return Gson().fromJson(json, ProductsModel::class.java)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }
}

data class IngredientDetail(
    val title: String = "",
    val body: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        title = parcel.readString() ?: "",
        body = parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(body)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IngredientDetail> {
        override fun createFromParcel(parcel: Parcel): IngredientDetail {
            return IngredientDetail(parcel)
        }

        override fun newArray(size: Int): Array<IngredientDetail?> {
            return arrayOfNulls(size)
        }
    }
}