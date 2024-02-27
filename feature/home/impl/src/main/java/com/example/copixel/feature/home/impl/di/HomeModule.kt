package com.example.copixel.feature.home.impl.di

import com.example.copixel.feature.home.impl.presentation.users.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { UsersViewModel() }
}