package com.example.copixel.feature.profile.impl.di

import com.example.copixel.feature.profile.impl.presentation.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    viewModel { ProfileViewModel() }
}