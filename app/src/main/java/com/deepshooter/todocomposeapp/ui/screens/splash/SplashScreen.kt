package com.deepshooter.todocomposeapp.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.deepshooter.todocomposeapp.R
import com.deepshooter.todocomposeapp.ui.theme.LOGO_HEIGHT
import com.deepshooter.todocomposeapp.ui.theme.splashScreenBackground
import com.deepshooter.todocomposeapp.util.Constants.SPLASH_SCREEN_DELAY
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navigateToListScreen: () -> Unit) {

    LaunchedEffect(key1 = true) {
        delay(SPLASH_SCREEN_DELAY)
        navigateToListScreen()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.splashScreenBackground),
        contentAlignment = Alignment.Center
    ) {

        Image(
            modifier = Modifier.height(LOGO_HEIGHT),
            painter = painterResource(id = getLogo()),
            contentDescription = stringResource(id = R.string.to_do_app_logo)
        )
    }

}

@Composable
fun getLogo(): Int {
    return if (isSystemInDarkTheme())
        R.drawable.logo_dark
    else
        R.drawable.logo_light
}

@Composable
@Preview
fun SplashScreenPreview() {
    SplashScreen(navigateToListScreen = {})
}