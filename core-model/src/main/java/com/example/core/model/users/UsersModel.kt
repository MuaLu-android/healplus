package com.example.core.model.users
data class UserAuthModel(
    var idauth: String = "",
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var phone: String = "",
    var gender: String = "",
    var dateBirth: String = "",
    var url: String = "",
    var bonuspoint: String = "",
    var token: String = "",
    val role: String = "user"
)
