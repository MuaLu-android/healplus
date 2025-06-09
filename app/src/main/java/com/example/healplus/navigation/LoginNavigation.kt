package com.example.healplus.navigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.features.authentication.screen.login.LoginScreen
import com.example.healplus.features.authentication.screen.onboarding.OnboardingScreen
import com.example.healplus.features.authentication.screen.splash.LottieLoadingAnimation
import com.example.healplus.features.authentication.screen.signup.CreateAccountScreen

@Composable
fun LoginNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel, navController: NavHostController) {
    NavHost(navController = navController, startDestination = "introApp") {
        composable("introApp") { LottieLoadingAnimation(modifier, navController) }
        composable("onboarding") { OnboardingScreen(modifier, navController) }
        composable("signup") { CreateAccountScreen(modifier, navController, authViewModel) }
        composable("login") { LoginScreen(modifier, navController, authViewModel) }
    }
}