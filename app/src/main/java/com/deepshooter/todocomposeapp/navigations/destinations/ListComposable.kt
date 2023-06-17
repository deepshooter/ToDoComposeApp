package com.deepshooter.todocomposeapp.navigations.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.deepshooter.todocomposeapp.ui.screens.list.ListScreen
import com.deepshooter.todocomposeapp.ui.viewmodel.SharedViewModel
import com.deepshooter.todocomposeapp.util.Constants.LIST_ARGUMENT_KEY
import com.deepshooter.todocomposeapp.util.Constants.LIST_SCREEN

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel) {

    composable(route = LIST_SCREEN, arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
        type = NavType.StringType
    })) {
        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel)
    }
}