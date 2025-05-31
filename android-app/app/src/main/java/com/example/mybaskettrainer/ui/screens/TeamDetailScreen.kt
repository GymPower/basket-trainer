package com.example.mybaskettrainer.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.mybaskettrainer.ui.theme.MyBasketTrainerTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamDetailScreen(teamId: Int, trainerDni: String = "12345678Z", navController: NavHostController) { // Añadido trainerDni
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val teamState = remember { mutableStateOf<Team?>(null) }
    val isLoading = remember { mutableStateOf(true) }

// Cargar detalles del equipo
    LaunchedEffect(teamId) {
        isLoading.value = true
        try {
            val response = ApiClient.teamApi.getTeamsByTrainer(trainerDni) // Usar getTeamsByTrainer
            if (response.isSuccessful) {
                teamState.value = response.body()?.find { it.teamId == teamId }
            } else {
                Toast.makeText(context, R.string.error_loading_team, Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "${R.string.connection_error}: ${e.message}", Toast.LENGTH_SHORT).show()
        }
        isLoading.value = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.team_details)) },
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
                Text(text = stringResource(R.string.loading))
            }
        } else if (teamState.value == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.team_not_found),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            val team = teamState.value!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = team.name,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "${stringResource(R.string.category)}: ${team.category}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "${stringResource(R.string.league)}: ${team.league ?: "N/A"}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "${stringResource(R.string.player_count)}: ${team.playerCount}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { navController.navigate("addEditTeamScreen/${team.teamId}/$trainerDni") }, // Pasar trainerDni
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(stringResource(R.string.edit))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = {
                            scope.launch {
                                try {
                                    val response = ApiClient.teamApi.deleteTeam(team.teamId.toLong())
                                    if (response.isSuccessful) {
                                        Toast.makeText(context, R.string.team_deleted, Toast.LENGTH_SHORT).show()
                                        navController.popBackStack()
                                    } else {
                                        Toast.makeText(context, R.string.error_deleting_team, Toast.LENGTH_SHORT).show()
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

@Preview(showBackground = true)
@Composable
fun TeamDetailScreenPreview() {
    val fakeNavController = rememberNavController()
    MyBasketTrainerTheme {
        TeamDetailScreen(teamId = 1, trainerDni = "12345678Z", navController = fakeNavController)
    }
}