package com.deepshooter.todocomposeapp.navigations.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.deepshooter.todocomposeapp.ui.screens.splash.SplashScreen
import com.deepshooter.todocomposeapp.util.Constants


fun NavGraphBuilder.splashComposable(
    navigateToListScreen: () -> Unit
) {

    composable(
        route = Constants.SPLASH_SCREEN
    ) {
        SplashScreen(navigateToListScreen = navigateToListScreen)
    }

}