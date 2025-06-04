package com.example.mybaskettrainer.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mybaskettrainer.R
import com.example.mybaskettrainer.data.remote.ApiClient
import com.example.mybaskettrainer.data.remote.dto.TrainerRequest
import com.example.mybaskettrainer.navigation.Routes
import com.example.mybaskettrainer.ui.theme.MyBasketTrainerTheme
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavHostController) {
    var dni by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var surname1 by remember { mutableStateOf("") }
    var surname2 by remember { mutableStateOf("") }
    var birthdate by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(2.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.trainer_register),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        OutlinedTextField(
            value = dni,
            onValueChange = { dni = it },
            label = { Text(text = stringResource(R.string.dni), color = MaterialTheme.colorScheme.onSurface) },
            placeholder = { Text("Ej: 12345678Z", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !isLoading
        )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = stringResource(R.string.name), color = MaterialTheme.colorScheme.onSurface) },
            placeholder = { Text("Ej: Juan", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !isLoading
        )
        OutlinedTextField(
            value = surname1,
            onValueChange = { surname1 = it },
            label = { Text(text = stringResource(R.string.first_surname), color = MaterialTheme.colorScheme.onSurface) },
            placeholder = { Text("Ej: Pérez", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !isLoading
        )
        OutlinedTextField(
            value = surname2,
            onValueChange = { surname2 = it },
            label = { Text(text = stringResource(R.string.second_surname), color = MaterialTheme.colorScheme.onSurface) },
            placeholder = { Text("Ej: González (opcional)", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !isLoading
        )
        OutlinedTextField(
            value = birthdate,
            onValueChange = { birthdate = it },
            label = { Text(text = stringResource(R.string.birthdate), color = MaterialTheme.colorScheme.onSurface) },
            placeholder = { Text("Ej: YYYY-MM-DD", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            enabled = !isLoading
        )
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text(text = stringResource(R.string.phone), color = MaterialTheme.colorScheme.onSurface) },
            placeholder = { Text("Ej: 123456789", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            enabled = !isLoading
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = stringResource(R.string.email), color = MaterialTheme.colorScheme.onSurface) },
            placeholder = { Text("Ej: usuario@ejemplo.com", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            enabled = !isLoading
        )
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = stringResource(R.string.user), color = MaterialTheme.colorScheme.onSurface) },
            placeholder = { Text("Ej: juanperez", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !isLoading
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = stringResource(R.string.password), color = MaterialTheme.colorScheme.onSurface) },
            placeholder = { Text("Ej: Contraseña123", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (dni.isNotBlank() && username.isNotBlank() && password.isNotBlank() && name.isNotBlank() && surname1.isNotBlank() && birthdate.isNotBlank() && phone.isNotBlank() && email.isNotBlank()) {
                    scope.launch {
                        isLoading = true
                        try {
                            val trainerRequest = TrainerRequest(
                                dni = dni,
                                name = name,
                                firstSurname = surname1,
                                secondSurname = surname2.takeIf { it.isNotBlank() },
                                birthdate = birthdate,
                                telephone = phone,
                                email = email,
                                username = username,
                                password = password
                            )
                            val response = ApiClient.authApi.registerTrainer(trainerRequest)
                            isLoading = false
                            if (response.isSuccessful) {
                                Toast.makeText(context, "Registro exitoso", Toast.LENGTH_LONG).show()
                                navController.navigate(Routes.LoginScreen.route)
                            } else {
                                Toast.makeText(context, "Error en el registro: ${response.message()}", Toast.LENGTH_LONG).show()
                            }
                        } catch (e: Exception) {
                            isLoading = false
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Por favor, llena todos los campos requeridos", Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(text = stringResource(R.string.register), color = MaterialTheme.colorScheme.onPrimary)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = {
                navController.navigate(Routes.LoginScreen.route)
            },
            enabled = !isLoading
        ) {
            Text(text = stringResource(R.string.text_to_sing_up), color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    val fakeNavController = rememberNavController()
    MyBasketTrainerTheme {
        RegisterScreen(navController = fakeNavController)
    }
}