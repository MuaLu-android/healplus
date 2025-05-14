package com.example.core.viewmodel.apiviewmodel

import androidx.lifecycle.ViewModel
import com.example.core.model.products.ProductsModel
import javax.inject.Inject

class OderViewModel @Inject constructor(): ViewModel() {
    var selectedProducts: List<ProductsModel> = listOf()
}