package com.example.healplus.settings
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.acitivity.LoginActivity
import com.example.healplus.R

@Composable
fun SettingScreen(modifier: Modifier = Modifier,
                  navController: NavController,
                  authViewModel: AuthViewModel
){

    Scaffold (
        topBar = {
            SettingTopAppBar(navController)
        })
     {paddingValues ->
         LazyColumn(
             modifier = Modifier.fillMaxSize()
             .padding(paddingValues), // Dùng paddingValues để tránh che khuất
         contentPadding = PaddingValues(bottom = 184.dp) // Thêm padding dưới cùng
         ) {
             item {
                 Row(
                     modifier = Modifier
                         .fillMaxWidth()
                         .padding(horizontal = 16.dp),
                     horizontalArrangement = Arrangement.SpaceBetween,
                     verticalAlignment = Alignment.CenterVertically
                 ) {
                     Text(
                         text = "Thống kê doanh thu",
                         fontSize = 16.sp,
                         fontWeight = FontWeight.Bold
                     )
                     Text(
                         text = "Xem tất cả",
                         fontSize = 14.sp,
                         color = Color(0xFF007BFF),
                         modifier = Modifier
                             .clickable { navController.navigate("oderscreen") }
                     )
                 }
             }
             item { SectionTitle(title = R.string.personal) }
             item { SettingsItem(title = R.string.profile,
                 onClick = {
                     navController.navigate("profile")
                 }
                 ) }
             item { SettingsItem(title = R.string.address) }
             item { SettingsItem(title = R.string.Paysmethoot) }

             // Shop Section
             item { SectionTitle(title = R.string.shop) }
             item { SettingsItem(title = R.string.country, value = "Vietnam") }
             item { SettingsItem(title = R.string.currency, value = "$ USD") }
             item { SettingsItem(title = R.string.size, value = "UK") }
             item { SettingsItem(title = R.string.terms) }

             // Account Section
             item { SectionTitle(title = R.string.account) }
             item { SettingsItem(title = R.string.lauguage, value = "English") }
             item { SettingsItem(title = R.string.about) }

             // Delete Account Button
             item { DeleteAccountButton() }
             item { LogOut(authViewModel) }

         }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingTopAppBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.settings),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

        },
        navigationIcon = {
            IconButton(onClick = {navController.popBackStack() }) {
                Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = null)
            }
        }
    )
}

@Composable
fun SectionTitle(@StringRes title: Int) {
    Text(
        text = stringResource(id = title),
        fontSize = 20.sp,
        color = Color.Gray,
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
    )
}

@Composable
fun SettingsItem(@StringRes title: Int, value: String? = null, onClick: () -> Unit ? = {}) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(id = title), fontSize = 16.sp, modifier = Modifier.weight(1f))
            if (value != null) {
                Text(
                    text = value,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            Icon(imageVector = Icons.Default.ArrowForward,
                contentDescription = "Arrow",
                modifier = Modifier
                    .clickable { onClick() }
                )
        }
        Spacer()
    }
}
@Composable
fun DeleteAccountButton() {
    Text(
        text = stringResource(R.string.delete_account),
        fontSize = 16.sp,
        color = Color.Red,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Xử lý khi nhấn */ }
            .padding(16.dp),
        textAlign = TextAlign.Start
    )
}
@Composable
fun LogOut(authViewModel: AuthViewModel) {
    val context = LocalContext.current
    Text(
        text = stringResource(R.string.logout),
        fontSize = 16.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                authViewModel.signOut()
                context.startActivity(Intent(context, LoginActivity::class.java))
            }
            .padding(top = 24.dp),
        textAlign = TextAlign.Center
    )
}
@Composable
fun Spacer(){
    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )
    }
}
@Composable
fun SpacerProduct(){
    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(Color.Gray)
        )
    }
}