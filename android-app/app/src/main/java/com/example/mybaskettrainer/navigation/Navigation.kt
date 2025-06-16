package com.example.mybaskettrainer.navigation

import ScoreboardScreen
import TeamDetailScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mybaskettrainer.ui.screens.AddEditEventScreen
import com.example.mybaskettrainer.ui.screens.AddEditPlayerScreen
import com.example.mybaskettrainer.ui.screens.AddEditTeamScreen
import com.example.mybaskettrainer.ui.screens.AgendaScreen
import com.example.mybaskettrainer.ui.screens.EventDetailScreen
import com.example.mybaskettrainer.ui.screens.LoginScreen
import com.example.mybaskettrainer.ui.screens.MainScreen
import com.example.mybaskettrainer.ui.screens.PlayerDetailScreen
import com.example.mybaskettrainer.ui.screens.PlayersScreen
import com.example.mybaskettrainer.ui.screens.RegisterScreen
import com.example.mybaskettrainer.ui.screens.SplashScreen
import com.example.mybaskettrainer.ui.screens.TacticsBoardScreen
import com.example.mybaskettrainer.ui.screens.TeamsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.SplashScreen.route
    ) {
        // Pantalla inicial (Splash)
        composable(Routes.SplashScreen.route) {
            SplashScreen(navController)
        }

        // Pantalla de login
        composable(Routes.LoginScreen.route) {
            LoginScreen(navController)
        }

        // Pantalla de registro
        composable(Routes.RegisterScreen.route) {
            RegisterScreen(navController)
        }

        // Pantalla principal con parámetro trainerDni
        composable(
            route = Routes.MainScreen.route,
            arguments = listOf(navArgument("trainerDni") { type = NavType.StringType })
        ) { backStackEntry ->
            val trainerDni = backStackEntry.arguments?.getString("trainerDni") ?: ""
            MainScreen(navController = navController, trainerDni = trainerDni)
        }

        // Pantalla de equipos con parámetro trainerDni
        composable(
            route = Routes.TeamScreen.route,
            arguments = listOf(navArgument("trainerDni") { type = NavType.StringType })
        ) { backStackEntry ->
            val trainerDni = backStackEntry.arguments?.getString("trainerDni") ?: ""
            TeamsScreen(navController = navController, trainerDni = trainerDni)
        }

        // Pantalla para añadir/editar equipos con parámetros teamId (opcional) y trainerDni
        composable(
            route = Routes.AddEditTeamScreen.route,
            arguments = listOf(
                navArgument("teamId") { type = NavType.StringType; nullable = true },
                navArgument("trainerDni") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val teamId = backStackEntry.arguments?.getString("teamId")
            val trainerDni = backStackEntry.arguments?.getString("trainerDni") ?: ""
            AddEditTeamScreen(teamId = teamId, trainerDni = trainerDni, navController = navController)
        }

        // Pantalla de detalles de equipo con parámetros teamId y trainerDni
        composable(
            route = Routes.TeamDetailScreen.route,
            arguments = listOf(
                navArgument("teamId") { type = NavType.IntType },
                navArgument("trainerDni") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val teamId = backStackEntry.arguments?.getInt("teamId") ?: 0
            val trainerDni = backStackEntry.arguments?.getString("trainerDni") ?: ""
            TeamDetailScreen(teamId = teamId, trainerDni = trainerDni, navController = navController)
        }

        // Pantalla de jugadores con parámetro trainerDni
        composable(
            route = Routes.PlayerScreen.route,
            arguments = listOf(navArgument("trainerDni") { type = NavType.StringType })
        ) { backStackEntry ->
            val trainerDni = backStackEntry.arguments?.getString("trainerDni") ?: ""
            PlayersScreen(navController = navController, trainerDni = trainerDni)
        }

        // Pantalla para añadir/editar jugadores con parámetros playerId (opcional) y trainerDni
        composable(
            route = Routes.AddEditPlayerScreen.route,
            arguments = listOf(
                navArgument("playerId") { type = NavType.StringType; nullable = true },
                navArgument("trainerDni") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val playerId = backStackEntry.arguments?.getString("playerId")
            val trainerDni = backStackEntry.arguments?.getString("trainerDni") ?: ""
            AddEditPlayerScreen(playerId = playerId, trainerDni = trainerDni, navController = navController)
        }

        // Pantalla de detalles de jugador con parámetro playerId
        composable(
            route = Routes.PlayerDetailScreen.route,
            arguments = listOf(navArgument("playerId") { type = NavType.IntType })
        ) { backStackEntry ->
            val playerId = backStackEntry.arguments?.getInt("playerId") ?: 0
            PlayerDetailScreen(playerId, navController)
        }

        // Pantalla de agenda
        composable(Routes.AgendaScreen.route) {
            AgendaScreen(navController = navController)
        }

        // Pantalla para añadir/editar eventos con parámetro eventId (opcional)
        composable(
            route = Routes.AddEditEventScreen.route,
            arguments = listOf(navArgument("eventId") { type = NavType.StringType; nullable = true })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            AddEditEventScreen(eventId = eventId, navController = navController)
        }

        // Pantalla de detalles de evento con parámetro eventId
        composable(
            route = Routes.EventDetailScreen.route,
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: ""
            EventDetailScreen(eventId = eventId, navController = navController)
        }

        // Pantalla de marcador
        composable(Routes.ScoreboardScreen.route) {
            ScoreboardScreen(navController = navController)
        }

        // Pantalla de pizarra táctica
        composable(Routes.TacticsBoardScreen.route) {
            TacticsBoardScreen(navController = navController)
        }
    }
}
