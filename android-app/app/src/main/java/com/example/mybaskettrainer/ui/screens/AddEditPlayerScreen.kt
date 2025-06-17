package com.example.mybaskettrainer.ui.screens

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditPlayerScreen(playerId: String? = null, trainerDni: String, navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val name = remember { mutableStateOf("") }
    val firstSurname = remember { mutableStateOf("") }
    val secondSurname = remember { mutableStateOf("") }
    val birthdate = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val telephone = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("") }
    val teamId = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }

    val parsedPlayerId = playerId?.toLongOrNull()
    val isEditMode = parsedPlayerId != null

    if (isEditMode && parsedPlayerId == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.invalid_player_id),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text(stringResource(R.string.go_back))
            }
        }
        return
    }

    LaunchedEffect(parsedPlayerId) {
        if (isEditMode) {
            isLoading.value = true
            try {
                val response = ApiClient.playerApi.getPlayersByTrainer(trainerDni)
                val player = response.body()?.find { it.playerId == parsedPlayerId }
                if (response.isSuccessful && player != null) {
                    name.value = player.name
                    firstSurname.value = player.surname1
                    secondSurname.value = player.surname2 ?: ""
                    birthdate.value = player.birthdate ?: ""
                    email.value = player.email ?: ""
                    telephone.value = player.telephone ?: ""
                    category.value = player.category ?: ""
                    teamId.value = player.teamId?.toString() ?: ""
                } else {
                    Toast.makeText(context, "Error loading player: ${response.code()} - ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Connection error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
            isLoading.value = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (isEditMode) stringResource(R.string.edit_player)
                        else stringResource(R.string.add_player)
                    )
                },
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
                Text(stringResource(R.string.loading))
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text(stringResource(R.string.name)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = firstSurname.value,
                    onValueChange = { firstSurname.value = it },
                    label = { Text(stringResource(R.string.first_surname)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = secondSurname.value,
                    onValueChange = { secondSurname.value = it },
                    label = { Text(stringResource(R.string.second_surname)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = birthdate.value,
                    onValueChange = { birthdate.value = it },
                    label = { Text(stringResource(R.string.birthdate)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text(stringResource(R.string.email)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = telephone.value,
                    onValueChange = { telephone.value = it },
                    label = { Text(stringResource(R.string.phone)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = category.value,
                    onValueChange = { category.value = it },
                    label = { Text(stringResource(R.string.category)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = teamId.value,
                    onValueChange = { teamId.value = it },
                    label = { Text(stringResource(R.string.team_optional)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (name.value.isBlank() || firstSurname.value.isBlank()) {
                            Toast.makeText(context, "Name and First Surname are required", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        val teamIdLong = teamId.value.toLongOrNull()
                        scope.launch {
                            isLoading.value = true
                            saveOrUpdatePlayer(
                                context = context,
                                isEditMode = isEditMode,
                                playerId = parsedPlayerId,
                                name = name.value,
                                firstSurname = firstSurname.value,
                                secondSurname = secondSurname.value.ifEmpty { null },
                                birthdate = birthdate.value.ifEmpty { null },
                                email = email.value.ifEmpty { null },
                                telephone = telephone.value.ifEmpty { null },
                                category = category.value.ifEmpty { null },
                                teamId = teamIdLong,
                                trainerDni = trainerDni,
                                navController = navController
                            )
                            isLoading.value = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading.value
                ) {
                    Text(
                        if (isEditMode) stringResource(R.string.save)
                        else stringResource(R.string.create)
                    )
                }
            }
        }
    }
}

// Función para guardar o actualizar un jugador
suspend fun saveOrUpdatePlayer(
    context: Context,
    isEditMode: Boolean,
    playerId: Long?,
    name: String,
    firstSurname: String,
    secondSurname: String?,
    birthdate: String?,
    email: String?,
    telephone: String?,
    category: String?,
    teamId: Long?,
    trainerDni: String,
    navController: NavHostController
) {
    try {
        val team = teamId?.let {
            Team(
                teamId = it.toInt(),
                name = "",
                category = "",
                league = null,
                trainerDni = null,
                playerCount = 0,
                isFavorite = false
            )
        }
        // Mapear Player a PlayerDTO
        val playerRequest = PlayerRequest(
            playerId = playerId,
            name = name,
            surname1 = firstSurname,
            surname2 = secondSurname,
            birthdate = birthdate,
            email = email,
            telephone = telephone,
            category = category,
            teamId = teamId,
            trainerDni = trainerDni
        )
        println("Sending playerRequest: $playerRequest, teamId: $teamId, trainerDni: $trainerDni") // Depuración
        val response = if (isEditMode) {
            ApiClient.playerApi.updatePlayer(playerId!!, playerRequest)
        } else {
            // Si teamId es null, notificar al usuario
            val effectiveTeamId = teamId ?: run {
                Toast.makeText(context, "Team ID is required to create a player", Toast.LENGTH_SHORT).show()
                return
            }
            ApiClient.playerApi.createPlayer(effectiveTeamId, trainerDni, playerRequest)
        }
        if (response.isSuccessful) {
            Toast.makeText(
                context,
                if (isEditMode) "Player updated" else "Player created",
                Toast.LENGTH_SHORT
            ).show()
            navController.popBackStack()
        } else {
            val errorBody = response.errorBody()?.string() ?: "No error details"
            Toast.makeText(
                context,
                "Error ${response.code()}: ${response.message()} - $errorBody",
                Toast.LENGTH_LONG
            ).show()
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Connection error: ${e.message}", Toast.LENGTH_LONG).show()
        e.printStackTrace()
    }
}
