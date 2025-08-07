package com.berlin.aflami.di

import android.content.Context
import com.berlin.safeimageviewer.NetworkConnectivityObserver
import com.berlin.safeimageviewer.FireBaseModelManager
import com.berlin.safeimageviewer.NetworkNetworkConnectivityObserverImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MlModule {

    @Binds
    @Singleton
    abstract fun provideNetworkConnectivityObserver(
     imbl: NetworkNetworkConnectivityObserverImpl
    ): NetworkConnectivityObserver
}
@Module
@InstallIn(SingletonComponent::class)
object ModelManagerModule {

    @Provides
    @Singleton
    fun provideModelManager(
        networkConnectivityObserver: NetworkConnectivityObserver
    ): FireBaseModelManager {
        return FireBaseModelManager(networkConnectivityObserver)
    }
}