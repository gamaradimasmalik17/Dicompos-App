package com.example.compost.di

import com.example.compost.viewModel.BottomBarViewModel
import com.example.compost.viewModel.IndicatorViewModel
import com.example.compost.viewModel.LoginViewModel
import com.example.compost.viewModel.RegisterViewModel
import com.example.compost.viewModel.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { UserViewModel(get()) }
    viewModel { IndicatorViewModel(get(), get(), androidContext()) } // Pass the Context here
    viewModel { BottomBarViewModel(get()) }
}