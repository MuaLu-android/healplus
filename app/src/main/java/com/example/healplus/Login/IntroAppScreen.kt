package com.example.healplus.Login


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.LottieDrawable
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.healplus.R
import com.example.healplus.ViewModel.AuthSate
import com.example.healplus.ViewModel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun LottieLoadingAnimation(modifier: Modifier= Modifier, navController: NavController, authViewModel: AuthViewModel) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.main_screen))
    val composition1 by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.main_animation))
    val authSate = authViewModel.authSate.observeAsState()
    var showSecondAnimation by remember { mutableStateOf(false) }
    LaunchedEffect(authSate.value) {
        delay(3000)
        when(authSate.value){
            is AuthSate.Authenticated -> navController.navigate("home")
            is AuthSate.Unauthenticated -> navController.navigate("onboarding")
            else -> Unit
        }
    }
    LaunchedEffect(Unit) {
        delay(1000)  // Chờ 3 giây
        showSecondAnimation = true
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.size(150.dp)
        )
        if (showSecondAnimation) {
            LottieAnimation(
                composition = composition1,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}