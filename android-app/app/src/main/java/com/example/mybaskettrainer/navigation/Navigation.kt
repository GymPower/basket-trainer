package com.example.mybaskettrainer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mybaskettrainer.ui.screens.LoginScreen
import com.example.mybaskettrainer.ui.screens.MainScreen
import com.example.mybaskettrainer.ui.screens.RegisterScreen
import com.example.mybaskettrainer.ui.screens.SplashScreen

@Composable
fun Navigation(

){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.SplashScreen.route
        ){
        composable(Routes.SplashScreen.route){
            SplashScreen(navController)
        }
        composable(Routes.MainScreen.route){
            MainScreen()
        }
        composable(Routes.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(Routes.RegisterScreen.route) {
            RegisterScreen(navController)
        }


    }
}
