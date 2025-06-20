package com.example.healplus.acitivity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.navigation.LoginNavigation
import com.example.healplus.ui.theme.AppTheme

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authViewModel: AuthViewModel by viewModels()

        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    LoginMain(authViewModel = authViewModel,
                        modifier = Modifier
                            .padding(innerPadding))
                }
            }
        }
    }
}
@Composable
fun LoginMain(modifier: Modifier = Modifier,
              authViewModel: AuthViewModel){
    val navController = rememberNavController()
    LoginNavigation(modifier, authViewModel, navController)
}