package com.example.feature.canvas.impl.di

import com.example.copixel.feature.canvas.api.repository.ArtRepository
import com.example.copixel.feature.canvas.api.usecase.CreateArtUseCase
import com.example.feature.canvas.impl.data.CopixelArtsApi
import com.example.feature.canvas.impl.data.repository.ArtRepositoryImpl
import com.example.feature.canvas.impl.presentation.canvas.CanvasViewModel
import com.example.feature.canvas.impl.presentation.canvasparams.CanvasParamsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val canvasModule = module {
    single<CopixelArtsApi> { get<Retrofit>().create(CopixelArtsApi::class.java) }
    single<ArtRepository> { ArtRepositoryImpl(get()) }
    factory { CreateArtUseCase(get(), get()) }
    viewModel { CanvasParamsViewModel() }
    viewModel { CanvasViewModel(get()) }
}