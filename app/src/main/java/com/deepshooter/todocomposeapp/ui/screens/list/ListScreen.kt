package com.deepshooter.todocomposeapp.ui.screens.list

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.deepshooter.todocomposeapp.R
import com.deepshooter.todocomposeapp.ui.theme.fabBackgroundColor
import com.deepshooter.todocomposeapp.ui.viewmodel.SharedViewModel
import com.deepshooter.todocomposeapp.util.Action
import com.deepshooter.todocomposeapp.util.SearchAppBarState
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navigateToTaskScreen: (taskId: Int) -> Unit , sharedViewModel: SharedViewModel) {

    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTasks()
    }

    val action by sharedViewModel.action
    val allTasks by sharedViewModel.allTask.collectAsState()

    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState
    val snackBarHostState = remember { SnackbarHostState() }

    DisplaySnackBar(
        snackBarHostState = snackBarHostState,
        handleDatabaseActions = { sharedViewModel.handleDatabaseAction(action = action) },
        taskTitle = sharedViewModel.title.value,
        action = action
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState)
        },
        content = {
            ListContent(
                todoTaskList = allTasks,
                navigateToTaskScreen = navigateToTaskScreen,
                paddingValues = it
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        })

}


@Composable
fun ListFab(onFabClicked: (taskId: Int) -> Unit) {
    FloatingActionButton(
        onClick = {
            onFabClicked(-1)
        }, containerColor = MaterialTheme.colorScheme.fabBackgroundColor) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White
        )
    }
}


@Composable
fun DisplaySnackBar(
    snackBarHostState: SnackbarHostState,
    handleDatabaseActions: () -> Unit,
    taskTitle: String,
    action: Action

) {

    handleDatabaseActions()

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = action) {

        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = snackBarHostState.showSnackbar(
                    message = "${action.name} :$taskTitle",
                    actionLabel = "OK"
                )
            }
        }

    }
}
