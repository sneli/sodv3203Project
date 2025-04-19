package com.example.sodv3203project.ui.subscreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sodv3203project.data.user.User
import com.example.sodv3203project.data.user.UserDao
import com.example.sodv3203project.ui.theme.kanit
import com.example.sodv3203project.ui.theme.winkle
import kotlinx.coroutines.launch

@Composable
fun EditProfile(
    user: User,
    navController: NavController,
    userDao: UserDao
){
    val coroutineScope = rememberCoroutineScope()

    var name by remember { mutableStateOf(user.name) }
    var username by remember { mutableStateOf(user.username) }
    var email by remember { mutableStateOf(user.email) }

    var showPasswordFields by remember { mutableStateOf(false) }
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var oldPasswordError by remember { mutableStateOf<String?>(null) }
    var newPasswordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Edit Profile",
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 30.sp,
            fontFamily = winkle
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = {
                Text(
                    text = "Name",
                    fontFamily = kanit
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = {
                Text(
                    text = "Username",
                    fontFamily = kanit
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text(
                    text = "Email",
                    fontFamily = kanit
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        TextButton(onClick = { showPasswordFields = !showPasswordFields }) {
            Text(
                text = "Change Password",
                fontFamily = kanit
            )
        }

        if (showPasswordFields) {
            OutlinedTextField(
                value = oldPassword,
                onValueChange = {
                    oldPassword = it
                    oldPasswordError = null
                },
                label = {
                    Text(
                        text = "Old Password",
                        fontFamily = kanit
                    )
                },
                isError = oldPasswordError != null,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            if (oldPasswordError != null) Text(oldPasswordError!!, color = MaterialTheme.colorScheme.error)

            OutlinedTextField(
                value = newPassword,
                onValueChange = {
                    newPassword = it
                    newPasswordError = null
                },
                label = {
                    Text(
                        text = "New Password",
                        fontFamily = kanit
                    )
                },
                isError = newPasswordError != null,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            if (newPasswordError != null) Text(newPasswordError!!, color = MaterialTheme.colorScheme.error)

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    confirmPasswordError = null
                },
                label = {
                    Text(
                        text = "Confirm New Password",
                        fontFamily = kanit
                    )
                },
                isError = confirmPasswordError != null,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            if (confirmPasswordError != null) Text(confirmPasswordError!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { navController.navigate("profile") }) {
                Text(
                    text = "Cancel",
                    fontFamily = kanit
                )
            }

            Button(onClick = {
                var hasError = false

                if (showPasswordFields) {
                    if (oldPassword != user.password) {
                        oldPasswordError = "Old password is incorrect"
                        hasError = true
                    }

                    if (newPassword == user.password) {
                        newPasswordError = "New password must be different from old password"
                        hasError = true
                    }

                    if (newPassword != confirmPassword) {
                        confirmPasswordError = "Passwords do not match"
                        hasError = true
                    }
                }

                if (!hasError) {
                    coroutineScope.launch {
                        val updatedUser = user.copy(
                            name = name,
                            username = username,
                            email = email,
                            password = if (showPasswordFields) newPassword else user.password
                        )
                        userDao.insertUser(updatedUser)
                        CurrentSession.user = updatedUser
                        navController.navigate("profile")
                    }
                }
            }) {
                Text(
                    text = "Save",
                    fontFamily = kanit
                )
            }
        }
    }
}