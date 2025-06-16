package com.example.mybaskettrainer.navigation

sealed class Routes(val route: String) {
    object SplashScreen : Routes("splash_screen")

    object LoginScreen : Routes("login_screen")

    object RegisterScreen : Routes("register_screen")

    object AgendaScreen : Routes("agenda_screen")

    object AddEditEventScreen : Routes("add_edit_event_screen/{eventId}?") {
        fun createRoute(eventId: String? = null) = "add_edit_event_screen/$eventId"
    }

    object EventDetailScreen : Routes("event_detail_screen/{eventId}") {
        fun createRoute(eventId: String) = "event_detail_screen/$eventId"
    }

    object ScoreboardScreen : Routes("scoreboard_screen")

    object TacticsBoardScreen : Routes("tactics_board")

    object MainScreen : Routes("main_screen/{trainerDni}") {
        fun createRoute(trainerDni: String) = "main_screen/$trainerDni"
    }

    object TeamScreen : Routes("team_screen/{trainerDni}") {
        fun createRoute(trainerDni: String) = "team_screen/$trainerDni"
    }

    object AddEditTeamScreen : Routes("add_edit_team_screen/{teamId}?/{trainerDni}") {
        fun createRoute(teamId: String? = null, trainerDni: String) = "add_edit_team_screen/$teamId/$trainerDni"
    }

    object TeamDetailScreen : Routes("team_detail_screen/{teamId}/{trainerDni}") {
        fun createRoute(teamId: Int, trainerDni: String) = "team_detail_screen/$teamId/$trainerDni"
    }

    object PlayerScreen : Routes("player_screen/{trainerDni}") {
        fun createRoute(trainerDni: String) = "player_screen/$trainerDni"
    }

    object AddEditPlayerScreen : Routes("add_edit_player_screen/{playerId}?/{trainerDni}") {
        fun createRoute(playerId: String? = null, trainerDni: String) = "add_edit_player_screen/$playerId/$trainerDni"
    }

    object PlayerDetailScreen : Routes("player_detail_screen/{playerId}") {
        fun createRoute(playerId: Int) = "player_detail_screen/$playerId"
    }
}