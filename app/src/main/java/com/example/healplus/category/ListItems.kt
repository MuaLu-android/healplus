package com.example.healplus.category

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.core.model.products.ProductsModel
import com.example.healplus.R
import com.example.healplus.ui.theme.errorDarkHighContrast
import com.example.healplus.ui.theme.inverseOnSurfaceLight
import com.example.healplus.ui.theme.inverseOnSurfaceLightMediumContrast
import com.example.healplus.ui.theme.inversePrimaryLight
import com.example.healplus.ui.theme.inversePrimaryLightHighContrast
import com.example.healplus.ui.theme.onPrimaryLightMediumContrast
import com.example.healplus.ui.theme.onTertiaryLightHighContrast
import com.example.healplus.ui.theme.primaryDark
import com.example.healplus.ui.theme.surfaceBrightLight
import com.google.gson.Gson
import kotlin.random.Random

@Composable
fun ListItems(items: List<ProductsModel>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .height(500.dp)
            .padding(start = 8.dp, end = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items.size){
            row ->
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                RecommendedList(items, row, navController)
            }
        }
    }
}
@Composable
fun ListItemsFullSize(items: List<ProductsModel>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items.size){
                row ->
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                RecommendedList(items, row, navController)
            }
        }
    }
}
@Composable
fun RecommendedList(items: List<ProductsModel>, row: Int, navController: NavController) {
    val colors = listOf(inverseOnSurfaceLight, inversePrimaryLight, surfaceBrightLight, onPrimaryLightMediumContrast,
        inverseOnSurfaceLightMediumContrast, onTertiaryLightHighContrast, inversePrimaryLightHighContrast,
        primaryDark, errorDarkHighContrast)
    val backgroundColor1 = colors[Random.nextInt(colors.size)]
    Column (modifier = Modifier
        .padding(8.dp)
        .height(280.dp)
    ){
     AsyncImage(
         model = items[row].product_images.firstOrNull(),
         contentDescription = null,
         modifier = Modifier
             .width(175.dp)
             .background(backgroundColor1, shape = RoundedCornerShape(8.dp))
             .height(180.dp)
             .padding(top = 16.dp, end = 16.dp, start = 16.dp)
             .clickable {
//                 openProduct(items[row])
                 navController.navigate("detail/${Uri.encode(Gson().toJson(items[row]))}")
             },
         contentScale = ContentScale.Crop
     )
        Text(
            text = items[row].name,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 8.dp)
        )
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween){
            Row {
                Image(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
                Spacer(
                    modifier = Modifier
                        .width(8.dp)
                )
                Text(
                    text = items[row].rating.toString(),
                    color = Color.Black,
                    fontSize = 15.sp
                )
            }
            Text(
                text = "${items[row].price}00 VND",
                color = colorResource(R.color.purple_200),
                maxLines = 1,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
