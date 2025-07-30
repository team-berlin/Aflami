package com.berlin.repository

import android.util.Log
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import kotlinx.coroutines.tasks.await
import repository.ModelRepository
import java.io.File
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class ModelRepositoryImpl(
    private val modelDownloader: FirebaseModelDownloader
): ModelRepository {


    private val models = mutableMapOf<String, MappedByteBuffer>()
    private var isDownloaded = false

    override suspend fun downloadAllModelsOnce(): Result<Unit> {
        if (isDownloaded) return Result.success(Unit)

        return try {
            models["nsfw"] = loadModel("nsfw")
            models["gender_not_quantized"] = loadModel("gender_not_quantized")
            isDownloaded = true
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun getModel(name: String): MappedByteBuffer {
        return models[name]
            ?: throw IllegalStateException("Model '$name' not downloaded yet.")
    }
    override suspend fun loadModel(name: String): MappedByteBuffer {
        val downloader = FirebaseModelDownloader.getInstance()
        val model = downloader.getModel(
            name,
            DownloadType.LOCAL_MODEL,
            CustomModelDownloadConditions.Builder().requireWifi().build()
        ).await()

        val file = model.file ?: throw IllegalStateException("Model file is null")
        return FileInputStream(file).channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length())
    }
}

