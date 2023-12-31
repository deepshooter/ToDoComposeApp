package com.deepshooter.todocomposeapp.ui.screens.task

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.deepshooter.todocomposeapp.R
import com.deepshooter.todocomposeapp.components.DisplayAlertDialog
import com.deepshooter.todocomposeapp.data.model.Priority
import com.deepshooter.todocomposeapp.data.model.TodoTask
import com.deepshooter.todocomposeapp.ui.theme.TOP_APP_BAR_TITLE_SIZE
import com.deepshooter.todocomposeapp.ui.theme.topAppBarBackgroundColor
import com.deepshooter.todocomposeapp.ui.theme.topAppBarContentColor
import com.deepshooter.todocomposeapp.util.Action


@Composable
fun TaskAppBar(selectedTask: TodoTask?, navigateToListScreen: (Action) -> Unit) {

    if (selectedTask == null) {
        NewTaskAppBar(navigateToListScreen = navigateToListScreen)
    } else {
        UpdateTaskAppBar(
            selectedTask = selectedTask,
            navigateToListScreen = navigateToListScreen
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskAppBar(navigateToListScreen: (Action) -> Unit) {

    TopAppBar(
        navigationIcon = {
            BackAction(onBackClicked = navigateToListScreen)
        },
        title = {
            Text(
                text = stringResource(id = R.string.add_task),
                color = MaterialTheme.colorScheme.topAppBarContentColor,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = TOP_APP_BAR_TITLE_SIZE)
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgroundColor
        ),
        actions = {
            AddAction(onAddClicked = navigateToListScreen)
        }
    )

}


@Composable
fun BackAction(
    onBackClicked: (Action) -> Unit
) {
    IconButton(
        onClick = {
            onBackClicked(Action.NO_ACTION)
        }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.back_arrow),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}


@Composable
fun AddAction(
    onAddClicked: (Action) -> Unit
) {
    IconButton(
        onClick = {
            onAddClicked(Action.ADD)
        }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.add_task),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTaskAppBar(selectedTask: TodoTask, navigateToListScreen: (Action) -> Unit) {

    TopAppBar(
        navigationIcon = {
            CloseAction(onCloseClicked = navigateToListScreen)
        },
        title = {
            Text(
                text = selectedTask.title,
                color = MaterialTheme.colorScheme.topAppBarContentColor,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = TOP_APP_BAR_TITLE_SIZE
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgroundColor
        ),
        actions = {

            UpdateTaskAppBarActions(
                selectedTask = selectedTask,
                navigateToListScreen = navigateToListScreen
            )

        }
    )

}


@Composable
fun UpdateTaskAppBarActions(
    selectedTask: TodoTask,
    navigateToListScreen: (Action) -> Unit
) {

    var openDialog by remember { mutableStateOf(false) }

    DisplayAlertDialog(
        title = stringResource(
            id = R.string.delete_task,
            selectedTask.title
        ),
        message = stringResource(
            id = R.string.delete_task_confirmation,
            selectedTask.title
        ),
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = {
            navigateToListScreen(Action.DELETE)
        })



    DeleteAction(onDeleteClicked = {
        openDialog = true
    })

    UpdateAction(onUpdateClicked = navigateToListScreen)


}

@Composable
fun CloseAction(
    onCloseClicked: (Action) -> Unit
) {
    IconButton(
        onClick = {
            onCloseClicked(Action.NO_ACTION)
        }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(id = R.string.close_icon),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

@Composable
fun DeleteAction(
    onDeleteClicked: () -> Unit
) {
    IconButton(
        onClick = {
            onDeleteClicked()
        }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_icon),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

@Composable
fun UpdateAction(
    onUpdateClicked: (Action) -> Unit
) {
    IconButton(
        onClick = {
            onUpdateClicked(Action.UPDATE)
        }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.update_icon),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}



@Composable
@Preview
fun NewTaskAppBarPreview() {
    NewTaskAppBar(navigateToListScreen = {})
}

@Composable
@Preview
fun UpdateTaskAppBarPreview() {
    UpdateTaskAppBar(selectedTask = TodoTask(0, "Avinash", "Some Text", Priority.MEDIUM), navigateToListScreen = {})
}