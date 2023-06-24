package com.deepshooter.todocomposeapp.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepshooter.todocomposeapp.data.model.Priority
import com.deepshooter.todocomposeapp.data.model.TodoTask
import com.deepshooter.todocomposeapp.data.repositories.DataStoreRepository
import com.deepshooter.todocomposeapp.data.repositories.ToDoRepository
import com.deepshooter.todocomposeapp.util.Action
import com.deepshooter.todocomposeapp.util.Constants.MAX_TITLE_LENGTH
import com.deepshooter.todocomposeapp.util.RequestState
import com.deepshooter.todocomposeapp.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: ToDoRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {


    var id by mutableStateOf(0)
        private set
    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var priority by mutableStateOf(Priority.LOW)
        private set



    var action by mutableStateOf(Action.NO_ACTION)
        private set



    var searchAppBarState by mutableStateOf(SearchAppBarState.CLOSED)
        private set
    var searchTextState by mutableStateOf("")
        private set



    private val _allTask = MutableStateFlow<RequestState<List<TodoTask>>>(RequestState.Idle)
    val allTask: StateFlow<RequestState<List<TodoTask>>> = _allTask

    private val _searchedTask = MutableStateFlow<RequestState<List<TodoTask>>>(RequestState.Idle)
    val searchedTask: StateFlow<RequestState<List<TodoTask>>> = _searchedTask

    private val _sortState = MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortState: StateFlow<RequestState<Priority>> = _sortState


    init {
        getAllTasks()
        readSortState()
    }

    private fun getAllTasks() {

        _allTask.value = RequestState.Loading

        try {
            viewModelScope.launch {
                repository.getAllTasks.collect {
                    _allTask.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allTask.value = RequestState.Error(e)
        }

    }

    fun getSearchTask(searchQuery: String) {

        _searchedTask.value = RequestState.Loading

        try {
            viewModelScope.launch {
                repository.searchDatabase(searchQuery = "%$searchQuery%").collect { searchedTask ->
                    _searchedTask.value = RequestState.Success(searchedTask)
                }
            }
        } catch (e: Exception) {
            _searchedTask.value = RequestState.Error(e)
        }

        searchAppBarState = SearchAppBarState.TRIGGERED
    }

    val lowPriorityTasks: StateFlow<List<TodoTask>> =
        repository.sortByLowPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            emptyList()
        )

    val highPriorityTasks: StateFlow<List<TodoTask>> =
        repository.sortByHighPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            emptyList()
        )


    private fun readSortState() {
        _sortState.value = RequestState.Loading

        try {
            viewModelScope.launch {
                dataStoreRepository.readSortState
                    .map { Priority.valueOf(it) }
                    .collect {
                        _sortState.value = RequestState.Success(it)
                    }
            }
        } catch (e: Exception) {
            _sortState.value = RequestState.Error(e)
        }
    }

    fun persistSortState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistSortState(priority = priority)
        }
    }

    private val _selectedTask: MutableStateFlow<TodoTask?> = MutableStateFlow(null)
    val selectedTask: StateFlow<TodoTask?> = _selectedTask

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            repository.getSelectedTask(taskId = taskId).collect { task ->
                _selectedTask.value = task
            }
        }
    }


    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val todoTask = TodoTask(
                title = title,
                description = description,
                priority = priority
            )

            repository.addTask(todoTask = todoTask)
        }
        searchAppBarState = SearchAppBarState.CLOSED
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val todoTask = TodoTask(
                id = id,
                title = title,
                description = description,
                priority = priority
            )

            repository.updateTask(todoTask = todoTask)
        }
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val todoTask = TodoTask(
                id = id,
                title = title,
                description = description,
                priority = priority
            )

            repository.deleteTask(todoTask = todoTask)
        }
    }

    private fun deleteAllTask() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTask()
        }
    }

    fun handleDatabaseAction(action: Action) {
        when (action) {

            Action.ADD -> {
                addTask()
            }

            Action.UPDATE -> {
                updateTask()
            }

            Action.DELETE -> {
                deleteTask()
            }

            Action.DELETE_ALL -> {
                deleteAllTask()
            }

            Action.UNDO -> {
                addTask()
            }

            else -> {

            }
        }
    }

    fun updateTaskFields(selectedTask: TodoTask?) {
        if (selectedTask != null) {
            id = selectedTask.id
            title = selectedTask.title
            description = selectedTask.description
            priority = selectedTask.priority
        } else {
            id = 0
            title = ""
            description = ""
            priority = Priority.LOW
        }
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length < MAX_TITLE_LENGTH) {
            title = newTitle
        }
    }

    fun updateDescription(newDescription: String) {
        description = newDescription
    }

    fun updatePriority(newPriority: Priority) {
        priority = newPriority
    }

    fun updateSearchAppBarState(newSearchAppBarState: SearchAppBarState) {
        searchAppBarState = newSearchAppBarState
    }

    fun updateSearchText(newText: String) {
        searchTextState = newText
    }

    fun validateFields(): Boolean {
        return title.isNotEmpty() && description.isNotEmpty()
    }

    fun updateAction(newAction: Action) {
        action = newAction
    }
}