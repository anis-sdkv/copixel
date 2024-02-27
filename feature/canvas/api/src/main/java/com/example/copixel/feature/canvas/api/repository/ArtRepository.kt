package com.example.copixel.feature.canvas.api.repository

import java.io.File

interface ArtRepository {
    suspend fun create(image: File)
}