package com.example.healplus.Screen.HomeScreen

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.compose.errorContainerDarkHighContrast
import com.example.compose.errorContainerLight
import com.example.compose.inversePrimaryDark
import com.example.compose.tertiaryDarkHighContrast
import com.example.healplus.Model.IngredientsModel
import com.example.healplus.Model.CategoryModel
import com.example.healplus.Model.ProductsModel
import com.example.healplus.Model.BannersModel
import com.example.healplus.R
import com.example.healplus.ViewModel.AuthSate
import com.example.healplus.ViewModel.AuthViewModel
import com.example.healplus.ViewModel.CategoryProductViewModel
import kotlinx.coroutines.delay

@Composable
fun MainActivityScreen(navController: NavController, authViewModel: AuthViewModel){
    val viewModel = CategoryProductViewModel()
    val banners = remember  { mutableStateListOf<BannersModel>() }
    val categories = remember { mutableStateListOf<CategoryModel>() }
    val ingredient = remember { mutableStateListOf<IngredientsModel>() }
    val recommended = remember { mutableStateListOf<ProductsModel>() }
    var showBannerLoading by remember { mutableStateOf(true) }
    var showCategoryLoading by remember { mutableStateOf(true) }
    var showRecommendedLoading by remember { mutableStateOf(true) }
    var showIngredient by remember { mutableStateOf(true) }
    val authSate = authViewModel.authSate.observeAsState()
    LaunchedEffect(authSate.value) {
        when(authSate.value){
            is AuthSate.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }
    //Banners
    LaunchedEffect(Unit) {
        Log.d("BANNER_LOG", "Bắt đầu loadBanners()")
        viewModel.loadBanners()
        viewModel.banners.observeForever {
            banners.clear()
            banners.addAll(it)
            showBannerLoading = false
        }

    }
    //Categories
    LaunchedEffect(Unit) {
        viewModel.loadCategory()
        viewModel.categories.observeForever{
            categories.clear()
            categories.addAll(it)
            Log.d("LaunchedEffect", "item Categories nhân đc:${categories.size}")
            showCategoryLoading = false
        }
    }
    //prominent category
    LaunchedEffect(Unit) {
        viewModel.loadIngredients()
        viewModel.ingredient.observeForever{
            ingredient.clear()
            ingredient.addAll(it)
            showIngredient = false
        }
    }
    //Recommended
    LaunchedEffect(Unit) {
        viewModel.loadRecommended()
        viewModel.recommended.observeForever{
            recommended.clear()
            recommended.addAll(it)
            Log.d("LaunchedEffect", "item Categories nhân đc:${recommended.size}")
            showRecommendedLoading = false
        }
    }
    Scaffold (
        topBar = { MediumTopAppBar(navController, categories, showCategoryLoading) }
    ){paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            val (scrollList, bottomMenu) = createRefs()
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(scrollList) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    }
            ){
                //banners
                item {
                    if (showBannerLoading){
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ){
                            CircularProgressIndicator(

                            )
                        }
                    }else{
                        Banners(banners)
                    }
                }
                //category
                item {
                    SectionTitle(R.string.categories, R.string.see_all)
                }
                item {
                    if (showCategoryLoading){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                            contentAlignment = Alignment.Center
                        ){
                        }
                    }else{
                        CategoryList(categories, navController)
                    }
                }
                //Ingredients
                item {
                    UnSectionTitle(R.string.prominment_category)
                }
                item {
                    if (showCategoryLoading){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                            contentAlignment = Alignment.Center
                        ){
                        }
                    }else{
                        IngredientScreen (ingredient, navController)
                    }
                }
                //recommended
                item {
                    SectionTitle(R.string.recommended, R.string.see_all)
                }
                item {
                    if (showRecommendedLoading){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                            contentAlignment = Alignment.Center){
                        }
                    }else{
                        ListItems(recommended, navController)
                    }
                }
                item {
                    Spacer(
                        modifier = Modifier
                            .height(100.dp)
                    )
                }
            }
//            BottomMenu(modifier = Modifier
//                .fillMaxWidth()
//                .constrainAs(bottomMenu) {
//                    bottom.linkTo(parent.bottom)
//                },
//                onItemClick = onCartClick,
//                authViewModel
//            )
        }

    }


}

@Composable
fun IngredientScreen(
    categoriesItems: List<IngredientsModel>,
    navController: NavController) {
    var selectedIndex by remember { mutableStateOf(-1) }
    Log.d("ProminentCategory", "Danh sách có ${categoriesItems.size} phần tử")
    Box(
        modifier = Modifier
            .height(424.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier // Đảm bảo không bị lỗi kích thước
            ) {
                items(categoriesItems.size) { index ->
                    val item = categoriesItems[index]
                    CategoryItem1(
                        item = item,
                        iSelected = selectedIndex == index,
                        onItemClick = {
                            selectedIndex = index
                            navController.navigate("category/${categoriesItems[index].iding}/${categoriesItems[index].title}")
                        }
                    )
                }
            }
        }
    }

}
@Composable
fun CategoryList(categories: List<CategoryModel>,  navController: NavController) {
    var selectedIndex by remember { mutableStateOf(-1) }
//    val context = LocalContext.current
    LazyRow (
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp)
    ){
        items(categories.size) { index -> CategoryItem(
            item = categories[index],
            iSelected = selectedIndex == index,
            onItemClick = {
                selectedIndex = index
//                oPenElements(categories[index].idc, categories[index].title)
                navController.navigate("category/${categories[index].idc}/${categories[index].title}")
//                navController.navigate("home")
            }
           )
        }
    }
}
@Composable
fun CategoryItem(
    item: CategoryModel,
    iSelected: Boolean,
    onItemClick: () -> Unit) {
    Row (modifier = Modifier
        .fillMaxHeight()
        .clickable(onClick = onItemClick)
        .background(
            color = if (iSelected) inversePrimaryDark else tertiaryDarkHighContrast,
            shape = RoundedCornerShape(8.dp)
        ),
        verticalAlignment = Alignment.CenterVertically){
        Text(
            text = item.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp),
        )
    }
}
@Composable
fun CategoryItem1(
    item: IngredientsModel,
    iSelected: Boolean,
    onItemClick: () -> Unit) {
    Row (modifier = Modifier
        .clickable(onClick = onItemClick)
        .background(
            color = if (iSelected) errorContainerLight else inversePrimaryDark,
            shape = RoundedCornerShape(8.dp)
        )
        .padding(vertical = 4.dp, horizontal = 8.dp)
        .height(60.dp),
        verticalAlignment = Alignment.CenterVertically){
        AsyncImage(
            model = item.url,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
        )
        Text(
            text = item.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 8.dp),
            textAlign = TextAlign.Start        )
    }
}
@Composable
fun Banners(banners: List<BannersModel>) {
    AutoSlidingCarousel(banners = banners)
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AutoSlidingCarousel(modifier: Modifier = Modifier,
                        banners: List<BannersModel>){
    val paperState = rememberPagerState ( pageCount = { banners.size } )
    val isDragged by paperState.interactionSource.collectIsDraggedAsState()
    // Auto-scroll khi không bị kéo
    LaunchedEffect(isDragged) {
        while (!isDragged) {
            delay(6000) // Chuyển trang sau mỗi 3 giây
            val nextPage = (paperState.currentPage + 1) % banners.size
            paperState.animateScrollToPage(nextPage)
        }
    }
    Column(modifier = Modifier
        .fillMaxWidth()) {
        HorizontalPager(state = paperState) {
                page ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(banners[page].url)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    )
                    .fillMaxWidth()
                    .height(150.dp)
            )
        }
        DotIndicator(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterHorizontally),
            totalDots = banners.size,
            selectedIndex = paperState.currentPage,
            dotHeight = 6.dp,
            dotWidthOff = 8.dp,
            dotWidth = 80.dp
        )
    }
}
@Composable
fun DotIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color = colorResource(R.color.purple_500),
    unSelectedColor: Color = colorResource(R.color.grey),
    dotHeight: Dp,
    dotWidthOff: Dp,
    dotWidth: Dp,
    dotShape: Shape = RoundedCornerShape(50)
){
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(totalDots) { index ->
            IndicatorDot(
                color = if (index == selectedIndex) selectedColor else unSelectedColor,
                height = dotHeight,
                width = if (index == selectedIndex) dotWidth else dotWidthOff,
                shape = dotShape
            )
            Spacer(modifier = Modifier.width(4.dp)) // Khoảng cách giữa các dot
        }
    }
}
@Composable
fun IndicatorDot(
    modifier: Modifier = Modifier,
    height: Dp,
    width: Dp,
    shape: Shape,
    color: Color
) {
    Box(
        modifier = modifier
            .height(height)
            .width(width)
            .clip(shape)
            .background(color = color)
    )
}
@Composable
fun SectionTitle(
    @StringRes title: Int,
    @StringRes actionText: Int
){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = stringResource(id = title),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = stringResource(id = actionText)
        )
    }
}
@Composable
fun UnSectionTitle(
    @StringRes title: Int
){
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp,
                start = 16.dp
            ),
        text = stringResource(id = title),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
}