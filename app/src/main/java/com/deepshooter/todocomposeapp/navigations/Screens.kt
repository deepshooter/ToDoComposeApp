package com.deepshooter.todocomposeapp.navigations

import androidx.navigation.NavHostController
import com.deepshooter.todocomposeapp.util.Action
import com.deepshooter.todocomposeapp.util.Constants.LIST_SCREEN

class Screens(navHostController: NavHostController) {

    val list: (Action) -> Unit = { action ->
        navHostController.navigate("list/${action.name}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }

    val task: (Int) -> Unit = { taskId ->
        navHostController.navigate("task/${taskId}")
    }
}