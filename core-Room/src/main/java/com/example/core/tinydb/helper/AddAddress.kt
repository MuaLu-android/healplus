package com.example.core.tinydb.helper

import android.content.Context
import com.example.core.model.address.AddressModel

class AddAddress(val context: Context, userId: String) {
    private val tinyDB = TinyDB(context)
    val address = "address-${userId}"
    fun insertFood(item: AddressModel) {
        val list = getListAddress()
        val existAlready = list.any { it.addressDetail == item.addressDetail && it.addressType == item.addressType }
        if (existAlready) {
        } else {
            list.add(item)
        }
        tinyDB.putListAddress(address, list)
    }
    fun getListAddress(): ArrayList<AddressModel> {
        val listCart = tinyDB.getListAddress(address) ?: arrayListOf()
        return listCart
    }
    fun getListAddressOder(): ArrayList<AddressModel> {
        val listCart = tinyDB.getListAddressOder(address) ?: arrayListOf()
        return listCart
    }
}