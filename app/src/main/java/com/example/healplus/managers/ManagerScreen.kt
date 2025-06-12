package com.example.healplus.managers
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.core.model.admin.MenuItems
import com.example.core.model.admin.menuItems
import com.example.core.model.categories.CategoryModel
import com.example.core.model.elements.ElementsModel
import com.example.core.model.ingredients.IngredientsModel
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import com.example.healplus.R
import kotlin.random.Random

@Composable
fun AddScreen(modifier: Modifier = Modifier,
              navController: NavController,
              viewModel: ApiCallViewModel = viewModel()
              ){
    val elements by viewModel.element.observeAsState(emptyList())
    LaunchedEffect(Unit) {
        viewModel.loadElement()
    }

    val categories = remember { mutableStateListOf<CategoryModel>() }
    val ingredient = remember { mutableStateListOf<IngredientsModel>() }
    val random = remember { Random(System.currentTimeMillis()) }
    var selectedMenu by remember { mutableStateOf<String?>(null) }
    Scaffold(
        topBar = {
            ManagersAppBarr(
                navController = navController,
                menuItems = menuItems,
            )
        }
    ) { paddingValues ->
        val colors = remember(elements) {
            elements.map {
                Color(
                    red = random.nextFloat(),
                    green = random.nextFloat(),
                    blue = random.nextFloat(),
                    alpha = 1f
                )
            }
        }
        Column (
            modifier = Modifier
                .padding(paddingValues),
        ){
            Spacer(Modifier.height(60.dp));
            DonutChart(elements, colors)
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(elements) { item ->
                    val color = colors[elements.indexOf(item)]
                    ProductCard(
                        data = item,
                        color = color,
                        onEdit = {},
                        onDelete = {}
                    )
                }
            }
        }
    }
}

@Composable
fun DonutChart(data: List<ElementsModel>, colors: List<Color> = emptyList()) {
    if (data.isEmpty()) {
        Text(text = "Không có dữ kiện")
        return
    }
    Box(modifier = Modifier
        .fillMaxWidth(),
        contentAlignment = Alignment.Center
        ){
        Canvas(
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
        ) {
            var startAngle = -90f
            val radius = size.minDimension / 2
            val center = center

            for (i in data.indices) {
                val item = data[i]
                val sweep = 360f * item.percentage / 100f

                drawArc(
                    color = colors[i],
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = false,
                    style = Stroke(width = 130f)
                )

                val labelAngle = Math.toRadians((startAngle + sweep / 2).toDouble())
                val labelRadius = radius * 0.7f
                val labelX = center.x + labelRadius * Math.cos(labelAngle)
                val labelY = center.y + labelRadius * Math.sin(labelAngle)

                drawContext.canvas.nativeCanvas.drawText(
                    "${item.percentage}%",
                    labelX.toFloat(),
                    labelY.toFloat(),
                    Paint().apply {
                        color = android.graphics.Color.WHITE
                        textSize = 30f
                        textAlign = Paint.Align.CENTER
                        isAntiAlias = true
                        setShadowLayer(0f, 0f, 0f, android.graphics.Color.WHITE)
                    }
                )

                startAngle += sweep
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagersAppBarr(
    navController: NavController,
    menuItems: List<MenuItems>,
    onClick: () -> Unit = {},
) {
    var showMenu by remember { mutableStateOf(false) }
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.managerappbar),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Quay lại"
                )
            }
        },
        actions = {
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(
                    imageVector =Icons.Default.MoreVert,
                    contentDescription = "Menu thêm"
                )
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                menuItems.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item.title) },
                        onClick = {
                            showMenu = false
                            navController.navigate(item.title.lowercase())
                        },
                        leadingIcon = {
                            Image(
                                painter = rememberAsyncImagePainter(model = item.url),
                                contentDescription = item.title,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    )
                }
            }
        }
    )
}
@Composable
fun ProductCard(
    data: ElementsModel,
    color: Color,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(color, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = data.title, fontWeight = FontWeight.Bold)
                Text(text = "Số lượng: ${data.quantity}")
                Text(text = "${data.percentage.toString()}%", color = Color.Gray)
            }

            IconButton(onClick = onEdit) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", tint = Color.Green)
            }

            IconButton(onClick = onDelete) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
            }
        }
    }
}
