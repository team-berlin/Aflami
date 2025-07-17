package com.berlin.aflami.di

import org.koin.dsl.module
import usecase.GetMovieDetailsUseCase
import usecase.SearchByActorNameUseCase
import usecase.GetSearchMoviesUseCase
import usecase.GetSearchTvShowsUseCase
import usecase.GetTvShowDetailsUseCase
import usecase.SearchByCountryUseCase

val useCaseModule = module {
    single { SearchByCountryUseCase(get()) }
    single { SearchByActorNameUseCase(get()) }
    single { GetSearchMoviesUseCase(get()) }
    single { GetSearchTvShowsUseCase(get()) }
    single { GetMovieDetailsUseCase(get()) }
    single { GetTvShowDetailsUseCase(get()) }

}