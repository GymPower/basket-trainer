package com.example.mybaskettrainer.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mybaskettrainer.R
import com.example.mybaskettrainer.ui.theme.MyBasketTrainerTheme

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text= stringResource(R.string.trainer_register), style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = dni, onValueChange = { dni = it }, label = { Text(text= stringResource(R.string.dni)) })
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text(text = stringResource(R.string.name)) })
        OutlinedTextField(value = surname1, onValueChange = { surname1 = it }, label = { Text(text= stringResource(R.string.first_surname)) })
        OutlinedTextField(value = surname2, onValueChange = { surname2 = it }, label = { Text(text = stringResource(R.string.second_surname)) })
        OutlinedTextField(value = birthdate, onValueChange = { birthdate = it }, label = { Text(text = stringResource(R.string.birthdate)) })
        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text(text = stringResource(R.string.phone)) })
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text(text = stringResource(R.string.email)) })
        OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text(text = stringResource(R.string.user)) })
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text(text = stringResource(R.string.password)) }, visualTransformation = PasswordVisualTransformation())

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            // Aquí hay que llamar a la API (POST) para registrar el entrenador
            println("Registrado: $username")
            navController.navigate("loginScreen")
        }) {
            Text(text= stringResource(R.string.register))
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = {
            navController.navigate("loginScreen")
        }) {
            Text(text = stringResource(R.string.text_to_sing_up))
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
