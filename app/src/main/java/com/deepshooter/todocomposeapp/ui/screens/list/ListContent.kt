package com.deepshooter.todocomposeapp.ui.screens.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.deepshooter.todocomposeapp.data.model.Priority
import com.deepshooter.todocomposeapp.data.model.TodoTask
import com.deepshooter.todocomposeapp.ui.theme.LARGE_PADDING
import com.deepshooter.todocomposeapp.ui.theme.PRIORITY_INDICATOR_SIZE
import com.deepshooter.todocomposeapp.ui.theme.TASK_ITEM_ELEVATION
import com.deepshooter.todocomposeapp.ui.theme.taskItemBackgroundColor
import com.deepshooter.todocomposeapp.ui.theme.taskItemTextColor
import com.deepshooter.todocomposeapp.util.RequestState
import com.deepshooter.todocomposeapp.util.SearchAppBarState


@Composable
fun ListContent(
    todoTaskList: RequestState<List<TodoTask>>,
    searchedTaskList: RequestState<List<TodoTask>>,
    lowPriorityTasks: List<TodoTask>,
    highPriorityTasks: List<TodoTask>,
    sortState: RequestState<Priority>,
    searchAppBarState: SearchAppBarState,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    paddingValues: PaddingValues
) {

    if (sortState is RequestState.Success) {

        when {
            searchAppBarState == SearchAppBarState.TRIGGERED -> {
                if (searchedTaskList is RequestState.Success) {
                    HandleListContent(
                        tasks = searchedTaskList.data,
                        navigateToTaskScreen = navigateToTaskScreen,
                        paddingValues = paddingValues
                    )
                }
            }

            sortState.data == Priority.NONE -> {
                if (todoTaskList is RequestState.Success) {
                    HandleListContent(
                        tasks = todoTaskList.data,
                        navigateToTaskScreen = navigateToTaskScreen,
                        paddingValues = paddingValues
                    )
                }
            }

            sortState.data == Priority.LOW -> {
                HandleListContent(
                    tasks = lowPriorityTasks,
                    navigateToTaskScreen = navigateToTaskScreen,
                    paddingValues = paddingValues
                )
            }

            sortState.data == Priority.HIGH -> {
                HandleListContent(
                    tasks = highPriorityTasks,
                    navigateToTaskScreen = navigateToTaskScreen,
                    paddingValues = paddingValues
                )
            }
        }

    }

}

@Composable
fun HandleListContent(
    tasks: List<TodoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    paddingValues: PaddingValues
) {

    if (tasks.isEmpty()) {
        EmptyContent()
    } else {
        DisplayTask(
            todoTaskList = tasks,
            navigateToTaskScreen = navigateToTaskScreen,
            paddingValues = paddingValues
        )
    }
}

@Composable
fun DisplayTask(
    todoTaskList: List<TodoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    paddingValues: PaddingValues
) {

    LazyColumn(Modifier.padding(paddingValues)) {
        items(
            items = todoTaskList,
            key = { task ->
                task.id
            }
        ) { task ->
            TaskItem(
                todoTask = task,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(
    todoTask: TodoTask,
    navigateToTaskScreen: (taskId: Int) -> Unit) {


    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = taskItemBackgroundColor,
        shape = RectangleShape,
        tonalElevation = TASK_ITEM_ELEVATION,
        onClick = {
            navigateToTaskScreen(todoTask.id)
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LARGE_PADDING)
        ) {

            Row {
                Text(
                    modifier = Modifier.weight(8f),
                    text = todoTask.title,
                    color = taskItemTextColor,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(PRIORITY_INDICATOR_SIZE)
                    ) {
                        drawCircle(color = todoTask.priority.color)
                    }
                }
            }

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = todoTask.description,
                color = taskItemTextColor,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

        }

    }
}


@Composable
@Preview
fun TaskItemPreview() {
    TaskItem(
        todoTask = TodoTask(
            0,
            "Title",
            "Description Description Description",
            Priority.MEDIUM
        ), navigateToTaskScreen = {})
}