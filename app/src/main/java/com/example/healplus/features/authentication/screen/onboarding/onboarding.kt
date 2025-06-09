package com.example.healplus.features.authentication.screen.onboarding
import IntroAppWelcome
import TextSkipPage
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healplus.R
import com.example.healplus.common.styles.ButtonStyle

@Composable
fun OnboardingScreen(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App Welcome
        IntroAppWelcome()
        Spacer(modifier = modifier.height(106.dp))
        // Next login
        ButtonStyle(title = R.string.intro_next, onPressed = {navController.navigate("login")})
        // Next signup
        TextSkipPage(title = R.string.account, icon = Icons.AutoMirrored.Filled.ArrowForward, onPressed = {navController.navigate("signup")})
    }
}



