package com.berlin.safeimageviewer
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
@EntryPoint
@InstallIn(SingletonComponent::class)
interface FireBaseModelManagerEntryPoint {
    fun modelManager(): FireBaseModelManager
}