package com.example.mybaskettrainer.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mybaskettrainer.R
import com.example.mybaskettrainer.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController){

    LaunchedEffect (key1 = true){
        delay(5000)
        navController.popBackStack()
        navController.navigate(Routes.MainScreen.route)
    }


    Splash()
}

@Composable
fun Splash(){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(painter = painterResource(id= R.drawable.logo),
              contentDescription = "Logo",
              Modifier.size(400.dp, 400.dp))
        Text(
             text = stringResource(id = R.string.welcome_message),
             fontSize = dimensionResource(id = R.dimen.title_font_size).value.sp,
             fontWeight = FontWeight.Bold
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    Splash()
}