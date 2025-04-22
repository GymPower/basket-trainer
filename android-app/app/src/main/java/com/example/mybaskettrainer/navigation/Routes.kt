package com.example.mybaskettrainer.navigation

sealed class Routes(val route: String) {
    object MainScreen : Routes("main_screen")
    object SplashScreen : Routes("splash_screen")
    object LoginScreen : Routes("login_screen")

}