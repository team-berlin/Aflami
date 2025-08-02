package com.berlin.aflami.di

import android.content.Context
import com.berlin.safeimageviewer.FireBaseModelManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MlModule {

    @Provides
    @Singleton
    fun provideFireBaseModelManager(
        @ApplicationContext context: Context
    ): FireBaseModelManager {
        val sharedPreferences = context.getSharedPreferences("ml_prefs", Context.MODE_PRIVATE)
        return FireBaseModelManager(prefs = sharedPreferences)
    }

}