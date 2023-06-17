package com.deepshooter.todocomposeapp.ui.screens.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.deepshooter.todocomposeapp.R
import com.deepshooter.todocomposeapp.components.PriorityItem
import com.deepshooter.todocomposeapp.data.model.Priority
import com.deepshooter.todocomposeapp.ui.theme.LARGE_PADDING
import com.deepshooter.todocomposeapp.ui.theme.topAppBarBackgroundColor
import com.deepshooter.todocomposeapp.ui.theme.topAppBarContentColor

@Composable
fun ListAppBar() {
    DefaultListAppBar(onSearchClicked = {}, onSortClicked = {}, onDeleteClicked = {})
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultListAppBar(onSearchClicked: () -> Unit,onSortClicked: (Priority) -> Unit,onDeleteClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Tasks",
                color = MaterialTheme.colorScheme.topAppBarContentColor,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        actions = {
            ListAppBarActions(
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteClicked = onDeleteClicked
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgroundColor
        )
    )
}

@Composable
fun ListAppBarActions(onSearchClicked: () -> Unit, onSortClicked: (Priority) -> Unit,
                      onDeleteClicked: () -> Unit) {
    SearchAction(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    DeleteAllActions(onDeleteClicked = onDeleteClicked)
}

@Composable
fun SearchAction(onSearchClicked: () -> Unit) {

    IconButton(onClick = {
        onSearchClicked
    }) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(id = R.string.search_action),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

@Composable
fun SortAction(onSortClicked: (Priority) -> Unit) {

    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = {
        expanded = true
    }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter_list),
            contentDescription = stringResource(id = R.string.sort_action),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { PriorityItem(priority = Priority.LOW) },
                onClick = {
                    expanded = false
                    onSortClicked(Priority.LOW)
                })
            DropdownMenuItem(
                text = { PriorityItem(priority = Priority.HIGH) },
                onClick = {
                    expanded = false
                    onSortClicked(Priority.HIGH)
                })
            DropdownMenuItem(
                text = { PriorityItem(priority = Priority.NONE) },
                onClick = {
                    expanded = false
                    onSortClicked(Priority.NONE)
                })
        }

    }
}

@Composable
fun DeleteAllActions(onDeleteClicked: () -> Unit) {

    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = {
        expanded = true
    }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_vertical_menu),
            contentDescription = stringResource(id = R.string.delete_all_action),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {

            DropdownMenuItem(
                text = {
                    Text(
                        modifier = Modifier.padding(start = LARGE_PADDING),
                        text = stringResource(id = R.string.delete_all_action),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                onClick = {
                    expanded = false
                    onDeleteClicked()
                })
        }
    }
}

@Composable
@Preview
fun DefaultListAppBarPreview() {
    DefaultListAppBar(onSearchClicked = {}, onSortClicked = {}, onDeleteClicked = {})
}