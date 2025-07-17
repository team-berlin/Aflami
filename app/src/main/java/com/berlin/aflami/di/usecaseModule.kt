package com.berlin.aflami.di

import org.koin.dsl.module
import usecase.GetMovieDetailsUseCase
import usecase.GetMovieGalleryUseCase
import usecase.SearchByActorNameUseCase
import usecase.GetMovieCastUseCase
import usecase.GetSearchMoviesUseCase
import usecase.GetSearchTvShowsUseCase
import usecase.GetSimilarMoviesUseCase
import usecase.GetSimilarSeriesUseCase
import usecase.GetTvShowDetailsUseCase
import usecase.GetSeriesGalleryUseCase
import usecase.GetSeriesCastUseCase
import usecase.SearchByCountryUseCase

val useCaseModule = module {
    single { SearchByCountryUseCase(get()) }
    single { SearchByActorNameUseCase(get()) }
    single { GetSearchMoviesUseCase(get()) }
    single { GetSearchTvShowsUseCase(get()) }
    single { GetSimilarMoviesUseCase(get()) }
    single { GetSimilarSeriesUseCase(get()) }
    single { GetMovieDetailsUseCase(get()) }
    single { GetTvShowDetailsUseCase(get()) }

    single { GetMovieGalleryUseCase(get()) }
    single { GetSeriesGalleryUseCase(get()) }
    single { GetMovieCastUseCase(get()) }
    single { GetSeriesCastUseCase(get()) }

}