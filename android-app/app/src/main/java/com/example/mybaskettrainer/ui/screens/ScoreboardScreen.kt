package com.example.mybaskettrainer.ui.screens
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mybaskettrainer.data.local.ScoreboardStorage
import com.example.mybaskettrainer.data.model.Scoreboard
import com.example.mybaskettrainer.ui.theme.MyBasketTrainerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreboardScreen(navController: NavHostController) {
    val context = LocalContext.current
    var scoreboard by remember { mutableStateOf(ScoreboardStorage.getScoreboard()) }

    fun updateScoreboard() {
        scoreboard = ScoreboardStorage.getScoreboard()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scoreboard") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Home",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = scoreboard.homeScore.toString(),
                        style = MaterialTheme.typography.displayLarge,
                        fontSize = 48.sp
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(onClick = {
                            ScoreboardStorage.updateHomeScore(scoreboard.homeScore + 1)
                            updateScoreboard()
                        }) {
                            Text("+1")
                        }
                        Button(onClick = {
                            ScoreboardStorage.updateHomeScore(scoreboard.homeScore + 2)
                            updateScoreboard()
                        }) {
                            Text("+2")
                        }
                        Button(onClick = {
                            ScoreboardStorage.updateHomeScore(scoreboard.homeScore + 3)
                            updateScoreboard()
                        }) {
                            Text("+3")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        ScoreboardStorage.updateHomeScore(scoreboard.homeScore - 1)
                        updateScoreboard()
                    }) {
                        Text("-1")
                    }
                }

                Text(
                    text = "-",
                    style = MaterialTheme.typography.displayLarge,
                    fontSize = 48.sp
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Away",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = scoreboard.awayScore.toString(),
                        style = MaterialTheme.typography.displayLarge,
                        fontSize = 48.sp
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(onClick = {
                            ScoreboardStorage.updateAwayScore(scoreboard.awayScore + 1)
                            updateScoreboard()
                        }) {
                            Text("+1")
                        }
                        Button(onClick = {
                            ScoreboardStorage.updateAwayScore(scoreboard.awayScore + 2)
                            updateScoreboard()
                        }) {
                            Text("+2")
                        }
                        Button(onClick = {
                            ScoreboardStorage.updateAwayScore(scoreboard.awayScore + 3)
                            updateScoreboard()
                        }) {
                            Text("+3")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        ScoreboardStorage.updateAwayScore(scoreboard.awayScore - 1)
                        updateScoreboard()
                    }) {
                        Text("-1")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    ScoreboardStorage.resetScoreboard()
                    updateScoreboard()
                    Toast.makeText(context, "Scoreboard reset", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Reset Scoreboard")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScoreboardScreenPreview() {
    val fakeNavController = rememberNavController()
    MyBasketTrainerTheme {
        ScoreboardScreen(navController = fakeNavController)
    }
}