package com.example.healplus.Model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

data class ProductsModel(
    val ide: String = "",
    val idp: String ="",
    val name: String ="",
    val trademark: String ="",
    var rating: Double = 0.0,
    var review: Double = 0.0,
    var comment: Double = 0.0,
    var price: Double = 0.0,
    var unit_names: ArrayList<String> = ArrayList(),
    val categoryItemName: String ="",
    val preparation: String ="",
    val specification: String ="",
    val origin: String ="",
    val manufacturer: String ="",
    val production: String ="",
    val ingredient: String ="",
    val description: String ="",
    val register: String ="",
    var quantity: Int ,
    var product_images: ArrayList<String> = ArrayList(),
    val showRecommended: Int
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.createStringArrayList() as ArrayList<String>,
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.createStringArrayList() as ArrayList<String>,
        parcel.readInt(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ide)
        parcel.writeString(idp)
        parcel.writeString(name)
        parcel.writeString(trademark)
        parcel.writeDouble(rating)
        parcel.writeDouble(review)
        parcel.writeDouble(comment)
        parcel.writeDouble(price)
        parcel.writeStringList(unit_names)
        parcel.writeString(categoryItemName)
        parcel.writeString(preparation)
        parcel.writeString(specification)
        parcel.writeString(origin)
        parcel.writeString(manufacturer)
        parcel.writeString(production)
        parcel.writeString(ingredient)
        parcel.writeString(description)
        parcel.writeString(register)
        parcel.writeInt(quantity)
        parcel.writeStringList(product_images) // Ghi danh sách model
        parcel.writeInt(showRecommended)
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
// Adapter để chuyển đổi Int thành Boolean
class ShowRecommendedAdapter : JsonDeserializer<Boolean>, JsonSerializer<Boolean> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): Boolean {
        return json.asInt == 1
    }

    override fun serialize(src: Boolean, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(if (src) 1 else 0)
    }
}

