package com.example.healplus.login

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.core.viewmodel.authviewmodel.AuthSate
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.acitivity.AdminActivity
import com.example.healplus.acitivity.MainActivity
import com.example.healplus.R
import kotlinx.coroutines.delay

@Composable
fun OnboardingScreen(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    val authSate = authViewModel.authSate.observeAsState()
    LaunchedEffect(authSate.value) {
        delay(3000)
        if (authSate.value is AuthSate.Admin) {
            // Chuyển sang MainActivity khi đã đăng nhập
            val intent = Intent(context, AdminActivity::class.java)
            context.startActivity(intent)
        }
        if (authSate.value is AuthSate.User) {
            val intent1 = Intent(context, MainActivity::class.java)
            context.startActivity(intent1)
        }

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(id = R.drawable.logo_app),
                contentDescription = "Shopping Bag",
                modifier = Modifier
                    .width(200.dp)
                    .height(246.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = stringResource(R.string.intro_app_welcom),
                fontSize = 19.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                lineHeight = 33.sp
            )
        }

        Spacer(modifier = Modifier.height(106.dp))

        Button(
            onClick = {
                navController.navigate("login")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
            ,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = stringResource(id = R.string.intro_next),
                fontSize = 22.sp)
        }
        Row (
            modifier = Modifier
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = stringResource(id = R.string.have_an_account))
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        navController.navigate("signup")
                    }
            )
        }
    }
}