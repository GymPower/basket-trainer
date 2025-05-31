package com.example.mybaskettrainer.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mybaskettrainer.ui.screens.AddEditEventScreen
import com.example.mybaskettrainer.ui.screens.AddEditPlayerScreen
import com.example.mybaskettrainer.ui.screens.AddEditTeamScreen
import com.example.mybaskettrainer.ui.screens.AgendaScreen
import com.example.mybaskettrainer.ui.screens.EventDetailScreen
import com.example.mybaskettrainer.ui.screens.LoginScreen
import com.example.mybaskettrainer.ui.screens.MainScreen
import com.example.mybaskettrainer.ui.screens.PlayerDetailScreen
import com.example.mybaskettrainer.ui.screens.RegisterScreen
import com.example.mybaskettrainer.ui.screens.SplashScreen
import com.example.mybaskettrainer.ui.screens.TeamDetailScreen
import com.example.mybaskettrainer.ui.screens.TeamsScreen
import com.example.mybaskettrainer.ui.screens.PlayersScreen
import com.example.mybaskettrainer.ui.screens.ScoreboardScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.SplashScreen.route
    ) {
        composable(Routes.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(
            route = "${Routes.MainScreen.route}/{trainerDni}",
            arguments = listOf(navArgument("trainerDni") { type = NavType.StringType })
        ) { backStackEntry ->
            val trainerDni = backStackEntry.arguments?.getString("trainerDni") ?: ""
            MainScreen(navController = navController, trainerDni = trainerDni)
        }
        composable(Routes.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(Routes.RegisterScreen.route) {
            RegisterScreen(navController)
        }
        composable(
            route = "${Routes.TeamScreen.route}/{trainerDni}",
            arguments = listOf(navArgument("trainerDni") { type = NavType.StringType })
        ) { backStackEntry ->
            val trainerDni = backStackEntry.arguments?.getString("trainerDni") ?: ""
            TeamsScreen(navController = navController, trainerDni = trainerDni)
        }
        composable(
            route = "${Routes.TeamDetailScreen.route}/{teamId}/{trainerDni}",
            arguments = listOf(
                navArgument("teamId") { type = NavType.StringType },
                navArgument("trainerDni") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val teamId = backStackEntry.arguments?.getString("teamId")?.toInt() ?: 0
            val trainerDni = backStackEntry.arguments?.getString("trainerDni") ?: "12345678Z"
            TeamDetailScreen(teamId = teamId, trainerDni = trainerDni, navController = navController)
        }
        composable(Routes.AddEditTeamScreen.route) {
            AddEditTeamScreen(navController = navController, trainerDni = "12345678Z")
        }
        composable(
            route = "${Routes.AddEditTeamScreen.route}/{teamId}/{trainerDni}",
            arguments = listOf(
                navArgument("teamId") { type = NavType.StringType },
                navArgument("trainerDni") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val teamId = backStackEntry.arguments?.getString("teamId")
            val trainerDni = backStackEntry.arguments?.getString("trainerDni") ?: "12345678Z"
            AddEditTeamScreen(teamId = teamId, trainerDni = trainerDni, navController = navController)
        }
        composable(
            route = "${Routes.PlayerScreen.route}/{trainerDni}",
            arguments = listOf(navArgument("trainerDni") { type = NavType.StringType })
        ) { backStackEntry ->
            val trainerDni = backStackEntry.arguments?.getString("trainerDni") ?: ""
            PlayersScreen(navController = navController, trainerDni = trainerDni)
        }
        composable("${Routes.PlayerDetailScreen.route}/{playerId}") {
                backStackEntry ->
            val playerId = backStackEntry.arguments?.getString("playerId")?.toInt() ?: 0
            PlayerDetailScreen(playerId, navController)
        }
        composable(Routes.AddEditPlayerScreen.route) {
            AddEditPlayerScreen(navController = navController, trainerDni = "12345678Z")
        }
        composable(
            route = "${Routes.AddEditPlayerScreen.route}/{playerId}/{trainerDni}",
            arguments = listOf(
                navArgument("playerId") { type = NavType.StringType },
                navArgument("trainerDni") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val playerId = backStackEntry.arguments?.getString("playerId")
            val trainerDni = backStackEntry.arguments?.getString("trainerDni") ?: "12345678Z"
            AddEditPlayerScreen(playerId = playerId, trainerDni = trainerDni, navController = navController)
        }
        composable(Routes.AgendaScreen.route) {
            AgendaScreen(navController = navController)
        }
        composable(Routes.AddEditEventScreen.route) {
            AddEditEventScreen(navController = navController)
        }
        composable("${Routes.AddEditEventScreen.route}/{eventId}") { backStackEntry ->
            AddEditEventScreen(
                eventId = backStackEntry.arguments?.getString("eventId"),
                navController = navController
            )
        }
        composable("${Routes.EventDetailScreen.route}/{eventId}") { backStackEntry ->
            EventDetailScreen(
                eventId = backStackEntry.arguments?.getString("eventId") ?: "",
                navController = navController
            )
        }
        composable(Routes.ScoreboardScreen.route) {
            ScoreboardScreen(navController = navController)
        }
    }
}