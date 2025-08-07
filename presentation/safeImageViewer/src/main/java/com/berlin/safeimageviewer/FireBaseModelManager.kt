package com.berlin.safeimageviewer

import android.util.Log
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import javax.inject.Inject


class FireBaseModelManager @Inject constructor(
    private val networkConnectivityObserver: NetworkConnectivityObserver,
) {
    val models = mutableMapOf<String, MappedByteBuffer>()
    private val _isModelDownloaded = MutableStateFlow(false)
    val isModelDownloaded: StateFlow<Boolean> = _isModelDownloaded.asStateFlow()

    internal var nsfwInterpreter: Interpreter? = null
    internal var genderInterpreter: Interpreter? = null
    suspend fun downloadModelsOnce() {
        networkConnectivityObserver.observe().first { it == NetworkStatus.Available }
        withContext(Dispatchers.IO) {
            try {
                coroutineScope {
                    val nsfwJob = async {
                        models[NSFW_MODEL] = loadFirebaseModel(NSFW_MODEL)
                    }

                    val genderModelJob = async {
                        models[GENDER_MODEL] = loadFirebaseModel(GENDER_MODEL)
                    }

                    nsfwJob.await()
                    genderModelJob.await()

                    nsfwInterpreter = Interpreter(getModel(NSFW_MODEL), Interpreter.Options())
                    genderInterpreter = Interpreter(getModel(GENDER_MODEL), Interpreter.Options())

                    _isModelDownloaded.value = true
                }

            } catch (e: Exception) {
                Log.e("FireBaseModelManager", "Error downloading models: ${e.message}", e)
            }
        }
    }

    fun getModel(name: String): MappedByteBuffer {
        return models[name]
            ?: throw IllegalStateException("Model $name not found. Please download models first.")
    }

    suspend fun loadFirebaseModel(name: String): MappedByteBuffer {
        val downloader = FirebaseModelDownloader.getInstance()
        val model = downloader.getModel(
            name, DownloadType.LOCAL_MODEL, CustomModelDownloadConditions.Builder().build()
        ).await()

        val file = model.file ?: throw IllegalStateException("Model not downloaded")
        return FileInputStream(file).channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length())
    }

}

const val NSFW_MODEL = "nsfw"
const val GENDER_MODEL = "gender_not_quantized"
