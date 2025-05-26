package com.example.healplus.category
import android.net.Uri
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.healplus.R
import com.example.core.model.categories.CategoryModel
import com.example.core.model.elements.ElementsModel
import com.example.core.model.ingredients.IngredientsModel
import com.example.core.model.products.ProductsModel
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import com.google.accompanist.flowlayout.FlowRow
import kotlin.random.Random
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import java.text.NumberFormat
import java.util.Locale


@Composable
fun CategoryScreen(
    id: String,
    title: String,
    viewModel: ApiCallViewModel,
    navController: NavController
) {
    val category by viewModel.categories.observeAsState(emptyList())
    val ingredients by viewModel.ingredient.observeAsState(emptyList())
    val element by viewModel.element.observeAsState(emptyList())
    val product by viewModel.recommended.observeAsState(emptyList())
    val isLoading by remember { mutableStateOf(false) }
    var currentDisplayState by rememberSaveable { mutableStateOf(DisplayState.INGREDIENTS_FOR_CATEGORY) }
    var titlec by remember { mutableStateOf(title) }
    var titleing by remember { mutableStateOf(title) }
    var titlelm by remember { mutableStateOf(title) }
    var idc by remember { mutableStateOf(id) }
    var iding by remember { mutableStateOf(id) }
    var idcelm by remember { mutableStateOf(id) }
    LaunchedEffect(idc) {
        viewModel.loadIngredientByCategory(idc)
        viewModel.loadProductByCategory(idc)
        viewModel.loadElementByIngredient(idc)
        currentDisplayState = DisplayState.INGREDIENTS_FOR_CATEGORY
        titlec = category.find { it.idc == idc }?.title ?: title
    }
    LaunchedEffect(iding) {
        viewModel.loadElementByIngredient(iding)
        viewModel.loadProductByIngredient(iding)
        currentDisplayState = DisplayState.ELEMENTS_FOR_INGREDIENT
    }
    LaunchedEffect(idcelm) {
        viewModel.loadProductByElement(idcelm)
        currentDisplayState = DisplayState.PRODUCT_FOR_ELEMENT
    }
    LaunchedEffect(Unit) {
        viewModel.loadCategory()
        currentDisplayState = DisplayState.INGREDIENTS_FOR_CATEGORY
    }
    Scaffold (
        topBar = {TopAppBarCategory(titlec, navController)}
    ){paddingValues ->
      Column (
          modifier = Modifier
              .padding(paddingValues)
      ){
          if (isLoading) {
              CircularProgressIndicator(modifier = Modifier
                  .fillMaxSize())
          } else {
              Column(
                  modifier = Modifier
                      .fillMaxSize()
              ) {
                  CategoryTabs(category, idc) { selectedIdc, selectedTitle, _ ->
                          idc = selectedIdc
                          titlec = selectedTitle
                          currentDisplayState = DisplayState.INGREDIENTS_FOR_CATEGORY
                  }

                  LazyColumn(
                      modifier = Modifier
                          .weight(1f)
                  ) {
                      if (currentDisplayState == DisplayState.ELEMENTS_FOR_INGREDIENT ||
                          (currentDisplayState == DisplayState.PRODUCT_FOR_ELEMENT)){
                          item {
                              val currentTitle = when (currentDisplayState) {
                                  DisplayState.ELEMENTS_FOR_INGREDIENT -> ingredients.find { it.iding == iding }?.title ?: "Element"
                                  DisplayState.INGREDIENTS_FOR_CATEGORY -> category.find { it.idc == idc }?.title ?: "Thành phần"
                                  DisplayState.PRODUCT_FOR_ELEMENT -> element.find { it.ide == idcelm }?.title ?: "Sản phẩm"
                              }
                              ShowTitleIngrendinet(currentTitle){
                                  when (currentDisplayState) {
                                      DisplayState.ELEMENTS_FOR_INGREDIENT -> {
                                          currentDisplayState =
                                              DisplayState.INGREDIENTS_FOR_CATEGORY
                                          idc = category.find { it.idc == idc }?.idc ?: idc
                                          iding = ingredients.find { it.iding == iding }?.iding ?: iding
                                          titlec = category.find { it.idc == idc }?.title ?: title
                                      }
                                      DisplayState.INGREDIENTS_FOR_CATEGORY -> {
                                      }
                                      DisplayState.PRODUCT_FOR_ELEMENT -> {
                                          currentDisplayState =
                                              DisplayState.ELEMENTS_FOR_INGREDIENT
                                          idcelm = element.find { it.ide == idcelm }?.ide ?: ""
                                          titlec = category.find { it.idc == idc }?.title ?: title
                                      }
                                  }
                              }
                          }
                      }
                      if (currentDisplayState == DisplayState.INGREDIENTS_FOR_CATEGORY && ingredients.isNotEmpty()) {
                          item {
                              FlowRow(
                                  modifier = Modifier
                                      .background(Color.White)
                                      .fillMaxWidth()
                                      .padding(horizontal = 8.dp, vertical = 8.dp),
                                  mainAxisSpacing = 8.dp,
                                  crossAxisSpacing = 8.dp
                              ) {
                                  ingredients.forEach { ingredients2 ->
                                      Box(
                                          modifier = Modifier
                                              .width((LocalConfiguration.current.screenWidthDp.dp / 2) - 16.dp)
                                      ) {
                                          CategoryItem12(ingredients2) { id, title ->
                                              iding = id
                                              titleing = title
                                              currentDisplayState = DisplayState.ELEMENTS_FOR_INGREDIENT
                                          }
                                      }
                                  }
                              }
                          }
                      }else if (currentDisplayState == DisplayState.ELEMENTS_FOR_INGREDIENT) {
                          item {
                              FlowRow(
                                  modifier = Modifier
                                      .background(Color.White)
                                      .fillMaxWidth()
                                      .padding(horizontal = 8.dp, vertical = 8.dp),
                                  mainAxisSpacing = 8.dp,
                                  crossAxisSpacing = 8.dp
                              ) {
                                  element.forEach { elementItem ->
                                      Box(
                                          modifier = Modifier
                                              .width((LocalConfiguration.current.screenWidthDp.dp / 2) - 16.dp)
                                      ) {
                                          CategoryItem123(elementItem) { selectedElementId, selectedElementTitle ->
                                              idcelm = selectedElementId
                                              titlelm = selectedElementTitle
                                          }
                                      }
                                  }
                              }
                          }
                      }
                      item {
                          Text(
                              text = "Danh sách sản phẩm",
                              style = MaterialTheme.typography.titleLarge,
                              modifier = Modifier.padding(8.dp)
                          )
                      }
                      item(product){
                          FlowRow(
                              modifier = Modifier.fillMaxWidth(),
                              mainAxisSpacing = 16.dp,
                              crossAxisSpacing = 16.dp
                          ) {
                              product.forEach { productItem ->
                                  Box(
                                      modifier = Modifier
                                          .width((LocalConfiguration.current.screenWidthDp.dp / 2) - 16.dp)
                                  ) {
                                      RecommendedList1(productItem, navController)
                                  }
                              }
                          }
                      }
                  }
              }
          }
      }
    }
}

@Composable
fun ShowTitleIngrendinet(titleing: String, onBackClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {onBackClicked()}
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 8.dp)
            )
        }
        Text(
            text = titleing,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun CategoryTabs(categories: List<CategoryModel>, selectedIdc: String,
                 onCategorySelected: (String, String, Boolean) -> Unit) {
    var selectedIndex by rememberSaveable  { mutableStateOf(0) }
    val showLoadingIngredient by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(selectedIdc) {
        selectedIndex = categories.indexOfFirst { it.idc == selectedIdc }.takeIf { it >= 0 } ?: 0
    }
    LazyRow (
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .background(Color.LightGray)
    ){
        itemsIndexed(categories) { index, category ->
            CategoryItem12(
                categories = category,
                iSelected = index == selectedIndex,
                onItemClick = {
                    selectedIndex = index
                    onCategorySelected(category.idc, category.title, showLoadingIngredient)
                }
            )
        }
    }
}

@Composable
fun CategoryItem12(categories: CategoryModel,
                   iSelected: Boolean,
                   onItemClick: () -> Unit) {
    Column {
        if (iSelected){
            Spacer(modifier = Modifier
                .height(1.dp)
                .background(Color.Black)
            )
        }
        Box(
            modifier = Modifier
                .height(60.dp)
                .width(110.dp)
                .clickable(onClick = onItemClick)
                .background(
                    color = if (iSelected) Color.White else Color.LightGray,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = categories.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 2,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .fillMaxWidth()
            )
        }
    }
    

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarCategory(
    title: String,
    navController: NavController
){
   TopAppBar(
       title ={
           Text(
               text = title,
               modifier = Modifier
                   .fillMaxWidth(),
               textAlign = TextAlign.Start
           )
       },
       navigationIcon = {
           Box {
               IconButton(onClick = {
                   navController.navigate("home")
               }) {
                   Icon(imageVector = Icons.Filled.Home, contentDescription = null)
               }
           }
       },
   )
}
@Composable
fun CategoryItem12(
    item: IngredientsModel,
    onItemClick: (String, String) -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(3f)
            .clickable { onItemClick(item.iding, item.title) }
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.url,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = item.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
@Composable
fun CategoryItem123(
    item: ElementsModel,
    onItemClick: (String, String) -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(3f)
            .clickable { onItemClick(item.ide, item.title) }
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.url,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = item.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
@Composable
fun RecommendedList1(items: ProductsModel, navController: NavController) {
    val colors = listOf(
        inverseOnSurfaceLight, inversePrimaryLight, surfaceBrightLight, onPrimaryLightMediumContrast,
        inverseOnSurfaceLightMediumContrast, onTertiaryLightHighContrast, inversePrimaryLightHighContrast,
        primaryDark, errorDarkHighContrast
    )
    val backgroundColor1 = colors[Random.nextInt(colors.size)]
    Column (modifier = Modifier
        .padding(8.dp)
        .height(280.dp)
    ){
        AsyncImage(
            model = items.product_images.firstOrNull(),
            contentDescription = null,
            modifier = Modifier
                .width(175.dp)
                .background(backgroundColor1, shape = RoundedCornerShape(8.dp))
                .height(180.dp)
                .padding(top = 16.dp, end = 16.dp, start = 16.dp)
                .clickable {
                    navController.navigate("detail/${Uri.encode(Gson().toJson(items))}")
                },
            contentScale = ContentScale.Crop
        )
        Text(
            text = items.name,
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
                    text = items.rating.toString(),
                    color = Color.Black,
                    fontSize = 15.sp
                )
            }
            Text(
                text = NumberFormat.getCurrencyInstance(Locale("vi", "VN")).format(items.price).toString(),
                color = colorResource(R.color.purple_200),
                maxLines = 1,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
enum class DisplayState {
    INGREDIENTS_FOR_CATEGORY,
    ELEMENTS_FOR_INGREDIENT,
    PRODUCT_FOR_ELEMENT,
}