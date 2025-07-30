package com.berlin.aflami.di

import com.berlin.repository.AuthenticationRepositoryImpl
import com.berlin.repository.HomeRepositoryImpl
import com.berlin.repository.MovieDetailsRepositoryImpl
import com.berlin.repository.MovieRepositoryImpl
import com.berlin.repository.SearchRepositoryImpl
import com.berlin.repository.TvShowDetailsRepositoryImpl
import com.berlin.repository.WatchedMediaRepositoryImpl
import org.koin.dsl.module
import repository.AuthenticationRepository
import repository.MovieDetailsRepository
import repository.MovieRepository
import repository.SearchRepository
import repository.TvShowDetailsRepository
import repository.ContinueWatchingRepository
import repository.HomeRepository

val repositoryModule = module {
    single<SearchRepository> { SearchRepositoryImpl(get(), get(),get(),get()) }
    single<MovieDetailsRepository> { MovieDetailsRepositoryImpl(get()) }
    single<TvShowDetailsRepository> { TvShowDetailsRepositoryImpl(get()) }
    single <ContinueWatchingRepository>{ WatchedMediaRepositoryImpl(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get()) }
    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get(),get()) }
    single<HomeRepository> { HomeRepositoryImpl(get()) }
}