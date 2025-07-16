package com.berlin.aflami.di

import com.berlin.repository.MovieDetailsRepositoryImpl
import com.berlin.repository.SearchRepositoryImpl
import com.berlin.repository.SeriesDetailsRepositoryImpl
import org.koin.dsl.module
import repository.MovieDetailsRepository
import repository.SearchRepository
import repository.SeriesDetailsRepository

val repositoryModule = module {
    single<SearchRepository> { SearchRepositoryImpl(get(), get()) }
    single<MovieDetailsRepository> { MovieDetailsRepositoryImpl(get()) }
    single<SeriesDetailsRepository> { SeriesDetailsRepositoryImpl(get()) }
}