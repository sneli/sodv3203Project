package com.example.sodv3203project.ui.subscreens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sodv3203project.R
import com.example.sodv3203project.data.AppDatabase
import com.example.sodv3203project.data.user.User
import com.example.sodv3203project.ui.theme.kanit
import com.example.sodv3203project.ui.theme.winkle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object CurrentSession {
    var user: User? = null
}

@Composable
fun LoginPage(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context.applicationContext) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Welcome back!",
            fontFamily = winkle,
            fontSize = 46.sp,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        TextField(
            value = username,
            onValueChange = { username = it },
            label = {
                Text(
                    text = "Username",
                    fontFamily = kanit
                )
            },
            modifier = Modifier.padding(bottom = 16.dp)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(
                    text = "Password",
                    fontFamily = kanit
                )
            },
            visualTransformation = PasswordVisualTransformation()
        )


        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        // First, check if the username exists
                        val usernameFlow = db.userDao().getUserByUsername(username)

                        usernameFlow.collect { existingUser ->
                            withContext(Dispatchers.Main) {
                                if (existingUser == null) {
                                    error = "Account does not exist"
                                    return@withContext
                                } else {
                                    // Now check if the credentials match
                                    CoroutineScope(Dispatchers.IO).launch {
                                        db.userDao().getUserByCredentials(username, password).collect { user ->
                                            withContext(Dispatchers.Main) {
                                                if (user != null) {
                                                    CurrentSession.user = user

                                                    navController.navigate("home") {
                                                        popUpTo("login") { inclusive = true }
                                                    }
                                                } else {
                                                    error = "Incorrect password"
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        withContext(Dispatchers.Main) {
                            error = "Login failed: ${e.message}"
                        }
                    }
                }
            }
        ) {
            Text(
                text = "Login",
                fontFamily = kanit
            )
        }

        error?.let {
            Text(text = it, color = Color.Red)
        }

        TextButton(onClick = { navController.navigate("register") }) {
            Text(
                text = "Don't have an account? Register",
                fontFamily = kanit
            )
        }
    }
}