package com.example.healplus.features.authentication.screen.splash
import SplashAnimation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.healplus.R
import kotlinx.coroutines.delay
@Composable
fun LottieLoadingAnimation(modifier: Modifier= Modifier, navController: NavController, ) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.main_screen))
    // splash screen delay
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("onboarding")
    }
    SplashAnimation(modifier, composition)
}

