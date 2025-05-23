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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mybaskettrainer.data.local.EventStorage
import com.example.mybaskettrainer.data.model.Event


@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaScreen(navController: NavHostController) {
    val context = LocalContext.current
    var events by remember { mutableStateOf(EventStorage.getEvents().sortedByDate()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agenda") },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
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
                                events = EventStorage.getEvents().sortedByDate()
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

// Función para ordenar eventos por fecha (formato "dd/MM/yyyy")
fun List<event>.sortedByDate(): List<event> {
    return sortedWith(compareBy { event ->
        val parts = event.date.split("/")
// Convertir a un formato comparable (yyyyMMdd)
        "${parts[2]}${parts[1].padStart(2, '0')}${parts[0].padStart(2, '0')}".toInt()
    })
}</event></event>