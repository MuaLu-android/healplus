package com.example.healplus.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.core.model.products.ProductsModel
import com.example.core.tinydb.helper.ManagmentCart
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.R

@Composable
fun ProductDetailScreen(
    modifier: Modifier = Modifier,
    item: ProductsModel,
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val managmentCart = ManagmentCart(LocalContext.current, authViewModel.getUserId().toString())
    Scaffold(
        topBar = {
            ProductTopAppBar(navController)
        },
        bottomBar = {
            BottomAppBarView(onAddCartClick = {
                item.quantity = 1
                managmentCart.insertFood(item)
            }, navController)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = paddingValues
        ) {
            item {
                Text(
                    text = "Dac diem noi bat:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            item {
                ProductInfoItem(stringResource(R.string.categories), item.element_names)
                ProductInfoItem(stringResource(R.string.dogam_from), item.preparation)
                ProductInfoItem(stringResource(R.string.Specification), item.specification)
                ProductInfoItem(stringResource(R.string.origa), item.origin)
                ProductInfoItem(stringResource(R.string.Manufacturer), item.manufacturer)
                ProductInfoItem(stringResource(R.string.product), item.productiondate)
                ProductInfoItem(stringResource(R.string.expiry), item.expiry)
                ProductInfoItem(stringResource(R.string.Ingredient), item.ingredient)
                ProductInfoItem(stringResource(R.string.description), item.description)
                ProductInfoItem(stringResource(R.string.cachdung), item.cachdung)
                ProductInfoItem(stringResource(R.string.congdung), item.congdung)
                ProductInfoItem(stringResource(R.string.tacdungphu), item.tacdungphu)
                ProductInfoItem(stringResource(R.string.baoquan), item.baoquan)
            }
        }
    }
}