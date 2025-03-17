package com.example.core.model.users

import android.net.Uri

data class UserModel(
    var userId: String = "",
    var email: String = "",
    var fullName: String = "",
    var phoneNumber: String = "",
    var localImageUrl: String = "",
    val role: String = "user"
)
