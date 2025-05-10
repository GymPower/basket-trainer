package com.example.mybaskettrainer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mybaskettrainer.ui.screens.AddEditTeamScreen
import com.example.mybaskettrainer.ui.screens.LoginScreen
import com.example.mybaskettrainer.ui.screens.MainScreen
import com.example.mybaskettrainer.ui.screens.RegisterScreen
import com.example.mybaskettrainer.ui.screens.SplashScreen
import com.example.mybaskettrainer.ui.screens.TeamDetailScreen
import com.example.mybaskettrainer.ui.screens.TeamsScreen

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
            MainScreen(navController)
        }
        composable(Routes.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(Routes.RegisterScreen.route) {
            RegisterScreen(navController)
        }
        composable(Routes.TeamScreen.route) {
            TeamsScreen(navController)
        }
        composable("${Routes.TeamDetailScreen.route}/{teamId}") {
            backStackEntry ->
            val teamId = backStackEntry.arguments?.getString("teamId")?.toInt() ?: 0
            TeamDetailScreen(teamId, navController)
        }
        composable(Routes.AddEditTeamScreen.route) {
            AddEditTeamScreen(navController = navController)
        }
        composable("${Routes.AddEditTeamScreen.route}/{teamId}") {
            backStackEntry ->
            val teamId = backStackEntry.arguments?.getString("teamId")
            AddEditTeamScreen(teamId, navController)
        }


    }
}
