package com.example.sodv3203project.data.user

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserByUsername(username: String): Flow<User?>

    fun getUserByCredentials(username: String, password: String): Flow<User?>

    suspend fun insertUser(user: User)
}