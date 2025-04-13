package com.example.healplus.add
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.core.model.admin.MenuItems
import com.example.core.model.admin.menuItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(modifier: Modifier = Modifier, navController: NavController){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manager") }
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Chia thành 2 cột
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = paddingValues
        ) {
            items(menuItems) { menuItem ->
                MenuItemCard(menuItem) {
                    navController.navigate(menuItem.title.lowercase()) // Điều hướng khi nhấn vào mục
                }
            }
        }
    }
}
@Composable
fun MenuItemCard(menuItem: MenuItems, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = menuItem.url),
                contentDescription = menuItem.title,
                modifier = Modifier
                    .size(80.dp) // Kích thước ảnh
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = menuItem.title,
                fontSize = 16.sp,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}
