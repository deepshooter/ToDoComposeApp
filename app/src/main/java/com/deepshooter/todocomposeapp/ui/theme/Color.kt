package com.deepshooter.todocomposeapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


val LightGray = Color(0xFFFCFCFC)
val MediumGray = Color(0xFF9C9C9C)
val DarkGray = Color(0xFF141414)
val Purple500 = Color(0xFF6200EE)
val Teal500 = Color(0xFF009688)
val Purple700 = Color(0xFF673AB7)
val Purple400 = Color(0xFF7E57C2)

val LowPriorityColor = Color(0xFF4CAF50)
val MediumPriorityColor = Color(0xFFFFC107)
val HighPriorityColor = Color(0xFFF44336)
val NonePriorityColor = MediumGray


val ColorScheme.topAppBarContentColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) LightGray else Color.White

val ColorScheme.topAppBarBackgroundColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.Black else Purple500

val ColorScheme.fabBackgroundColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Purple700 else Teal500

val taskItemBackgroundColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) DarkGray else Color.White

val taskItemTextColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) LightGray else DarkGray

val ColorScheme.splashScreenBackground: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.Black else Purple700
