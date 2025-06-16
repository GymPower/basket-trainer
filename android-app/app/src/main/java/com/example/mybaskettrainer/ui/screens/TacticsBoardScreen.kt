package com.example.mybaskettrainer.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mybaskettrainer.R
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.mybaskettrainer.ui.theme.MyBasketTrainerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TacticsBoardScreen(navController: NavHostController) {
    var paths by remember { mutableStateOf(listOf<Pair<Path, Color>>()) }
    var currentPath by remember { mutableStateOf(Path()) }
    var currentColor by remember { mutableStateOf(Color.Red) }
    val backgroundPainter = painterResource(id = R.drawable.basketball_court)
    var menuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.tactics_board)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = (stringResource(R.string.back)))
                    }
                },
                actions = {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(Icons.Filled.Menu, contentDescription = (stringResource(R.string.menu)))
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text((stringResource(R.string.main_screen))) },
                            onClick = {
                                navController.navigate("main_screen") {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = false
                                    }
                                    launchSingleTop = true
                                }
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text((stringResource(R.string.teams))) },
                            onClick = {
                                navController.navigate("team_screen") {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = false
                                    }
                                    launchSingleTop = true
                                }
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.players)) },
                            onClick = {
                                navController.navigate("player_screen") {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = false
                                    }
                                    launchSingleTop = true
                                }
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.agenda)) },
                            onClick = {
                                navController.navigate("agenda_screen") {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = false
                                    }
                                    launchSingleTop = true
                                }
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.scoreboard)) },
                            onClick = {
                                navController.navigate("scoreboard_screen") {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = false
                                    }
                                    launchSingleTop = true
                                }
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.scoreboard)) },
                            enabled = false,
                            onClick = {}
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                Image(
                    painter = backgroundPainter,
                    contentDescription = "Cancha de baloncesto",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )

                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    currentPath = Path().apply {
                                        moveTo(offset.x, offset.y)
                                    }
                                },
                                onDrag = { change, _ ->
                                    change.consume()
                                    currentPath.lineTo(change.position.x, change.position.y)
                                    paths = paths + (currentPath to currentColor)
                                },
                                onDragEnd = {
                                    currentPath = Path()
                                }
                            )
                        }
                ) {
                    paths.forEach { (path, color) ->
                        drawPath(
                            path = path,
                            color = color,
                            style = Stroke(width = 5f)
                        )
                    }

                    drawPath(
                        path = currentPath,
                        color = currentColor,
                        style = Stroke(width = 5f)
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { currentColor = Color.Red },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text(stringResource(R.string.red))
                }
                Button(onClick = { currentColor = Color.Blue },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text(stringResource(R.string.blue))
                }
                Button(onClick = { currentColor = Color.Green },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                ) {
                    Text(stringResource(R.string.green))
                }
            }

            Button(
                onClick = {
                    paths = emptyList()
                    currentPath = Path()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.Clear, contentDescription = "Limpiar")
                Text(stringResource(R.string.clear_board))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TacticsBoardScreenPreview() {
    val fakeNavController = rememberNavController()
    MyBasketTrainerTheme {
        TacticsBoardScreen(navController = fakeNavController)
    }
}
