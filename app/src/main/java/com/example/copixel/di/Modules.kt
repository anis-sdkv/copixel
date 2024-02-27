package com.example.copixel.di

import com.example.copixel.feature.auth.impl.di.authModule
import com.example.copixel.feature.home.impl.di.homeModule
import com.example.copixel.feature.profile.impl.di.profileModule
import com.example.core.network.di.networkModule
import com.example.feature.canvas.impl.di.canvasModule
import org.koin.dsl.module

private val featureModules = listOf(authModule, canvasModule, homeModule, profileModule)

val appModules = module {
    includes(networkModule)
    includes(featureModules)
}