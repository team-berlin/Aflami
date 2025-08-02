package com.berlin.aflami.di

import androidx.lifecycle.SavedStateHandle
import com.berlin.aflami.viewmodel.CastDetailsArgs
import com.berlin.aflami.viewmodel.MediaDetailsArgs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModel {
    @Provides
    fun mediaDetailsArgs(savedStateHandle: SavedStateHandle): MediaDetailsArgs {
        return MediaDetailsArgs(savedStateHandle)
    }

    @Provides
    fun castDetailsArgs(savedStateHandle: SavedStateHandle): CastDetailsArgs {
        return CastDetailsArgs(savedStateHandle)
    }
}