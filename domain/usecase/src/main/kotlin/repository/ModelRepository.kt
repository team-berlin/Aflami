package repository

import java.io.File
import java.nio.MappedByteBuffer

interface ModelRepository {
     suspend fun loadModel(name: String): MappedByteBuffer
     suspend fun downloadAllModelsOnce(): Result<Unit>
}