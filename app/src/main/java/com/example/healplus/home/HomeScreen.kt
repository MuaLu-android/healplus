package com.example.healplus.home

import android.net.Uri
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.core.model.banners.BannersModel
import com.example.core.model.categories.CategoryModel
import com.example.core.model.ingredients.IngredientsModel
import com.example.core.model.products.ProductsModel
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import com.example.core.viewmodel.authviewmodel.AuthSate
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.R
import com.example.healplus.category.ListItems
import com.example.healplus.ui.theme.errorContainerLight
import com.example.healplus.ui.theme.errorDarkHighContrast
import com.example.healplus.ui.theme.inverseOnSurfaceLight
import com.example.healplus.ui.theme.inverseOnSurfaceLightMediumContrast
import com.example.healplus.ui.theme.inversePrimaryDark
import com.example.healplus.ui.theme.inversePrimaryLight
import com.example.healplus.ui.theme.inversePrimaryLightHighContrast
import com.example.healplus.ui.theme.onPrimaryLightMediumContrast
import com.example.healplus.ui.theme.onTertiaryLightHighContrast
import com.example.healplus.ui.theme.primaryDark
import com.example.healplus.ui.theme.surfaceBrightLight
import com.example.healplus.ui.theme.tertiaryDarkHighContrast
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun MainActivityScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    viewModel: ApiCallViewModel = viewModel()
) {
    val banners = remember { mutableStateListOf<BannersModel>() }
    val categories = remember { mutableStateListOf<CategoryModel>() }
    val ingredient = remember { mutableStateListOf<IngredientsModel>() }
    val recommended = remember { mutableStateListOf<ProductsModel>() }
    var showBannerLoading by remember { mutableStateOf(true) }
    var showCategoryLoading by remember { mutableStateOf(true) }
    var showRecommendedLoading by remember { mutableStateOf(true) }
    var showIngredient by remember { mutableStateOf(true) }
    val authSate = authViewModel.authSate.observeAsState()

    LaunchedEffect(authSate.value) {
        when (authSate.value) {
            is AuthSate.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }
    LaunchedEffect(Unit) {
        viewModel.loadBanners()
        viewModel.banners.observeForever {
            banners.clear()
            banners.addAll(it)
            showBannerLoading = false
        }

    }
    LaunchedEffect(Unit) {
        viewModel.loadCategory()
        viewModel.categories.observeForever {
            categories.clear()
            categories.addAll(it)
            showCategoryLoading = false
        }
    }
    LaunchedEffect(Unit) {
        viewModel.loadIngredientCount()
        viewModel.ingredient.observeForever {
            ingredient.clear()
            ingredient.addAll(it)
            showIngredient = false
        }
    }
    LaunchedEffect(Unit) {
        viewModel.loadRecommended()
        viewModel.recommended.observeForever {
            recommended.clear()
            recommended.addAll(it)
            showRecommendedLoading = false
        }
    }
    Scaffold(
        topBar = { MediumTopAppBar(navController, categories, showCategoryLoading, authViewModel) }
    ) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            val (scrollList, bottomMenu) = createRefs()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(scrollList) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    }
            ) {
                item {
                    if (showBannerLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        Banners(banners)
                    }
                }
                item {
                    if (showCategoryLoading) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                        }
                    } else {
                        Column {
                            Spacer(modifier = Modifier.height(16.dp))
                            CategoryList(categories, navController)
                        }
                    }
                }
                item {
                    if (showRecommendedLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                        }
                    } else {
                        Column (
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            SectionTitle(R.string.recommended, R.string.see_all)
                            ListItems(recommended, navController)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
                item {
                    if (showCategoryLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                        }
                    } else {
                        Column(
                            modifier = Modifier,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            IngredientScreen(ingredient, navController)
                        }
                    }
                }
                item {
                    if (showRecommendedLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                        }
                    }else {
                        DrugStoreInfoScreen()
                    }
                }
                item {
                    Spacer(
                        modifier = Modifier
                            .padding(paddingValues)
                    )
                }
            }
        }

    }


}

@Composable
fun IngredientScreen(
    categoriesItems: List<IngredientsModel>,
    navController: NavController
) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(-1) }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            UnSectionTitle(R.string.prominment_category)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val displayedItems = if (isExpanded)
                    categoriesItems
                else
                    categoriesItems.take(4)

                for (i in displayedItems.indices step 2) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            IngredientItem(
                                item = displayedItems[i],
                                iSelected = selectedIndex == i,
                                onItemClick = {
                                    selectedIndex = i
                                    navController.navigate(
                                        "category/${displayedItems[i].iding}/${displayedItems[i].title}"
                                    )
                                }
                            )
                        }

                        if (i + 1 < displayedItems.size) {
                            Box(modifier = Modifier.weight(1f)) {
                                IngredientItem(
                                    item = displayedItems[i + 1],
                                    iSelected = selectedIndex == (i + 1),
                                    onItemClick = {
                                        selectedIndex = i + 1
                                        navController.navigate(
                                            "category/${displayedItems[i + 1].iding}/${displayedItems[i + 1].title}"
                                        )
                                    }
                                )
                            }
                        } else {
                            Box(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
            IconButton(
                onClick = { isExpanded = !isExpanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(end = 16.dp)
            ) {
                Icon(
                    imageVector = if (isExpanded)
                        Icons.Rounded.KeyboardArrowUp
                    else
                        Icons.Rounded.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Show less" else "Show more",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
@Composable
fun DrugStoreInfoScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    InfoItem(
                        modifier = Modifier.weight(1f),
                        icon = painterResource(R.drawable.genuine),
                        title = "Thuốc chính hãng",
                        subtitle = "đa dạng và chuyên sâu"
                    )
                    InfoItem(
                        modifier = Modifier.weight(1f),
                        icon = painterResource(R.drawable.return_of_investment),
                        title = "Đổi trả trong 30 ngày",
                        subtitle = "kể từ ngày mua hàng"
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    InfoItem(
                        modifier = Modifier.weight(1f),
                        icon = painterResource(R.drawable.tag),
                        title = "Cam kết 100%",
                        subtitle = "chất lượng sản phẩm"
                    )
                    InfoItem(
                        modifier = Modifier.weight(1f),
                        icon = painterResource(R.drawable.free_shipping),
                        title = "Miễn phí vận chuyển",
                        subtitle = "theo chính sách giao hàng"
                    )
                }
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Thông tin cửa hàng",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "© 2007 - 2024 Công ty Cổ Phần Dược Phẩm HealPlus",
                )

                Text(
                    text = "Số ĐKKD 0315275368 cấp ngày 17/09/2025 tại Sở Kế hoạch Đầu tư THHN",
                )

                Divider()

                ContactInfoItem(
                    icon = Icons.Rounded.LocationOn,
                    content = "379-381 Xuân Thủy, P. Xuân Thủy, Q.Cầu Giấy, TP. HN"
                )

                ContactInfoItem(
                    icon = Icons.Rounded.Phone,
                    content = "(028)73023456"
                )

                ContactInfoItem(
                    icon = Icons.Rounded.Email,
                    content = "sale@healplus.com.vn"
                )

                ContactInfoItem(
                    icon = Icons.Rounded.Person,
                    content = "Người quản lý nội dung: Lầu A Lử"
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_app),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

        }
    }
}

@Composable
private fun InfoItem(
    modifier: Modifier = Modifier,
    icon: Painter,
    title: String,
    subtitle: String
) {
    Card(
        modifier = modifier
            .animateContentSize(
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearOutSlowInEasing
            )
        ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(32.dp)
                    .padding(bottom = 8.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ContactInfoItem(
    icon: ImageVector,
    content: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
@Composable
fun CategoryList(categories: List<CategoryModel>, navController: NavController) {
    var selectedIndex by remember { mutableStateOf(-1) }
    LazyRow(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(categories.size) { index ->
            CategoryItem(
                item = categories[index],
                iSelected = selectedIndex == index,
                onItemClick = {
                    selectedIndex = index
                    navController.navigate("category/${categories[index].idc}/${categories[index].title}")
                }
            )
        }
    }
}

@Composable
fun CategoryItem(
    item: CategoryModel,
    iSelected: Boolean,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(50.dp)
            .wrapContentWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true),
                onClick = onItemClick
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (iSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (iSelected) 4.dp else 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = item.title,
                color = if (iSelected) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}

@Composable
fun IngredientItem(
    item: IngredientsModel,
    iSelected: Boolean,
    onItemClick: () -> Unit
) {
    val colors = listOf(
        inverseOnSurfaceLight, inversePrimaryLight, surfaceBrightLight, onPrimaryLightMediumContrast,
        inverseOnSurfaceLightMediumContrast, onTertiaryLightHighContrast, inversePrimaryLightHighContrast,
        primaryDark, errorDarkHighContrast
    )
    val backgroundColor1 = colors[Random.nextInt(colors.size)]
    Row(
        modifier = Modifier
            .clickable(onClick = onItemClick)
            .background(
                color = backgroundColor1,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .width(150.dp)
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.url,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
        )
        Text(
            text = item.title,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(start = 8.dp),
            textAlign = TextAlign.Start,
            maxLines = 2,
        )
    }
}

@Composable
fun Banners(banners: List<BannersModel>) {
    AutoSlidingCarousel(banners = banners)
}

@Composable
fun AutoSlidingCarousel(
    modifier: Modifier = Modifier,
    banners: List<BannersModel>
) {
    val paperState = rememberPagerState(pageCount = { banners.size })
    val isDragged by paperState.interactionSource.collectIsDraggedAsState()
    LaunchedEffect(isDragged) {
        while (!isDragged) {
            delay(6000)
            val nextPage = (paperState.currentPage + 1) % banners.size
            paperState.animateScrollToPage(nextPage)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        HorizontalPager(state = paperState) { page ->
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
) {
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
            Spacer(modifier = Modifier.width(4.dp))
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
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
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
) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = stringResource(id = title),
        fontWeight = FontWeight.Bold
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediumTopAppBar(navController: NavController,
                    categories: List<CategoryModel>,
                    showCategoryLoading: Boolean,
                    viewModel: AuthViewModel) {
    var expanded by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.getCurrentUser()
    }
    TopAppBar(
        title = {

        },
        navigationIcon = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { expanded = true },
                    modifier = Modifier
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu, contentDescription = null,
                        modifier = Modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.logo_group_app1),
                    contentDescription = "logo_app",
                    modifier = Modifier
                        .size(120.dp)
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start)
                )
            }

        },
        actions = {
            IconButton(onClick = {
                navController.navigate("search")
            }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
            }
            IconButton(onClick = {
                navController.navigate("add")
            }) {
                Icon(imageVector = Icons.Filled.Notifications, contentDescription = null)
            }
        }
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .zIndex(2f)
            .fillMaxHeight()
            .offset(x = 0.dp, y = -5.dp)
    ) {
        if (showCategoryLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(Color.Yellow)
                ) {
                    Column {
                        UserView(viewModel, navController)
                        Notification1("Thông báo", navController)
                    }
                }
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.title) },
                        onClick = {
                            navController.navigate("category/${category.idc}/${category.title}")
                            expanded = false
                        }
                    )
                }
            }

        }
    }
}

@Composable
fun Notification1(tile: String,
                  navController: NavController) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(tile)
        IconButton(
            onClick = {navController.navigate("notification")}
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
            )
        }
    }
}

@Composable
fun UserView(viewModel: AuthViewModel, navController: NavController) {
    val user by viewModel.user.observeAsState()
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(top = 40.dp)
        .clickable {
            navController.navigate("profile")
        },
        verticalAlignment = Alignment.CenterVertically) {
        user?.let { userData ->
            val imageUri = userData.url?.let { Uri.parse(it) }
            Log.d("UserProfileScreen", "localImageUrl: ${userData.url}")
            Log.d("UserProfileScreen", "Parsed imageUri: $imageUri")
            GlideImage(
                imageModel = { imageUri },
                modifier = Modifier
                    .padding(10.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )
            Column (
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            ){
                InfoRow(value = userData.name)
                InfoRow(value = userData.phone)
            }
        }
    }
}
@Composable
fun InfoRow(value: String) {
    Text(text = value, fontWeight = FontWeight.Light,
        modifier = Modifier.padding(bottom = 4.dp))
}
@Composable
fun CategoryItemTopBar(item: CategoryModel, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(top = 8.dp)
    ) {
        Text(
            text = item.title
        )
        IconButton(
            onItemClick
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null
            )
        }
    }
}