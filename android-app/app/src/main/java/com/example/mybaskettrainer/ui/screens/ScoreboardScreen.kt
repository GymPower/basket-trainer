
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mybaskettrainer.R
import com.example.mybaskettrainer.data.local.ScoreboardStorage
import com.example.mybaskettrainer.data.model.Scoreboard
import com.example.mybaskettrainer.navigation.Routes
import com.example.mybaskettrainer.ui.theme.MyBasketTrainerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreboardScreen(navController: NavHostController) {
    val context = LocalContext.current
    var scoreboard by remember { mutableStateOf(ScoreboardStorage.getScoreboard()) }
    var menuExpanded by remember { mutableStateOf(false) }

    // Función para actualizar el marcador
    fun updateScoreboard() {
        scoreboard = ScoreboardStorage.getScoreboard()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.scoreboard)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = stringResource(R.string.menu)
                        )
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.main_screen)) },
                            onClick = {
                                navController.navigate(Routes.MainScreen.route) {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = false }
                                    launchSingleTop = true
                                }
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.teams)) },
                            onClick = {
                                navController.navigate(Routes.TeamScreen.route) {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = false }
                                    launchSingleTop = true
                                }
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.players)) },
                            onClick = {
                                navController.navigate(Routes.PlayerScreen.route) {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = false }
                                    launchSingleTop = true
                                }
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.agenda)) },
                            onClick = {
                                navController.navigate(Routes.AgendaScreen.route) {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = false }
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
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.tactics_board)) },
                            onClick = {
                                navController.navigate(Routes.TacticsBoardScreen.route) {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = false }
                                    launchSingleTop = true
                                }
                                menuExpanded = false
                            }
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
                        text = stringResource(R.string.home_team),
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
                            Text(stringResource(R.string.plus_one))
                        }
                        Button(onClick = {
                            ScoreboardStorage.updateHomeScore(scoreboard.homeScore + 2)
                            updateScoreboard()
                        }) {
                            Text(stringResource(R.string.plus_two))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(onClick = {
                            ScoreboardStorage.updateHomeScore(scoreboard.homeScore + 3)
                            updateScoreboard()
                        }) {
                            Text(stringResource(R.string.plus_three))
                        }
                        Button(onClick = {
                            ScoreboardStorage.updateHomeScore(scoreboard.homeScore - 1)
                            updateScoreboard()
                        }) {
                            Text(stringResource(R.string.minus_one))
                        }
                    }
                }

                Text(
                    text = "-",
                    style = MaterialTheme.typography.displayLarge,
                    fontSize = 48.sp
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.away_team),
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
                            Text(stringResource(R.string.plus_one))
                        }
                        Button(onClick = {
                            ScoreboardStorage.updateAwayScore(scoreboard.awayScore + 2)
                            updateScoreboard()
                        }) {
                            Text(stringResource(R.string.plus_two))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(onClick = {
                            ScoreboardStorage.updateAwayScore(scoreboard.awayScore + 3)
                            updateScoreboard()
                        }) {
                            Text(stringResource(R.string.plus_three))
                        }
                        Button(onClick = {
                            ScoreboardStorage.updateAwayScore(scoreboard.awayScore - 1)
                            updateScoreboard()
                        }) {
                            Text(stringResource(R.string.minus_one))
                        }
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
                Text(stringResource(R.string.reset_scoreboard))
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