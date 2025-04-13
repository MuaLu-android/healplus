package com.example.core.model.address

data class AddressModel(
    val fullName: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val province: String = "",
    val addressDetail: String = "",
    val addressType: String = "",
    val isDefault: Boolean
)
