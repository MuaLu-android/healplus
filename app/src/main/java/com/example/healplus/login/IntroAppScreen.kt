package com.example.healplus.login


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.R
import kotlinx.coroutines.delay

@Composable
fun LottieLoadingAnimation(modifier: Modifier= Modifier, navController: NavController, authViewModel: AuthViewModel) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.main_screen))
    val composition1 by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.main_animation))
    var showSecondAnimation by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(3000) // Chờ 3 giây
        showSecondAnimation = true
        navController.navigate("onboarding")
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