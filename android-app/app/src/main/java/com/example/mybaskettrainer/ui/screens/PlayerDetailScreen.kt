package com.example.mybaskettrainer.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mybaskettrainer.R
import com.example.mybaskettrainer.data.model.Player
import com.example.mybaskettrainer.data.model.Team
import com.example.mybaskettrainer.data.remote.ApiClient
import com.example.mybaskettrainer.data.remote.dto.PlayerRequest
import com.example.mybaskettrainer.navigation.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerDetailScreen(playerId: Int, navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val playerState = remember { mutableStateOf<Player?>(null) }
    val isLoading = remember { mutableStateOf(true) }

    // Cargar detalles del jugador
    LaunchedEffect(playerId) {
        isLoading.value = true
        try {
            val response = ApiClient.playerApi.getPlayersByTrainer("12345678Z") // TODO: Pasar el trainerDni correcto
            if (response.isSuccessful) {
                val playerRequest = response.body()?.find { it.playerId?.toInt() == playerId }
                playerState.value = playerRequest?.let { request ->
                    Player(
                        playerId = request.playerId?.toInt() ?: 0,
                        name = request.name,
                        firstSurname = request.surname1,
                        secondSurname = request.surname2,
                        birthdate = request.birthdate,
                        email = request.email,
                        telephone = request.telephone,
                        category = request.category,
                        trainerDni = request.trainerDni,
                        team = request.teamId?.let { teamId ->
                            Team(
                                teamId = teamId.toInt(),
                                name = "", // Puedes obtener el nombre del equipo desde TeamApi si es necesario
                                category = "",
                                league = null,
                                trainerDni = null,
                                playerCount = 0,
                                isFavorite = false
                            )
                        }
                    )
                }
            } else {
                Toast.makeText(context, R.string.error_loading_player, Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "${R.string.connection_error}: ${e.message}", Toast.LENGTH_SHORT).show()
        }
        isLoading.value = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.player_details)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (isLoading.value) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(R.string.loading))
            }
        } else if (playerState.value == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.player_not_found),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            val player = playerState.value!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "${player.name} ${player.firstSurname} ${player.secondSurname ?: ""}",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "${stringResource(R.string.birthdate)}: ${player.birthdate ?: "N/A"}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "${stringResource(R.string.email)}: ${player.email ?: "N/A"}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "${stringResource(R.string.phone)}: ${player.telephone ?: "N/A"}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "${stringResource(R.string.category)}: ${player.category ?: "N/A"}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "${stringResource(R.string.team_name)}: ${player.team?.name ?: "N/A"}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { navController.navigate(Routes.AddEditPlayerScreen.createRoute(player.playerId.toString(), "12345678Z")) }, // TODO: Pasar el trainerDni correcto
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(stringResource(R.string.edit))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = {
                            scope.launch {
                                try {
                                    val response = ApiClient.playerApi.deletePlayer(player.playerId.toLong())
                                    if (response.isSuccessful) {
                                        Toast.makeText(context, R.string.player_deleted, Toast.LENGTH_SHORT).show()
                                        navController.popBackStack()
                                    } else {
                                        Toast.makeText(context, R.string.error_deleting_player, Toast.LENGTH_SHORT).show()
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(context, "${R.string.connection_error}: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(stringResource(R.string.delete))
                    }
                }
            }
        }
    }
}