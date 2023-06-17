package com.deepshooter.todocomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.deepshooter.todocomposeapp.navigations.SetUpNavigation
import com.deepshooter.todocomposeapp.ui.theme.ToDoComposeAppTheme
import com.deepshooter.todocomposeapp.ui.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoComposeAppTheme {
                navHostController = rememberNavController()
                SetUpNavigation(
                    navHostController = navHostController,
                    sharedViewModel = sharedViewModel)
            }
        }
    }
}

