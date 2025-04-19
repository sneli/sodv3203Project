package com.example.sodv3203project.data.user

import kotlinx.coroutines.flow.Flow

class OfflineUserRepository(private val userDao: UserDao) : UserRepository {
    override fun getUserByUsername(username: String): Flow<User?> = userDao.getUserByUsername(username)

    override fun getUserByCredentials(username: String, password: String): Flow<User?> = userDao.getUserByCredentials(username, password)

    override suspend fun insertUser(user: User) = userDao.insertUser(user)
}