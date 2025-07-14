package com.berlin.aflami.di

import org.koin.dsl.module
import usecase.GetRecentHistoryUseCase
import usecase.SearchByActorNameUseCase
import usecase.GetSearchMoviesUseCase
import usecase.GetSearchTvShowsUseCase
import usecase.SaveRecentHistoryUseCase
import usecase.SearchByCountryUseCase

val useCaseModule = module {
    single { SearchByCountryUseCase(get()) }
    single { SearchByActorNameUseCase(get()) }
    single { GetSearchMoviesUseCase(get()) }
    single { GetSearchTvShowsUseCase(get()) }
    single { GetRecentHistoryUseCase(get()) }
    single { SaveRecentHistoryUseCase(get()) }

}