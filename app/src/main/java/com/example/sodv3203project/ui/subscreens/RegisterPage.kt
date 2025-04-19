package com.example.sodv3203project.ui.subscreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sodv3203project.data.AppDatabase
import com.example.sodv3203project.data.user.User
import com.example.sodv3203project.ui.theme.kanit
import com.example.sodv3203project.ui.theme.winkle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RegisterPage(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context.applicationContext) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Welcome!",
            fontFamily = winkle,
            fontSize = 46.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Text(
            text = "Register to get started!",
            fontFamily = winkle,
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        TextField(
            value = name,
            onValueChange = { name = it },
            label = {
                Text(
                    text = "Name",
                    fontFamily = kanit
                )
            },
            modifier = Modifier.padding(bottom = 16.dp)
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
            value = email,
            onValueChange = { email = it },
            label = {
                Text(
                    text = "Email",
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

        Button(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // Check if username already exists
                    val existingUser = db.userDao().getUserByUsername(username).firstOrNull()
                    if (existingUser != null) {
                        withContext(Dispatchers.Main) {
                            error = "Username already taken"
                        }
                        return@launch
                    }

                    // Register user
                    db.userDao().insertUser(
                        User(
                            name = name,
                            username = username,
                            email = email,
                            password = password
                        )
                    )

                    withContext(Dispatchers.Main) {
                        navController.navigate("home") {
                            popUpTo("register") { inclusive = true }
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        error = "Registration failed: ${e.message}"
                    }
                }
            }
        },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = "Register",
                fontFamily = kanit
            )
        }

        error?.let {
            Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
        }

        TextButton(onClick = { navController.navigate("login") }) {
            Text(
                text = "Already have an account? Login!",
                fontFamily = kanit
            )
        }
    }
}