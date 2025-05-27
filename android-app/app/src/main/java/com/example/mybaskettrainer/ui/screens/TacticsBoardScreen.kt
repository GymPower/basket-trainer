package com.example.mybaskettrainer.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mybaskettrainer.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TacticsBoardScreen(navController: NavHostController) {
    var paths by remember { mutableStateOf(listOf<Pair<Path, Color>>()) }
    var currentPath by remember { mutableStateOf(Path()) }
    var currentColor by remember { mutableStateOf(Color.Red) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tactics Board") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                currentPath = Path().apply {
                                    moveTo(offset.x, offset.y)
                                }
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                currentPath.lineTo(
                                    change.position.x,
                                    change.position.y
                                )
                                paths = paths + (currentPath to currentColor)
                            },
                            onDragEnd = {
                                currentPath = Path()
                            }
                        )
                    }
            ) {
                val image = painterResource(id = R.drawable.basketball_court)
                val imageWidth = with(LocalDensity.current) { size.width }
                val imageHeight = with(LocalDensity.current) { size.height }

                drawImage(
                    image = image.painter,
                    dstSize = androidx.compose.ui.unit.IntSize(imageWidth.toInt(), imageHeight.toInt()),
                    contentScale = ContentScale.Fit
                )

                paths.forEach { (path, color) ->
                    drawPath(
                        path = path,
                        color = color,
                        style = Stroke(width = 5f)
                    )
                }

// Dibujar el camino actual mientras se arrastra
                drawPath(
                    path = currentPath,
                    color = currentColor,
                    style = Stroke(width = 5f)
                )
            }

// Botones para seleccionar color
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { currentColor = Color.Red },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Red")
                }
                Button(
                    onClick = { currentColor = Color.Blue },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Blue")
                }
                Button(
                    onClick = { currentColor = Color.Green },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Green")
                }
            }

            Button(
                onClick = {
                    paths = emptyList()
                    currentPath = Path()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.Clear, contentDescription = "Clear")
                Text("Clear Board")
            }
        }
    }
}
