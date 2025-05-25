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
        val listFood = getListCart()
        val existAlready = listFood.any { it.name == item.name }
        val index = listFood.indexOfFirst { it.name == item.name }

        if (existAlready) {
            listFood[index].quantity += item.quantity
        } else {
            listFood.add(item)
        }
        tinyDB.putListObject(CartKey, listFood)
        Toast.makeText(context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show()
    }

    fun getListCart(): ArrayList<ProductsModel> {
        val listCart = tinyDB.getListObject(CartKey) ?: arrayListOf()
        return listCart
    }

    fun minusItem(listFood: ArrayList<ProductsModel>, position: Int, listener: ChangeNumberItemsListener) {
        if (listFood[position].quantity == 1) {
            listFood[position].quantity
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
        val index = listFood.indexOfFirst { it.idp == item.idp }

        if (index != -1) {
            listFood.removeAt(index)
            tinyDB.putListObject(CartKey, listFood)
            listener.onChanged()
        } else {
            Log.d("ManagmentCart", "Không tìm thấy sản phẩm để xóa: ${item.name}")
        }
    }
    fun resetCart() {
        tinyDB.remove(CartKey)
        Toast.makeText(context, "Đã xóa toàn bộ giỏ hàng", Toast.LENGTH_SHORT).show()
    }
    fun getItemCount(): Int {
        return getListCart().size
    }
}