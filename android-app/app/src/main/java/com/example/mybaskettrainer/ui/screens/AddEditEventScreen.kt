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
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditEventScreen(eventId: String? = null, navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val name = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val time = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }

    val parsedEventId = eventId?.toIntOrNull()
    val isEditMode = parsedEventId != null

    if (isEditMode && parsedEventId == null) {
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
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text("Go Back")
            }
        }
        return
    }

// Cargar datos si es modo edición
    if (isEditMode) {
        val event = EventStorage.getEvents().find { it.id == parsedEventId }
        if (event != null) {
            name.value = event.name
            description.value = event.description
            address.value = event.address
            date.value = event.date
            time.value = event.time
        } else {
            Toast.makeText(context, "Event not found", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
            return
        }
    }

// Configuración para DatePicker y TimePicker
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            date.value = "${selectedDay.toString().padStart(2, '0')}/${
                (selectedMonth + 1).toString().padStart(2, '0')
            }/$selectedYear"
        },
        year,
        month,
        day
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            time.value = "${selectedHour.toString().padStart(2, '0')}:${
                selectedMinute.toString().padStart(2, '0')
            }"
        },
        hour,
        minute,
        true
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Edit Event" else "Add Event") },
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
                    value = description.value,
                    onValueChange = { description.value = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = address.value,
                    onValueChange = { address.value = it },
                    label = { Text("Address") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = date.value,
                        onValueChange = { },
                        label = { Text("Date (dd/MM/yyyy)") },
                        modifier = Modifier.weight(1f),
                        readOnly = true,
                        enabled = false
                    )
                    IconButton(onClick = { datePickerDialog.show() }) {
                        Icon(Icons.Filled.CalendarToday, contentDescription = "Pick Date")
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = time.value,
                        onValueChange = { },
                        label = { Text("Time (HH:mm)") },
                        modifier = Modifier.weight(1f),
                        readOnly = true,
                        enabled = false
                    )
                    IconButton(onClick = { timePickerDialog.show() }) {
                        Icon(Icons.Filled.Schedule, contentDescription = "Pick Time")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (name.value.isBlank() || date.value.isBlank() || time.value.isBlank()) {
                            Toast.makeText(
                                context,
                                "Name, Date, and Time are required",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Button
                        }
                        scope.launch {
                            saveOrUpdateEvent(
                                context = context,
                                isEditMode = isEditMode,
                                eventId = parsedEventId,
                                name = name.value,
                                description = description.value,
                                address = address.value,
                                date = date.value,
                                time = time.value,
                                navController = navController
                            )
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

suspend fun saveOrUpdateEvent(
    context: Context,
    isEditMode: Boolean,
    eventId: Int?,
    name: String,
    description: String,
    address: String,
    date: String,
    time: String,
    navController: NavHostController
) {
    try {
// Validar formato de fecha y hora (básico)
        if (!date.matches(Regex("\d{2}/\d{2}/\d{4}"))) {
            Toast.makeText(context, "Invalid date format (use dd/MM/yyyy)", Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (!time.matches(Regex("\d{2}:\d{2}"))) {
            Toast.makeText(context, "Invalid time format (use HH:mm)", Toast.LENGTH_SHORT).show()
            return
        }

// Crear o actualizar el evento
        val event = Event(
            id = if (isEditMode) eventId!! else EventStorage.generateEventId(),
            name = name,
            description = description,
            address = address,
            date = date,
            time = time
        )

        if (isEditMode) {
            EventStorage.updateEvent(event)
            Toast.makeText(context, "Event updated", Toast.LENGTH_SHORT).show()
        } else {
            EventStorage.addEvent(event)
            Toast.makeText(context, "Event created", Toast.LENGTH_SHORT).show()
        }
        navController.popBackStack()
    } catch (e: Exception) {
        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}