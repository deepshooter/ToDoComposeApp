package com.deepshooter.todocomposeapp.ui.screens.task

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.deepshooter.todocomposeapp.data.model.TodoTask
import com.deepshooter.todocomposeapp.util.Action


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(selectedTask: TodoTask?, navigateToListScreen: (Action) -> Unit) {

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = navigateToListScreen
            )
        },
        content = { it }
    )
}