package com.example.feature.canvas.impl.data.repository

import com.example.copixel.feature.canvas.api.repository.ArtRepository
import com.example.feature.canvas.impl.data.CopixelArtsApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class ArtRepositoryImpl(val artApi: CopixelArtsApi) : ArtRepository {
    override suspend fun create(image: File) {
        val fileBody: RequestBody = image.asRequestBody("image/*".toMediaTypeOrNull())
        artApi.create(fileBody)
    }
}