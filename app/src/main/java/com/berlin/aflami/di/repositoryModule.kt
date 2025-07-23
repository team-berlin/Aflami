package com.berlin.aflami.di

import com.berlin.repository.MovieDetailsRepositoryImpl
import com.berlin.repository.SearchRepositoryImpl
import com.berlin.repository.TvShowDetailsRepositoryImpl
import com.berlin.repository.WatchedMediaRepositoryImpl
import org.koin.dsl.module
import repository.MovieDetailsRepository
import repository.SearchRepository
import repository.TvShowDetailsRepository
import repository.WatchedMediaRepository

val repositoryModule = module {
    single<SearchRepository> { SearchRepositoryImpl(get(), get(),get(),get()) }
    single<MovieDetailsRepository> { MovieDetailsRepositoryImpl(get()) }
    single<TvShowDetailsRepository> { TvShowDetailsRepositoryImpl(get()) }
    single <WatchedMediaRepository>{ WatchedMediaRepositoryImpl(get()) }
}