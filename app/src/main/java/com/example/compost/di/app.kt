package com.example.compost.di



import android.app.Application

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class CompostApp : Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CompostApp)
            androidLogger()
            modules(networkModule, remoteDataSourceModule, repositoryModule, viewModelModule,
                dataBaseModule)
        }
    }
}
