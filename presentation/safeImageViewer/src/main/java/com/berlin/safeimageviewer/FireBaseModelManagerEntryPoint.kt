package com.berlin.safeimageviewer
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@EntryPoint
@Singleton
@InstallIn(SingletonComponent::class)
interface FireBaseModelManagerEntryPoint {
    fun modelManager(): FireBaseModelManager
}