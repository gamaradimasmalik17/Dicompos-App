package com.example.compost.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.compost.data.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE username = :username ")
    suspend fun getUserByUsername(username: String): UserEntity?

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getCurrentUser(username: String): UserEntity?

    // Menambahkan metode untuk memilih email berdasarkan username
    @Query("SELECT email FROM users WHERE username = :username")
    suspend fun getEmailByUsername(username: String): String?
}
