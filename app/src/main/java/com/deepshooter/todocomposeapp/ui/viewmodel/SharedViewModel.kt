package com.deepshooter.todocomposeapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepshooter.todocomposeapp.data.model.TodoTask
import com.deepshooter.todocomposeapp.data.repositories.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: ToDoRepository
) : ViewModel() {

    private val _allTask = MutableStateFlow<List<TodoTask>>(emptyList())
    val allTask: StateFlow<List<TodoTask>> = _allTask

    fun getAllTasks() {
        viewModelScope.launch {
            repository.getAllTasks.collect {
                _allTask.value = it
            }
        }
    }

}