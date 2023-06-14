package com.capstone.empoweru.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black

private val DarkColorPalette = darkColors(
    primary = Red200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Red200,
    primaryVariant = Purple700,
    secondary = Teal200
)


    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */

@Composable
fun EmpowerUTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            background = Color.Black
        )
    } else {
        lightColors(
            background = Color.White
        )
    }

    MaterialTheme(
        colors = colors,
        typography = PoppinsTypography,
        shapes = Shapes,
        content = content
    )
}

@Composable
fun BottomBarTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        lightColors(
            primary = Black,
            primaryVariant = Red200,
            secondary = Teal200
        )
    } else {
        lightColors(
            primary = White,
            primaryVariant = Red200,
            secondary = Teal200
        )
    }

    MaterialTheme(
        colors = colors,
        typography = PoppinsTypography,
        shapes = Shapes,
        content = content
    )
}