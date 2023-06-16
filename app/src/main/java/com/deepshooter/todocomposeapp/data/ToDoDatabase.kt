package com.deepshooter.todocomposeapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deepshooter.todocomposeapp.data.model.TodoTask

@Database(entities = [TodoTask::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao

}