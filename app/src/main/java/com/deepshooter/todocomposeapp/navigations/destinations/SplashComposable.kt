package com.deepshooter.todocomposeapp.navigations.destinations

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.deepshooter.todocomposeapp.ui.screens.splash.SplashScreen
import com.deepshooter.todocomposeapp.util.Constants
import com.deepshooter.todocomposeapp.util.Constants.ANIMATION_TRANSITION_300


fun NavGraphBuilder.splashComposable(
    navigateToListScreen: () -> Unit
) {

    composable(
        route = Constants.SPLASH_SCREEN,
        exitTransition = {
            slideOutVertically(
                targetOffsetY = { fullHeight -> -fullHeight },
                animationSpec = tween(durationMillis = ANIMATION_TRANSITION_300)
            )
        }
    ) {
        SplashScreen(navigateToListScreen = navigateToListScreen)
    }

}