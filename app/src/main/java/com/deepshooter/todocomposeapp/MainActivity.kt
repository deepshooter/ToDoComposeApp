package com.deepshooter.todocomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.deepshooter.todocomposeapp.navigations.SetUpNavigation
import com.deepshooter.todocomposeapp.ui.theme.ToDoComposeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoComposeAppTheme {
                navHostController = rememberNavController()
                SetUpNavigation(navHostController = navHostController)
            }
        }
    }
}

