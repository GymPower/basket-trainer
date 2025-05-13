package com.example.mybaskettrainer.ui.screens


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.rememberNavController
import com.example.mybaskettrainer.R
import com.example.mybaskettrainer.data.model.Team
import com.example.mybaskettrainer.data.remote.ApiClient
import com.example.mybaskettrainer.ui.theme.MyBasketTrainerTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamsScreen(navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val teamsState = remember { mutableStateOf<List<Team>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }

    // Cargar equipos al iniciar
    LaunchedEffect(Unit) {
        isLoading.value = true
        try {
            val response = ApiClient.teamApi.getTeamsByTrainer("dni")//TODO, añadir el val del dni
            if (response.isSuccessful) {
                teamsState.value = response.body()?.sortedByDescending { it.isFavorite } ?: emptyList()
            } else {
                Toast.makeText(context, R.string.error_loading_teams, Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "${R.string.connection_error}: ${e.message}", Toast.LENGTH_SHORT).show()
        }
        isLoading.value = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.my_teams)) },
                navigationIcon = {
                    IconButton(onClick = { /* Abrir menú hamburguesa - Implementar si es necesario */ }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            Button(
                onClick = { navController.navigate("addEditTeamScreen") },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Team")
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.add_team))
            }
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
        } else if (teamsState.value.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.no_teams),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { navController.navigate("addEditTeamScreen") }) {
                    Text(stringResource(R.string.add_team))
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(teamsState.value) { team ->
                    TeamCard(
                        team = team,
                        onClick = { navController.navigate("teamDetailScreen/${team.teamId}") },
                        onFavoriteToggle = {
                            val updatedTeam = team.copy(isFavorite = !team.isFavorite)
                            teamsState.value = teamsState.value.map {
                                if (it.teamId == team.teamId) updatedTeam else it
                            }.sortedByDescending { it.isFavorite }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TeamCard(team: Team, onClick: () -> Unit, onFavoriteToggle: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen placeholder (puedes reemplazar con una imagen real desde el backend)
            Image(
                painter = painterResource(R.drawable.icono_equipo),
                contentDescription = "Team Image",
                modifier = Modifier.width(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = team.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${stringResource(R.string.category)}: ${team.category}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = onFavoriteToggle) {
                Icon(
                    imageVector = if (team.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (team.isFavorite) "Remove Favorite" else "Add Favorite"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TeamsScreenPreview() {
    val fakeNavController = rememberNavController()
    MyBasketTrainerTheme {
        TeamsScreen(navController = fakeNavController)
    }
}