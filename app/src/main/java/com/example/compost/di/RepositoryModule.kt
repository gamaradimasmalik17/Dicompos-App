package com.example.compost.di

import com.example.compost.data.repository.ControlRepository
import com.example.compost.data.repository.IndicatorRepository
import com.example.compost.data.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory {  UserRepository(get(), get(), androidContext()) }
    factory {  IndicatorRepository(get()) }
    factory {  ControlRepository(get()) }
}