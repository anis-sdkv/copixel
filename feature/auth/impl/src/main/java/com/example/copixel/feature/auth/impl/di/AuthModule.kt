package com.example.copixel.feature.auth.impl.di

import com.example.copixel.feature.auth.api.servise.AuthService
import com.example.copixel.feature.auth.api.usecase.LoginUserUsecase
import com.example.copixel.feature.auth.api.usecase.RegisterUserUsecase
import com.example.copixel.feature.auth.impl.data.remote.CopixelAuthApi
import com.example.copixel.feature.auth.impl.data.servise.AuthServiceImpl
import com.example.copixel.feature.auth.impl.presentation.login.LoginViewModel
import com.example.copixel.feature.auth.impl.presentation.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val authModule = module {
    single<CopixelAuthApi> { get<Retrofit>().create(CopixelAuthApi::class.java) }
    single<AuthService> { AuthServiceImpl(get()) }
    factory { LoginUserUsecase(get()) }
    factory { RegisterUserUsecase(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
}