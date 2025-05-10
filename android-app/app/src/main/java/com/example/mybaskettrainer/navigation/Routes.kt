package com.example.mybaskettrainer.navigation

sealed class Routes(val route: String) {
    object MainScreen : Routes("main_screen")
    object SplashScreen : Routes("splash_screen")
    object LoginScreen : Routes("login_screen")
    object RegisterScreen : Routes("register_screen")
    object TeamScreen : Routes("team_screen")
    object AddEditTeamScreen : Routes("add_edit_team_screen")
    object TeamDetailScreen : Routes("team_detail_screen")

}