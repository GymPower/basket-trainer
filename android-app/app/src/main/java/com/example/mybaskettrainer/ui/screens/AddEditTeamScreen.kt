package com.example.mybaskettrainer.ui.screens

import android.content.Context
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mybaskettrainer.R
import com.example.mybaskettrainer.data.model.Team
import com.example.mybaskettrainer.data.remote.ApiClient
import com.example.mybaskettrainer.navigation.Routes
import com.example.mybaskettrainer.ui.theme.MyBasketTrainerTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTeamScreen(teamId: String? = null, trainerDni: String, navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val name = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("") }
    val league = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }
    val parsedTeamId = teamId?.toIntOrNull()
    val isEditMode = parsedTeamId != null

    // Validar el ID del equipo en modo edición
    if (isEditMode && parsedTeamId == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.invalid_team_id),
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

    // Cargar datos del equipo si es modo edición
    LaunchedEffect(parsedTeamId) {
        if (isEditMode) {
            isLoading.value = true
            try {
                val response = ApiClient.teamApi.getTeamsByTrainer(trainerDni)
                val team = response.body()?.find { it.teamId == parsedTeamId }
                if (response.isSuccessful && team != null) {
                    name.value = team.name
                    category.value = team.category
                    league.value = team.league ?: ""
                } else {
                    Toast.makeText(context, "Error loading team", Toast.LENGTH_SHORT).show()
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
                        if (isEditMode) stringResource(R.string.edit_team)
                        else stringResource(R.string.add_team)
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
                Text(text = stringResource(R.string.loading))
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
                    label = { Text(stringResource(R.string.team_name)) },
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
                    value = league.value,
                    onValueChange = { league.value = it },
                    label = { Text(stringResource(R.string.league)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (name.value.isBlank() || category.value.isBlank()) {
                            Toast.makeText(context, "Required fields", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        scope.launch {
                            isLoading.value = true
                            saveOrUpdateTeam(
                                context = context,
                                isEditMode = isEditMode,
                                teamId = parsedTeamId,
                                name = name.value,
                                category = category.value,
                                league = league.value,
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

// Función para guardar o actualizar un equipo
suspend fun saveOrUpdateTeam(
    context: Context,
    isEditMode: Boolean,
    teamId: Int?,
    name: String,
    category: String,
    league: String?,
    trainerDni: String,
    navController: NavHostController
) {
    try {
        val team = Team(
            teamId = teamId ?: 0,
            name = name,
            category = category,
            league = league,
            trainerDni = trainerDni
        )
        val response = if (isEditMode) {
            ApiClient.teamApi.updateTeam(teamId!!, team)
        } else {
            ApiClient.teamApi.createTeam(trainerDni, team)
        }
        if (response.isSuccessful) {
            Toast.makeText(
                context,
                if (isEditMode) "Team updated" else "Team created",
                Toast.LENGTH_SHORT
            ).show()
            navController.popBackStack()
        } else {
            Toast.makeText(
                context,
                if (isEditMode) "Error updating team" else "Error creating team",
                Toast.LENGTH_SHORT
            ).show()
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Connection error: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}
@Preview(showBackground = true)
@Composable
fun AddEditTeamsScreenPreview() {
    val fakeNavController = rememberNavController()
    MyBasketTrainerTheme {
        AddEditTeamScreen(teamId = "1", trainerDni = "DniPreview", navController = fakeNavController)
    }
}