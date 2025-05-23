package com.example.mybaskettrainer.ui.screens
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Schedule
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mybaskettrainer.data.local.EventStorage
import com.example.mybaskettrainer.data.model.Event

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(eventId: String, navController: NavHostController) {
    val context = LocalContext.current
    val event = remember { mutableStateOf<Event?>(null) }

    val parsedEventId = eventId.toIntOrNull()

    if (parsedEventId == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Invalid Event ID",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.padding(16.dp))
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        }
        return
    }

    event.value = EventStorage.getEvents().find { it.id == parsedEventId }
    if (event.value == null) {
        Toast.makeText(context, "Event not found", Toast.LENGTH_SHORT).show()
        navController.popBackStack()
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Event Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Name: ${event.value!!.name}", style = MaterialTheme.typography.titleLarge)
            Text(text = "Description: ${event.value!!.description}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Address: ${event.value!!.address}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Date: ${event.value!!.date}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Time: ${event.value!!.time}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}