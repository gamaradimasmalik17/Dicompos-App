package com.example.compost.di

import android.app.Application
import androidx.room.Room
import com.example.compost.data.AppDataBase

import com.example.compost.data.dao.UserDao
import org.koin.dsl.module

fun provideDataBase(application: Application): AppDataBase =
    Room.databaseBuilder(
        application,
        AppDataBase::class.java,
        "CompostDB"
    ).
    fallbackToDestructiveMigration().build()

fun provideDao(appDataBase: AppDataBase): UserDao = appDataBase.getUserDao()


val dataBaseModule= module {
    single { provideDataBase(get()) }
    single { provideDao(get()) }
}