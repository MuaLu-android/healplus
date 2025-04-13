package com.example.core.tinydb.helper

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.core.model.address.AddressModel
import com.example.core.model.products.ProductsModel
import com.google.gson.Gson

class AddAddress(val context: Context, userId: String) {
    private val tinyDB = TinyDB(context)
    val address = "address-${userId}"
    fun insertFood(item: AddressModel) {
        Log.d("ManagmentCart", "Start inser:")
        val list = getListAddress()
        Log.d("ManagmentCart", "Trước khi thêm: $list")
        val existAlready = list.any { it.addressDetail == item.addressDetail && it.addressType == item.addressType }

        if (existAlready) {
            Toast.makeText(context, "Đã có địa chỉ vui lòng nhập lại", Toast.LENGTH_SHORT).show()
        } else {
            list.add(item)
        }
        Log.d("ManagmentCart", "Dữ liệu trước khi lưu: ${Gson().toJson(list)}")
        tinyDB.putListAddress(address, list)
        Log.d("ManagmentCart", "Dữ liệu đã lưu thành công")
        Log.d("ManagmentCart", "Sau khi thêm: $list")
    }
    fun getListAddress(): ArrayList<AddressModel> {
        val listCart = tinyDB.getListAddress(address) ?: arrayListOf()
        Log.d("ManagmentCart", "Danh sách sản phẩm trong giỏ hàng: $listCart")
        return listCart
    }
    fun getListAddressOder(): ArrayList<AddressModel> {
        val listCart = tinyDB.getListAddressOder(address) ?: arrayListOf()
        Log.d("ManagmentCart", "Danh sách sản phẩm trong giỏ hàng: $listCart")
        return listCart
    }
}