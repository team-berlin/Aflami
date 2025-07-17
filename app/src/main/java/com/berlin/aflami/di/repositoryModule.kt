package com.berlin.aflami.di

import com.berlin.repository.MovieDetailsRepositoryImpl
import com.berlin.repository.SearchRepositoryImpl
import com.berlin.repository.TvShowDetailsRepositoryImpl
import org.koin.dsl.module
import repository.MovieDetailsRepository
import repository.SearchRepository
import repository.TvShowDetailsRepository

val repositoryModule = module {
    single<SearchRepository> { SearchRepositoryImpl(get(), get()) }
    single<MovieDetailsRepository> { MovieDetailsRepositoryImpl(get()) }
    single<TvShowDetailsRepository> { TvShowDetailsRepositoryImpl(get()) }
    single<MovieDetailsRepository> { MovieDetailsRepositoryImpl(get()) }
}