package com.example.mybaskettrainer.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BlackBackground = Color(0xFF000000)
private val WhiteText = Color(0xFFFFFFFF)
private val OrangeDetails = Color(0xFFFF9800)

private val AppColorScheme = darkColorScheme(
    primary = OrangeDetails,
    onPrimary = BlackBackground,
    secondary = OrangeDetails,
    onSecondary = BlackBackground,
    tertiary = OrangeDetails,
    onTertiary = BlackBackground,
    background = BlackBackground,
    onBackground = WhiteText,
    surface = BlackBackground,
    onSurface = WhiteText,
    error = Color.Red,
    onError = WhiteText,
    outline = OrangeDetails,
    surfaceVariant = BlackBackground,
    onSurfaceVariant = WhiteText
)

@Composable
fun MyBasketTrainerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}