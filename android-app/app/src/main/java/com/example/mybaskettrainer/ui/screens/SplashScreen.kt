package com.example.mybaskettrainer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.mybaskettrainer.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "MyBasketTrainer",
            color = Color.White
        )
    }

    LaunchedEffect(Unit) {
        delay(2000L)
        navController.navigate(Routes.LoginScreen.route) {
            popUpTo(Routes.SplashScreen.route) { inclusive = true }
        }
    }
}
