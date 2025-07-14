package com.berlin.aflami.di

import org.koin.dsl.module
import usecase.GetMovieReviewUseCase
import usecase.SearchByActorNameUseCase
import usecase.GetSearchMoviesUseCase
import usecase.GetSearchTvShowsUseCase
import usecase.GetSeriesReviewUseCase
import usecase.SearchByCountryUseCase

val useCaseModule = module {
    single { SearchByCountryUseCase(get()) }
    single { SearchByActorNameUseCase(get()) }
    single { GetSearchMoviesUseCase(get()) }
    single { GetSearchTvShowsUseCase(get()) }
    single { GetMovieReviewUseCase(get()) }
    single { GetSeriesReviewUseCase(get()) }
}