package com.example.core.model.address

import android.os.Parcelable
import androidx.annotation.DrawableRes
import java.io.Serializable

data class PaymentMethod(
    val name: String, @DrawableRes val icon: Int
): Serializable
