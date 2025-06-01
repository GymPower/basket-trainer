package com.example.mybaskettrainer.ui.screens

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mybaskettrainer.data.model.Player
import com.example.mybaskettrainer.data.model.Team
import com.example.mybaskettrainer.data.remote.ApiClient
import com.example.mybaskettrainer.ui.theme.MyBasketTrainerTheme
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditPlayerScreen(playerId: String? = null, trainerDni: String = "12345678Z", navController: NavHostController) { // Añadido trainerDni
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

    val parsedPlayerId = playerId?.toIntOrNull()
    val isEditMode = parsedPlayerId != null

    if (isEditMode && parsedPlayerId == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Invalid Player ID",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text("Go Back")
            }
        }
        return
    }

    LaunchedEffect(parsedPlayerId) {
        if (isEditMode) {
            isLoading.value = true
            try {
                val response = ApiClient.playerApi.getPlayersByTrainer(trainerDni) // Usar trainerDni pasado
                val player = response.body()?.find { it.playerId == parsedPlayerId }
                if (response.isSuccessful && player != null) {
                    name.value = player.name
                    firstSurname.value = player.firstSurname
                    secondSurname.value = player.secondSurname ?: ""
                    birthdate.value = player.birthdate?.toString() ?: ""
                    email.value = player.email ?: ""
                    telephone.value = player.telephone ?: ""
                    category.value = player.category ?: ""
                    teamId.value = player.team?.teamId?.toString() ?: ""
                } else {
                    Toast.makeText(context, "Error loading player", Toast.LENGTH_SHORT).show()
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
                title = { Text(if (isEditMode) "Edit Player" else "Add Player") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
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
                Text(text = "Loading")
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
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = firstSurname.value,
                    onValueChange = { firstSurname.value = it },
                    label = { Text("First Surname") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = secondSurname.value,
                    onValueChange = { secondSurname.value = it },
                    label = { Text("Second Surname") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = birthdate.value,
                    onValueChange = { birthdate.value = it },
                    label = { Text("Birthdate (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = telephone.value,
                    onValueChange = { telephone.value = it },
                    label = { Text("Telephone") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = category.value,
                    onValueChange = { category.value = it },
                    label = { Text("Category") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = teamId.value,
                    onValueChange = { teamId.value = it },
                    label = { Text("Team ID (optional)") },
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
                        scope.launch {
                            isLoading.value = true
                            saveOrUpdatePlayer(
                                context = context,
                                isEditMode = isEditMode,
                                playerId = parsedPlayerId,
                                name = name.value,
                                firstSurname = firstSurname.value,
                                secondSurname = secondSurname.value.ifEmpty { null },
                                birthdate = birthdate.value.takeIf { it.isNotEmpty() }?.let { LocalDate.parse(it) },
                                email = email.value.ifEmpty { null },
                                telephone = telephone.value.ifEmpty { null },
                                category = category.value.ifEmpty { null },
                                teamId = teamId.value.toIntOrNull(),
                                trainerDni = trainerDni,
                                navController = navController
                            )
                            isLoading.value = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading.value
                ) {
                    Text(if (isEditMode) "Save" else "Create")
                }
            }
        }
    }
}

suspend fun saveOrUpdatePlayer(
    context: Context,
    isEditMode: Boolean,
    playerId: Int?,
    name: String,
    firstSurname: String,
    secondSurname: String?,
    birthdate: LocalDate?,
    email: String?,
    telephone: String?,
    category: String?,
    teamId: Int?,
    trainerDni: String,
    navController: NavHostController
) {
    try {
        val team = teamId?.let {
            Team(
                teamId = it,
                name = "",
                category = "",
                league = null,
                trainerDni = null,
                playerCount = 0,
                isFavorite = false
            )
        }
        val player = Player(
            playerId = playerId ?: 0,
            name = name,
            firstSurname = firstSurname,
            secondSurname = secondSurname,
            birthdate = birthdate.toString(),
            email = email,
            telephone = telephone,
            category = category,
            trainerDni = trainerDni,
            team = team
        )
        val response = if (isEditMode) {
            ApiClient.playerApi.updatePlayer(playerId!!, player)
        } else {
            ApiClient.playerApi.createPlayer(trainerDni, player)
        }
        if (response.isSuccessful) {
            Toast.makeText(
                context,
                if (isEditMode) "Player updated" else "Player created",
                Toast.LENGTH_SHORT
            ).show()
            navController.popBackStack()
        } else {
            Toast.makeText(
                context,
                if (isEditMode) "Error updating player" else "Error creating player",
                Toast.LENGTH_SHORT
            ).show()
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Connection error: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AddEditPlayerScreenPreview() {
    val fakeNavController = rememberNavController()
    MyBasketTrainerTheme {
        AddEditPlayerScreen(navController = fakeNavController, trainerDni = "12345678Z")
    }
}