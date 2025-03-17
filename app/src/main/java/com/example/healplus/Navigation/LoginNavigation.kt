package com.example.healplus.Navigation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.login.CreateAccountScreen
import com.example.healplus.login.LoginScreen
import com.example.healplus.login.LottieLoadingAnimation
import com.example.healplus.login.OnboardingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel, navController: NavHostController) {
    NavHost(navController = navController, startDestination = "intropApp") {
        composable("introApp") { LottieLoadingAnimation(modifier, navController, authViewModel) }
        composable("onboarding") { OnboardingScreen(modifier, navController, authViewModel) }
        composable("signup") { CreateAccountScreen(modifier, navController, authViewModel) }
        composable("login") { LoginScreen(modifier, navController, authViewModel) }
    }
}