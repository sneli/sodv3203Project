package com.example.sodv3203project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sodv3203project.data.AppDatabase
import com.example.sodv3203project.ui.subscreens.CurrentSession
import com.example.sodv3203project.ui.subscreens.LoginPage
import com.example.sodv3203project.ui.subscreens.RegisterPage
import com.example.sodv3203project.ui.theme.Sodv3203ProjectTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val db = remember { AppDatabase.getDatabase(context) }

            val repository = (context.applicationContext as RecipeApplication).repository

            LaunchedEffect(Unit) {
                try {
                    val users = db.userDao().getAllUsers()
                    android.util.Log.d("DBCheck", "DB is open? ${db.openHelper.writableDatabase.isOpen}")
                } catch (e: Exception) {
                    android.util.Log.e("DBCheck", "Error accessing DB: ${e.message}")
                }
            }
            Sodv3203ProjectTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    composable("login") {
                        LoginPage(navController)
                    }
                    composable("register") {
                        RegisterPage(navController)
                    }

                    composable("home") {
                        CurrentSession.user?.let { it1 -> RecipeScreen(repository = repository, user = it1) }
                    }
                }
            }
        }
    }
}