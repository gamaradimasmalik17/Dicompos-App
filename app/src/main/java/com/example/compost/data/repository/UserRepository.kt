package com.example.compost.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.compost.data.dao.UserDao
import com.example.compost.data.entity.UserEntity
import com.example.compost.data.model.User
import com.example.compost.data.remote.RemoteDataSource
import com.example.compost.data.response.LoginResponse
import com.example.compost.data.response.RegisterResponse

class UserRepository(private val userDao: UserDao,private val remoteDataSource: RemoteDataSource, context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    suspend fun registerUser(user: User) {
        val userEntity = UserEntity(
            fullName = user.fullName,
            email = user.email,
            username = user.username,
            password = user.password
        )

        userDao.insertUser(userEntity)
    }

    suspend fun registerAccount(email: String, username: String, password: String): RegisterResponse {

        return remoteDataSource.registerUser(username, email, password)
    }
    suspend fun authenticate(username: String, password: String): Boolean {

        val user = userDao.getUserByUsername(username)


        return user != null && user.password == password
    }

    suspend fun getCurrentUser(username: String): UserEntity? {

        val currentUserEntity = userDao.getCurrentUser(username)


        return currentUserEntity

    }

    suspend fun login(email: String, password: String): LoginResponse {


        return remoteDataSource.loginUser(email,password)
    }

    fun logout() {
        sharedPreferences.edit {
            remove("username")
            remove("email")
            putBoolean("is_logged_in", false)
        }
    }

}



