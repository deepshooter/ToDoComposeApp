package com.deepshooter.todocomposeapp.navigations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.deepshooter.todocomposeapp.navigations.destinations.listComposable
import com.deepshooter.todocomposeapp.navigations.destinations.taskComposable
import com.deepshooter.todocomposeapp.ui.viewmodel.SharedViewModel
import com.deepshooter.todocomposeapp.util.Constants.LIST_SCREEN


@Composable
fun SetUpNavigation(navHostController: NavHostController, sharedViewModel: SharedViewModel) {

    val screen = remember(navHostController) {
        Screens(navHostController = navHostController)
    }

    NavHost(
        navController = navHostController,
        startDestination = LIST_SCREEN
    ) {
        listComposable(navigateToTaskScreen = screen.task, sharedViewModel = sharedViewModel)
        taskComposable(navigateToListScreen = screen.list)
    }
}