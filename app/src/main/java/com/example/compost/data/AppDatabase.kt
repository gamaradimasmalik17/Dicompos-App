package com.example.compost.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.compost.data.dao.UserDao
import com.example.compost.data.entity.UserEntity
import com.example.compost.data.model.User

// Define entities and version number
@Database(entities = [(UserEntity::class)], version = 1)
abstract class AppDataBase :RoomDatabase() {
    abstract fun getUserDao() : UserDao
}