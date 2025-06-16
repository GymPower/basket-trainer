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
import com.example.mybaskettrainer.data.local.EventStorage
import com.example.mybaskettrainer.data.model.Event
import com.example.mybaskettrainer.navigation.Routes
import com.example.mybaskettrainer.ui.theme.MyBasketTrainerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(eventId: String, navController: NavHostController) {
    val context = LocalContext.current
    val event = remember { mutableStateOf<Event?>(null) }

    val parsedEventId = eventId.toIntOrNull()

    // Validar el ID del evento
    if (parsedEventId == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.error_event_id),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.padding(16.dp))
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        }
        return
    }

    // Cargar el evento
    event.value = EventStorage.getEvents().find { it.id == parsedEventId }
    if (event.value == null) {
        Toast.makeText(context, stringResource(R.string.event_not_found), Toast.LENGTH_SHORT).show()
        navController.popBackStack()
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.event_details)) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "${stringResource(R.string.name)}: ${event.value!!.name}",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "${stringResource(R.string.description)}: ${event.value!!.description}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "${stringResource(R.string.adress)}: ${event.value!!.address}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "${stringResource(R.string.date_style)}: ${event.value!!.date}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "${stringResource(R.string.hour_style)}: ${event.value!!.time}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
