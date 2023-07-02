package com.deepshooter.todocomposeapp.ui.screens.list

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.deepshooter.todocomposeapp.R
import com.deepshooter.todocomposeapp.data.model.Priority
import com.deepshooter.todocomposeapp.data.model.TodoTask
import com.deepshooter.todocomposeapp.ui.theme.HighPriorityColor
import com.deepshooter.todocomposeapp.ui.theme.LARGEST_PADDING
import com.deepshooter.todocomposeapp.ui.theme.LARGE_PADDING
import com.deepshooter.todocomposeapp.ui.theme.MEDIUM_PADDING
import com.deepshooter.todocomposeapp.ui.theme.PRIORITY_INDICATOR_SIZE
import com.deepshooter.todocomposeapp.ui.theme.SMALL_PADDING
import com.deepshooter.todocomposeapp.ui.theme.TASK_ITEM_CUT_PADDING
import com.deepshooter.todocomposeapp.ui.theme.TASK_ITEM_ELEVATION
import com.deepshooter.todocomposeapp.ui.theme.TITLE_TEXT_SIZE
import com.deepshooter.todocomposeapp.ui.theme.taskItemBackgroundColor
import com.deepshooter.todocomposeapp.ui.theme.taskItemTextColor
import com.deepshooter.todocomposeapp.util.Action
import com.deepshooter.todocomposeapp.util.RequestState
import com.deepshooter.todocomposeapp.util.SearchAppBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun ListContent(
    todoTaskList: RequestState<List<TodoTask>>,
    searchedTaskList: RequestState<List<TodoTask>>,
    lowPriorityTasks: List<TodoTask>,
    highPriorityTasks: List<TodoTask>,
    sortState: RequestState<Priority>,
    searchAppBarState: SearchAppBarState,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    onSwipeToDelete: (Action, TodoTask) -> Unit,
    paddingValues: PaddingValues
) {

    if (sortState is RequestState.Success) {

        when {
            searchAppBarState == SearchAppBarState.TRIGGERED -> {
                if (searchedTaskList is RequestState.Success) {
                    HandleListContent(
                        tasks = searchedTaskList.data,
                        onSwipeToDelete = onSwipeToDelete,
                        navigateToTaskScreen = navigateToTaskScreen,
                        paddingValues = paddingValues
                    )
                }
            }

            sortState.data == Priority.NONE -> {
                if (todoTaskList is RequestState.Success) {
                    HandleListContent(
                        tasks = todoTaskList.data,
                        onSwipeToDelete = onSwipeToDelete,
                        navigateToTaskScreen = navigateToTaskScreen,
                        paddingValues = paddingValues
                    )
                }
            }

            sortState.data == Priority.LOW -> {
                HandleListContent(
                    tasks = lowPriorityTasks,
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToTaskScreen = navigateToTaskScreen,
                    paddingValues = paddingValues
                )
            }

            sortState.data == Priority.HIGH -> {
                HandleListContent(
                    tasks = highPriorityTasks,
                    onSwipeToDelete = onSwipeToDelete,
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
    onSwipeToDelete: (Action, TodoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    paddingValues: PaddingValues
) {

    if (tasks.isEmpty()) {
        EmptyContent()
    } else {
        DisplayTask(
            todoTaskList = tasks,
            onSwipeToDelete = onSwipeToDelete,
            navigateToTaskScreen = navigateToTaskScreen,
            paddingValues = paddingValues
        )
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayTask(
    todoTaskList: List<TodoTask>,
    onSwipeToDelete: (Action, TodoTask) -> Unit,
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

            val dismissState = rememberDismissState()
            val dismissDirection = dismissState.dismissDirection
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)

            if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
                val scope = rememberCoroutineScope()
                scope.launch {
                    delay(300)
                    onSwipeToDelete(Action.DELETE, task)
                }
            }

            val degrees by animateFloatAsState(
                targetValue =
                if (dismissState.targetValue == DismissValue.Default)
                    0F
                else
                    -180F
            )

            var itemAppeared by remember { mutableStateOf(false) }
            LaunchedEffect(key1 = true) {
                itemAppeared = true
            }

            AnimatedVisibility(
                visible = itemAppeared && !isDismissed,
                enter = expandVertically(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                ),
                exit = shrinkVertically(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            ) {

                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    background = { RedBackground(degrees = degrees) },
                    dismissContent = {
                        TaskItem(
                            todoTask = task,
                            navigateToTaskScreen = navigateToTaskScreen
                        )
                    })

            }

        }
    }
}


@Composable
fun TaskItem(
    todoTask: TodoTask,
    navigateToTaskScreen: (taskId: Int) -> Unit) {


    Card(
        modifier = Modifier
            .padding(
                bottom = SMALL_PADDING,
                top = SMALL_PADDING,
                start = MEDIUM_PADDING,
                end = MEDIUM_PADDING
            )
            .fillMaxWidth()
            .clickable { navigateToTaskScreen(todoTask.id) },
        colors = CardDefaults.cardColors(containerColor = taskItemBackgroundColor),
        shape = CutCornerShape(topEnd = TASK_ITEM_CUT_PADDING),
        elevation = CardDefaults.cardElevation(
            defaultElevation = TASK_ITEM_ELEVATION,
        )
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
                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = TITLE_TEXT_SIZE),
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
fun RedBackground(degrees: Float) {

    Card(
        modifier = Modifier
            .padding(
                bottom = SMALL_PADDING,
                top = SMALL_PADDING,
                start = MEDIUM_PADDING,
                end = MEDIUM_PADDING
            )
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = HighPriorityColor),
        shape = CutCornerShape(topEnd = LARGE_PADDING),
        elevation = CardDefaults.cardElevation(
            defaultElevation = TASK_ITEM_ELEVATION,
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = HighPriorityColor)
                .padding(horizontal = LARGEST_PADDING),
            contentAlignment = Alignment.CenterEnd
        ) {

            Icon(
                modifier = Modifier.rotate(degrees = degrees),
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(id = R.string.delete_icon),
                tint = Color.White
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

@Composable
@Preview
fun RedBackgroundPreview() {
    Column(modifier = Modifier.height(80.dp)) {
        RedBackground(degrees = 0F)
    }
}