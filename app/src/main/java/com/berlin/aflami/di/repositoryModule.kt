package com.berlin.aflami.di

import com.berlin.repository.MovieDetailsRepositoryImpl
import com.berlin.repository.MovieRepositoryImpl
import com.berlin.repository.MovieRepositoryImpl
import com.berlin.repository.SearchRepositoryImpl
import com.berlin.repository.TvShowDetailsRepositoryImpl
import com.berlin.repository.WatchedMediaRepositoryImpl
import org.koin.dsl.module
import repository.MovieDetailsRepository
import repository.MovieRepository
import repository.MovieRepository
import repository.SearchRepository
import repository.TvShowDetailsRepository
import repository.ContinueWatchingRepository

val repositoryModule = module {
    single<MovieDetailsRepository> { MovieDetailsRepositoryImpl(get()) }
    single<TvShowDetailsRepository> { TvShowDetailsRepositoryImpl(get()) }
    single <ContinueWatchingRepository>{ WatchedMediaRepositoryImpl(get()) }
    single<SearchRepository> { SearchRepositoryImpl(get(), get(), get(), get()) }
    single<MovieRepository> { MovieRepositoryImpl(get()) }
}