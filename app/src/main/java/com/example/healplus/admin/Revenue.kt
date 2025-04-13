package com.example.healplus.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.core.model.revenue.DetailedOrder
import com.example.core.model.revenue.RevenueData
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import com.example.healplus.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.delay
import java.text.DecimalFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

@Composable
fun MonthlyRevenueBarChart(navController: NavController) {
    val apiCallViewModel = remember { ApiCallViewModel() }
    var selectedFilter by remember { mutableStateOf("Doanh thu tháng") }
    var currentMonthYear by remember { mutableStateOf(YearMonth.now()) }
    var currentWeekStartDate by remember {
        mutableStateOf(LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)))
    }
    val weeklyRevenueData by apiCallViewModel.revenueWeek.observeAsState(initial = emptyList())
    val monthlyRevenueData by apiCallViewModel.revenueMonth.observeAsState(initial = emptyList())
    val yearlyRevenueData by apiCallViewModel.revenueYear.observeAsState(initial = emptyList())
    val historyWeek by apiCallViewModel.historyWeek.observeAsState(initial = emptyList())
    val historyMonth by apiCallViewModel.historyMonth.observeAsState(initial = emptyList())
    val historyYear by apiCallViewModel.historyYear.observeAsState(initial = emptyList())
    val isDataEmptyAfterLoad by apiCallViewModel.isDataEmptyAfterLoad.observeAsState(initial = true)
    var showNoDataMessage by remember { mutableStateOf(false) }
    LaunchedEffect(selectedFilter, currentMonthYear, currentWeekStartDate) {
        apiCallViewModel.clearRevenueData()
        showNoDataMessage = false
        when (selectedFilter) {
            "Doanh thu tuần" -> apiCallViewModel.revenueWeek(currentWeekStartDate)
            "Doanh thu tháng" -> apiCallViewModel.revenueMonth(
                currentMonthYear.monthValue,
                currentMonthYear.year
            )

            "Doanh thu năm" -> apiCallViewModel.revenueYear(currentMonthYear.year)
        }
    }
    LaunchedEffect(isDataEmptyAfterLoad) {
        if (!isDataEmptyAfterLoad) {
            delay(1000)
            showNoDataMessage = true
        } else {
            showNoDataMessage = false
        }
    }
    Scaffold(
        topBar = {
            RevenueTopBar(navController)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = paddingValues
        ) {
            item {
                RevenueFilterChips(
                    selectedFilter = selectedFilter,
                    onFilterSelected = { filter -> selectedFilter = filter }
                )
            }

            when (selectedFilter) {
                "Doanh thu tuần" -> {
                    item {
                        WeekSelectorAndTotal(
                            currentWeekStartDate = currentWeekStartDate,
                            onWeekChange = { newStartDate -> currentWeekStartDate = newStartDate },
                            weeklyRevenueData = weeklyRevenueData
                        )
                    }
                }

                "Doanh thu tháng" -> {
                    item {
                        MonthYearSelectorAndTotal(
                            currentMonthYear = currentMonthYear,
                            onMonthYearChange = { newMonthYear -> currentMonthYear = newMonthYear },
                            monthlyRevenueData = monthlyRevenueData
                        )
                    }
                }

                "Doanh thu năm" -> {
                    item {
                        YearSelectorAndTotal(
                            currentYear = currentMonthYear.year,
                            onYearChange = { newYear ->
                                currentMonthYear =
                                    YearMonth.of(newYear, currentMonthYear.monthValue)
                            },
                            yearlyRevenueData = yearlyRevenueData
                        )
                    }
                }
            }
            item {
                RevenueBarChart(
                    selectedFilter = selectedFilter,
                    weeklyRevenueData = weeklyRevenueData,
                    monthlyRevenueData = monthlyRevenueData,
                    yearlyRevenueData = yearlyRevenueData,
                    currentMonthYear = currentMonthYear,
                    currentWeekStartDate = currentWeekStartDate,
                    showNoDataMessage = showNoDataMessage
                )
            }
            item {
                HistoryRevenue(
                    selectedFilter = selectedFilter,
                    historyWeek = historyWeek,
                    historyMonth = historyMonth,
                    historyYear = historyYear,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun HistoryRevenue(
    selectedFilter: String,
    historyWeek: List<DetailedOrder>,
    historyMonth: List<DetailedOrder>,
    historyYear: List<DetailedOrder>,
    navController: NavController
) {
    val ordersToDisplay = when (selectedFilter) {
        "Doanh thu tuần" -> historyWeek
        "Doanh thu tháng" -> historyMonth
        "Doanh thu năm" -> historyYear
        else -> emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (ordersToDisplay.isNotEmpty()) {
            Text(
                text = "Lịch sử:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ordersToDisplay.forEach { order ->
                    OrderItem(order = order, navController = navController)
                    Divider()
                }
            }
        }
    }
}

@Composable
fun OrderItem(order: DetailedOrder, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = order.datetime,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "${DecimalFormat("#,###").format(order.sumMoney)} VNĐ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(
                onClick = { navController.navigate("order_detail_route/${order.id}") }, // Gọi lambda khi icon được nhấp
                modifier = Modifier.size(24.dp) // Đặt kích thước cho icon
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight, // Sử dụng icon mũi tên tiến
                    contentDescription = "Xem chi tiết đơn hàng",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant // Màu sắc icon
                )
            }
        }
    }
}

@Composable
fun WeekSelectorAndTotal(
    currentWeekStartDate: LocalDate,
    onWeekChange: (LocalDate) -> Unit,
    weeklyRevenueData: List<RevenueData>
) {
    val currentWeekEndDate = currentWeekStartDate.plusDays(6)
    val totalRevenue = weeklyRevenueData.sumOf { it.total_revenue }
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val weekRangeText =
        "${currentWeekStartDate.format(formatter)} - ${currentWeekEndDate.format(formatter)}"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Nút lùi tuần
            IconButton(onClick = { onWeekChange(currentWeekStartDate.minusWeeks(1)) }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Tuần trước")
            }
            Text(
                text = weekRangeText,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            // Nút tiến tuần
            IconButton(onClick = { onWeekChange(currentWeekStartDate.plusWeeks(1)) }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Tuần sau")
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${DecimalFormat("#,###").format(totalRevenue)} VNĐ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun YearSelectorAndTotal(
    currentYear: Int,
    onYearChange: (Int) -> Unit,
    yearlyRevenueData: List<RevenueData>
) {
    val totalRevenue = yearlyRevenueData.sumOf { it.total_revenue }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onYearChange(currentYear - 1) }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Năm trước")
            }
            Text(
                text = "Năm $currentYear",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            IconButton(onClick = { onYearChange(currentYear + 1) }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Năm sau")
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${DecimalFormat("#,###").format(totalRevenue)} VNĐ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun RevenueBarChart(
    selectedFilter: String,
    weeklyRevenueData: List<RevenueData>,
    monthlyRevenueData: List<RevenueData>,
    yearlyRevenueData: List<RevenueData>,
    currentMonthYear: YearMonth,
    currentWeekStartDate: LocalDate,
    showNoDataMessage: Boolean

) {
    val dataToDisplay = remember(
        selectedFilter,
        currentMonthYear,
        currentWeekStartDate,
        weeklyRevenueData,
        monthlyRevenueData,
        yearlyRevenueData
    ) {
        when (selectedFilter) {
            "Doanh thu tuần" -> weeklyRevenueData
            "Doanh thu tháng" -> {
                monthlyRevenueData.filter {
                    it.order_year == currentMonthYear.year && it.order_month == currentMonthYear.monthValue
                }
            }

            "Doanh thu năm" -> yearlyRevenueData
            else -> emptyList()
        }
    }
    val sortedData = remember(dataToDisplay, selectedFilter) {
        when (selectedFilter) {
            "Doanh thu tuần" -> dataToDisplay.sortedWith(
                compareBy(
                    { it.order_year },
                    { it.order_month },
                    { it.order_day })
            )

            "Doanh thu tháng" -> dataToDisplay.sortedWith(
                compareBy(
                    { it.order_year },
                    { it.order_month },
                    { it.order_day })
            )

            "Doanh thu năm" -> dataToDisplay.sortedWith(compareBy { it.order_month })
            else -> emptyList()
        }
    }

    val entries = sortedData.mapIndexed { index, data ->
        BarEntry(index.toFloat(), data.total_revenue.toFloat())
    }

    val xAxisLabels = sortedData.map { data ->
        when (selectedFilter) {
            "Doanh thu tuần" -> "Ngày ${data.order_day}/${data.order_month}"
            "Doanh thu tháng" -> "Ngày ${data.order_day}/${data.order_month}"
            "Doanh thu năm" -> "Tháng ${data.order_month}"
            else -> ""
        }
    }
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp),
        factory = { context ->
            BarChart(context).apply {
                description.isEnabled = false
                legend.isEnabled = false
                setFitBars(true)
                xAxis.apply {
                    valueFormatter = IndexAxisValueFormatter(xAxisLabels)
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(false)
                    granularity = 1f
                    labelRotationAngle = 0f
                    setCenterAxisLabels(false)
                    textColor = Color.Black.toArgb()
                }
                axisLeft.apply {
                    setDrawGridLines(false)
                    granularity = 1f
                    textColor = Color.Black.toArgb()
                    valueFormatter = object : ValueFormatter() {
                        private val format = DecimalFormat("#,###")
                        override fun getFormattedValue(value: Float): String {
                            return "${format.format(value)} VNĐ"
                        }
                    }
                }
                axisRight.isEnabled = false
                animateY(1000)
            }
        },
        update = { barChart ->
            if (entries.isNotEmpty()) {
                val dataSet = BarDataSet(entries, "Doanh thu").apply {
                    colors = ColorTemplate.VORDIPLOM_COLORS.toList()
                    valueTextColor = Color.Black.toArgb()
                    valueFormatter = object : ValueFormatter() {
                        private val format = DecimalFormat("#,###")
                        override fun getFormattedValue(value: Float): String {
                            return format.format(value)
                        }
                    }
                }
                val barData = BarData(dataSet)
                barChart.data = barData
                barChart.setTouchEnabled(true)
                barChart.isDragEnabled = true
                barChart.setScaleEnabled(true)
                barChart.setPinchZoom(true)
                barChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)
                barChart.invalidate()
            } else {
                barChart.clear()
                if (showNoDataMessage) {
                    barChart.setNoDataText("Không có dữ liệu doanh thu")
                } else {
                    barChart.setNoDataText("")
                }
                barChart.invalidate()
            }
        }
    )
}

@Composable
fun MonthYearSelectorAndTotal(
    currentMonthYear: YearMonth,
    onMonthYearChange: (YearMonth) -> Unit,
    monthlyRevenueData: List<RevenueData>
) {
    val totalRevenue = monthlyRevenueData.sumOf { it.total_revenue }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onMonthYearChange(currentMonthYear.minusMonths(1)) }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Tháng trước")
            }
            Text(
                text = "Tháng ${currentMonthYear.monthValue}/${currentMonthYear.year}",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            IconButton(onClick = { onMonthYearChange(currentMonthYear.plusMonths(1)) }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Tháng sau")
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${DecimalFormat("#,###").format(totalRevenue)} VNĐ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun RevenueFilterChips(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit
) {
    val filters = listOf("Doanh thu tuần", "Doanh thu tháng", "Doanh thu năm")
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(filters) { filter ->
            FilterChip(
                selected = filter == selectedFilter,
                onClick = { onFilterSelected(filter) },
                label = { Text(filter) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RevenueTopBar(navController: NavController) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.revenue),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Quay lại"
                )
            }
        }
    )
}
