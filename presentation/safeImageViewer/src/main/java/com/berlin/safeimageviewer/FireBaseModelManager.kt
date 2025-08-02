package com.berlin.safeimageviewer

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import kotlinx.coroutines.tasks.await
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import androidx.core.content.edit
import javax.inject.Inject


class FireBaseModelManager @Inject constructor(
    val prefs : SharedPreferences
) {
         val models = mutableMapOf<String, MappedByteBuffer>()
         val isModelDownloaded = mutableStateOf(false)

        suspend fun downloadModelsOnce() {
            while (!isModelDownloaded.value) {
                try {
                    models[NSFW_MODEL] = loadFirebaseModel(NSFW_MODEL)
                    models[GENDER_MODEL] = loadFirebaseModel(GENDER_MODEL)
                    prefs.edit(commit = true) {
                        putBoolean(IS_MODEL_DOWNLOADED, true)
                        isModelDownloaded.value=true
                    }
                } catch (e: Exception) {
                    Log.e("FireBaseModelManager", "Error downloading models: ${e.message}")
                }
            }
        }

        fun getModel(name: String): MappedByteBuffer {
            return models[name]?:
                 throw IllegalStateException("Model $name not found. Please download models first.")
        }

         suspend fun loadFirebaseModel(name: String): MappedByteBuffer {
            val downloader = FirebaseModelDownloader.getInstance()
            val model = downloader.getModel(
                name,
                DownloadType.LOCAL_MODEL,
                CustomModelDownloadConditions
                    .Builder()
                    .requireWifi()
                    .build()
            ).await()

            val file = model.file ?: throw IllegalStateException("Model not downloaded")
            return FileInputStream(file).channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length())
        }
    }
const val NSFW_MODEL= "nsfw"
const val GENDER_MODEL = "gender_not_quantized"
const val IS_MODEL_DOWNLOADED = "models_downloaded"