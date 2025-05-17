package com.example.core.model.products

import android.os.Parcel
import android.os.Parcelable

data class ReviewItem(
    val reviewerName: String,
    val rating: Float,
    val comment: String,
    val date: String,
    val profileImageUrl: String? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        reviewerName = parcel.readString() ?: "",
        rating = parcel.readFloat(),
        comment = parcel.readString() ?: "",
        date = parcel.readString() ?: "",
        profileImageUrl = parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(reviewerName)
        parcel.writeFloat(rating)
        parcel.writeString(comment)
        parcel.writeString(date)
        parcel.writeString(profileImageUrl)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<ReviewItem> {
        override fun createFromParcel(parcel: Parcel): ReviewItem {
            return ReviewItem(parcel)
        }
        override fun newArray(size: Int): Array<ReviewItem?> {
            return arrayOfNulls(size)
        }
    }
}
