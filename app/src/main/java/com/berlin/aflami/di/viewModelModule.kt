package com.berlin.aflami.di

import androidx.lifecycle.SavedStateHandle
import com.berlin.aflami.viewmodel.categories.movie.MediaByCategoryArgs
import com.berlin.aflami.viewmodel.details.cast.CastDetailsArgs
import com.berlin.aflami.viewmodel.details.movie.MovieDetailsArgs
import com.berlin.aflami.viewmodel.details.series.TVShowDetailsArgs
import com.berlin.aflami.viewmodel.list.FavouriteListArgs
import com.berlin.aflami.viewmodel.listDetails.FavouriteListDetailsArgs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModel {
    @Provides
    fun movieDetailsArgs(savedStateHandle: SavedStateHandle): MovieDetailsArgs {
        return MovieDetailsArgs(savedStateHandle)
    }

    @Provides
    fun tvShowDetailsArgs(savedStateHandle: SavedStateHandle): TVShowDetailsArgs {
        return TVShowDetailsArgs(savedStateHandle)
    }

    @Provides
    fun castDetailsArgs(savedStateHandle: SavedStateHandle): CastDetailsArgs {
        return CastDetailsArgs(savedStateHandle)
    }

    @Provides
    fun provideFavouriteListDetailsArgs(savedStateHandle: SavedStateHandle): FavouriteListDetailsArgs {
        return FavouriteListDetailsArgs(savedStateHandle)
    }

    @Provides
    fun provideFavouriteListArgs(savedStateHandle: SavedStateHandle): FavouriteListArgs {
        return FavouriteListArgs(savedStateHandle)
    }
    @Provides
    fun mediaByCategoryArgs(savedStateHandle: SavedStateHandle): MediaByCategoryArgs
    {        return MediaByCategoryArgs(savedStateHandle)
    }
}