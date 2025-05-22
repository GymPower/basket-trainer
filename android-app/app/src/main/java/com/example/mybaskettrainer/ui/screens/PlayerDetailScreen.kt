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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mybaskettrainer.R
import com.example.mybaskettrainer.data.model.Player
import com.example.mybaskettrainer.data.remote.ApiClient
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerDetailScreen(playerId: Int, navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val playerState = remember { mutableStateOf<Player?>(null) }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(playerId) {
        isLoading.value = true
        try {
            val response = ApiClient.playerApi.getPlayers()
            if (response.isSuccessful) {
                playerState.value = response.body()?.find { it.playerId == playerId }
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
                    text = "${player.name} ${player.firstSurname} ${player.secondSurname} ",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "${stringResource(R.string.birthdate)}: ${player.birthdate}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "${stringResource(R.string.email)}: ${player.email}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "${stringResource(R.string.phone)}: ${player.telephone}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "${stringResource(R.string.category)}: ${player.category}",
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
                        onClick = { navController.navigate("addEditPlayerScreen/${player.playerId}") },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(stringResource(R.string.edit))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = {
                            scope.launch {
                                try {
                                    val response = ApiClient.playerApi.deletePlayer(player.playerId)
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