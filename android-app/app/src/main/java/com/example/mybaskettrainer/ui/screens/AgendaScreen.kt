package com.example.mybaskettrainer.ui.screens

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mybaskettrainer.data.local.EventStorage
import com.example.mybaskettrainer.data.model.Event
import com.example.mybaskettrainer.ui.theme.MyBasketTrainerTheme


@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaScreen(navController: NavHostController) {
    val context = LocalContext.current
    var events by remember { mutableStateOf(EventStorage.getEvents()) }
    var menuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agenda") },
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
                                navController.navigate("main_screen") {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = false }
                                    launchSingleTop = true
                                }
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Equipos") },
                            onClick = {
                                navController.navigate("team_screen") {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = false }
                                    launchSingleTop = true
                                }
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Jugadores") },
                            onClick = {
                                navController.navigate("player_screen") {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = false }
                                    launchSingleTop = true
                                }
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Agenda") },
                            enabled = false,
                            onClick = {}
                        )
                        DropdownMenuItem(
                            text = { Text("Marcador") },
                            onClick = {
                                navController.navigate("scoreboard_screen") {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = false }
                                    launchSingleTop = true
                                }
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Pizarra Táctica") },
                            onClick = {
                                navController.navigate("tactics_board") {
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
                onClick = { navController.navigate("addEditEventScreen") },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Event")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Event")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            if (events.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No events found",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { navController.navigate("addEditEventScreen") }) {
                        Text("Add Event")
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(events) { event ->
                        EventCard(
                            event = event,
                            onClick = { navController.navigate("eventDetailScreen/${event.id}") },
                            onEdit = { navController.navigate("addEditEventScreen/${event.id}") },
                            onDelete = {
                                EventStorage.deleteEvent(event.id)
                                events = EventStorage.getEvents()
                                Toast.makeText(context, "Event deleted", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EventCard(
    event: Event,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
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
                    text = event.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Date: ${event.date}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Time: ${event.time}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit Event")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete Event")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AgendaScreenPreview() {
    val fakeNavController = rememberNavController()
    MyBasketTrainerTheme {
        AgendaScreen(navController = fakeNavController)
    }
}
