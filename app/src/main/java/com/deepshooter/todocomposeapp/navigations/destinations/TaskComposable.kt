package com.deepshooter.todocomposeapp.navigations.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.deepshooter.todocomposeapp.util.Action
import com.deepshooter.todocomposeapp.util.Constants
import com.deepshooter.todocomposeapp.util.Constants.TASK_SCREEN


fun NavGraphBuilder.taskComposable(navigateToListScreen: (Action) -> Unit) {

    composable(route = TASK_SCREEN, arguments = listOf(navArgument(Constants.TASK_ARGUMENT_KEY) {
        type = NavType.IntType
    })) {

    }
}