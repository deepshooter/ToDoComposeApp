package com.deepshooter.todocomposeapp.navigations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.deepshooter.todocomposeapp.navigations.destinations.listComposable
import com.deepshooter.todocomposeapp.navigations.destinations.taskComposable
import com.deepshooter.todocomposeapp.util.Constants.LIST_SCREEN


@Composable
fun SetUpNavigation(navHostController: NavHostController) {

    val screen = remember(navHostController) {
        Screens(navHostController = navHostController)
    }

    NavHost(
        navController = navHostController,
        startDestination = LIST_SCREEN
    ) {
        listComposable(navigateToTaskScreen = screen.task)
        taskComposable(navigateToListScreen = screen.list)
    }
}