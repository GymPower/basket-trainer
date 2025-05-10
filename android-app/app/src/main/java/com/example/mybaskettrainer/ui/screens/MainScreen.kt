package com.example.mybaskettrainer.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.rememberNavController
import com.example.mybaskettrainer.R
import com.example.mybaskettrainer.ui.theme.MyBasketTrainerTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )
                NavigationDrawerItem(
                    label = { Text(stringResource(R.string.my_teams)) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        Toast.makeText(context, "Funcionalidad 'Mis equipos' en desarrollo", Toast.LENGTH_SHORT).show()
                    }
                )
                NavigationDrawerItem(
                    label = { Text(stringResource(R.string.my_players)) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        Toast.makeText(context, "Funcionalidad 'Mis jugadores' en desarrollo", Toast.LENGTH_SHORT).show()
                    }
                )
                NavigationDrawerItem(
                    label = { Text(stringResource(R.string.agenda)) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        Toast.makeText(context, "Funcionalidad 'Agenda' en desarrollo", Toast.LENGTH_SHORT).show()
                    }
                )
                NavigationDrawerItem(
                    label = { Text(stringResource(R.string.playbook)) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        Toast.makeText(context, "Funcionalidad 'Pizarra' en desarrollo", Toast.LENGTH_SHORT).show()
                    }
                )
                NavigationDrawerItem(
                    label = { Text(stringResource(R.string.scoreboard)) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        Toast.makeText(context, "Funcionalidad 'Marcador' en desarrollo", Toast.LENGTH_SHORT).show()
                    }
                )
                NavigationDrawerItem(
                    label = { Text(stringResource(R.string.logout)) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.popBackStack()
                        navController.navigate("loginScreen") {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = stringResource(R.string.welcome_message),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Próximo evento: [En desarrollo]",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Selección de equipos: [En desarrollo]",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val fakeNavController = rememberNavController()
    MyBasketTrainerTheme {
        MainScreen(navController = fakeNavController)

        }
}

