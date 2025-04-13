package com.example.core.tinydb.helper

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.core.model.products.ProductsModel
import com.google.gson.Gson

class ManagmentCart(val context: Context, userId: String) {

    private val tinyDB = TinyDB(context)
    val CartKey = "CartList-${userId}"
    fun insertFood(item: ProductsModel) {
        Log.d("ManagmentCart", "Start inser:")
        var listFood = getListCart()
        Log.d("ManagmentCart", "Trước khi thêm: $listFood")
        val existAlready = listFood.any { it.name == item.name }
        val index = listFood.indexOfFirst { it.name == item.name }

        if (existAlready) {
            listFood[index].quantity += item.quantity
        } else {
            listFood.add(item)
        }
        Log.d("ManagmentCart", "Dữ liệu trước khi lưu: ${Gson().toJson(listFood)}")
        tinyDB.putListObject(CartKey, listFood)
        Log.d("ManagmentCart", "Dữ liệu đã lưu thành công")
        Log.d("ManagmentCart", "Sau khi thêm: $listFood")
        Toast.makeText(context, "Them vao gio hang", Toast.LENGTH_SHORT).show()
    }

    fun getListCart(): ArrayList<ProductsModel> {
        Log.d("ManagmentCart", "Start getList")
//        return tinyDB.getListObject("CartList") ?: arrayListOf()
        val listCart = tinyDB.getListObject(CartKey) ?: arrayListOf()
        Log.d("ManagmentCart", "Danh sách sản phẩm trong giỏ hàng: $listCart")
        return listCart
    }

    fun minusItem(listFood: ArrayList<ProductsModel>, position: Int, listener: ChangeNumberItemsListener) {
        if (listFood[position].quantity == 1) {
            removeItemByProduct(listFood[position], listener)
        } else {
            listFood[position].quantity--
        }
        tinyDB.putListObject(CartKey, listFood)
        listener.onChanged()
    }

    fun plusItem(listFood: ArrayList<ProductsModel>, position: Int, listener: ChangeNumberItemsListener) {
        if (position !in listFood.indices) return
        listFood[position].quantity++
        tinyDB.putListObject(CartKey, listFood)
        listener.onChanged()
    }
    fun removeItemByProduct(item: ProductsModel, listener: ChangeNumberItemsListener) {
        val listFood = getListCart()
        Log.d("ManagmentCart", "Sản phẩm cần xóa:${Gson().toJson(item)}")
        Log.d("ManagmentCart", "Giỏ hàng trước khi xóa: ${Gson().toJson(listFood)}")
        val index = listFood.indexOfFirst { it.idp == item.idp }

        if (index != -1) {
            listFood.removeAt(index)
            tinyDB.putListObject(CartKey, listFood)
            listener.onChanged()
            Log.d("ManagmentCart", "Đã xóa sản phẩm: ${item.name}")
        } else {
            Log.d("ManagmentCart", "Không tìm thấy sản phẩm để xóa: ${item.name}")
        }
    }
}