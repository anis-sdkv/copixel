package com.example.copixel.feature.canvas.api.usecase

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.example.copixel.feature.canvas.api.repository.ArtRepository
import java.io.File


class CreateArtUseCase(private val artRepository: ArtRepository, private val context: Context) {
    suspend operator fun invoke(userId: String, bitmap: Bitmap): Unit = try {
        val file = bitmap.writeToFile(userId, context)
        artRepository.create(file)
    } catch (e: Exception) {
        Log.e("test", e.message.toString())
        Unit
    }

    private fun Bitmap.writeToFile(userId: String, context: Context): File {
        val artsDir = File(context.filesDir, "arts")
        if (!artsDir.exists()) artsDir.mkdir()
        val filename = "${userId}_${System.currentTimeMillis()}.png"
        val file = File(artsDir, filename)
        file.createNewFile()
        context.openFileOutput(filename, Context.MODE_PRIVATE).use {
            this.compress(Bitmap.CompressFormat.JPEG, 100, it.buffered())
        }
        return file
    }
}