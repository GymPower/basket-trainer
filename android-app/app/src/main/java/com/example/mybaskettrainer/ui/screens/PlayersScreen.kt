package com.example.mybaskettrainer.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mybaskettrainer.R
import com.example.mybaskettrainer.data.model.Player
import com.example.mybaskettrainer.data.remote.ApiClient
import com.example.mybaskettrainer.ui.theme.MyBasketTrainerTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayersScreen(navController: NavHostController, trainerDni: String) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val playersState = remember { mutableStateOf<List<Player>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf("") }
    var categoryFilter by remember { mutableStateOf("") }
    var teamFilter by remember { mutableStateOf("") }
    var menuExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (trainerDni.isEmpty()) {
            Toast.makeText(context, "Error: Trainer DNI not provided", Toast.LENGTH_LONG).show()
            navController.popBackStack()
            return@LaunchedEffect
        }
        isLoading.value = true
        try {
            val response = ApiClient.playerApi.getPlayersByTrainer(trainerDni)
            if (response.isSuccessful) {
                playersState.value = response.body()?.sortedBy { it.firstSurname } ?: emptyList()
            } else {
                Toast.makeText(context, R.string.error_loading_players, Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "${R.string.connection_error}: ${e.message}", Toast.LENGTH_SHORT).show()
        }
        isLoading.value = false
    }

    val filteredPlayers = playersState.value.filter { player ->
        val matchesName = player.name.contains(searchQuery, ignoreCase = true) ||
                player.firstSurname.contains(searchQuery, ignoreCase = true)
        val matchesCategory = if (categoryFilter.isEmpty()) true else player.category?.contains(categoryFilter, ignoreCase = true) == true
        val matchesTeam = if (teamFilter.isEmpty()) true else player.team?.name?.contains(teamFilter, ignoreCase = true) ?: false
        matchesName && matchesCategory && matchesTeam
    }.sortedBy { it.firstSurname }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.my_players)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menú")
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Pantalla Principal") },
                            onClick = {
                                navController.navigate("mainScreen/$trainerDni") {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = false }
                                    launchSingleTop = true
                                }
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Equipos") },
                            onClick = {
                                navController.navigate("teamScreen/$trainerDni") {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = false }
                                    launchSingleTop = true
                                }
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Jugadores") },
                            enabled = false,
                            onClick = {}
                        )
                        DropdownMenuItem(
                            text = { Text("Agenda") },
                            onClick = {
                                navController.navigate("agendaScreen") {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = false }
                                    launchSingleTop = true
                                }
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Marcador") },
                            onClick = {
                                navController.navigate("scoreboardScreen") {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = false }
                                    launchSingleTop = true
                                }
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Pizarra Táctica") },
                            onClick = {
                                navController.navigate("tacticsBoard") {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = false }
                                    launchSingleTop = true
                                }
                                menuExpanded = false
                            }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            Button(
                onClick = { navController.navigate("addEditPlayerScreen") },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Player")
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.add_player))
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
// Filtros de búsqueda
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text(stringResource(R.string.search_by_name)) },
                    modifier = Modifier.weight(1f),
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                    singleLine = true
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = categoryFilter,
                    onValueChange = { categoryFilter = it },
                    label = { Text(stringResource(R.string.filter_by_category)) },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = teamFilter,
                    onValueChange = { teamFilter = it },
                    label = { Text(stringResource(R.string.filter_by_team)) },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

// Lista de jugadores
            if (isLoading.value) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(R.string.loading))
                }
            } else if (filteredPlayers.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.no_players),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { navController.navigate("addEditPlayerScreen") }) {
                        Text(stringResource(R.string.add_player))
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredPlayers) { player ->
                        PlayerCard(
                            player = player,
                            onClick = { navController.navigate("playerDetailScreen/${player.playerId}") }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerCard(player: Player, onClick: () -> Unit) {
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
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${player.name} ${player.firstSurname}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${stringResource(R.string.category)}: ${player.category}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${stringResource(R.string.team_name)}: ${player.team?.name ?: "N/A"}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerScreenPreview() {
    val fakeNavController = rememberNavController()
    MyBasketTrainerTheme {
        PlayersScreen(navController = fakeNavController, trainerDni = "12345678A")
    }
}