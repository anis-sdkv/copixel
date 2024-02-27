package com.example.core.network.di

import com.example.core.network.InMemoryCookieJar
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

//TODO в конфиги
const val BASE_URL = "http://192.168.0.104:5038/"

val networkModule = module {
    single<OkHttpClient> {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .cookieJar(InMemoryCookieJar)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    single<Retrofit> {
        val contentType = "application/json".toMediaType()
        val kotlinxConverterFactory = Json.asConverterFactory(contentType)

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(kotlinxConverterFactory)
            .build()
    }
}