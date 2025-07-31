package com.berlin.aflami.di

import com.berlin.repository.AuthenticationRepositoryImpl
import com.berlin.repository.MovieDetailsRepositoryImpl
import com.berlin.repository.MovieRepositoryImpl
import com.berlin.repository.TvShowDetailsRepositoryImpl
import com.berlin.repository.WatchedMediaRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import repository.AuthenticationRepository
import repository.MovieDetailsRepository
import repository.MovieRepository


val repositoryModule = module {
    singleOf(::SearchRepositoryImpl) bind SearchRepository::class
    singleOf(::MovieDetailsRepositoryImpl) bind MovieDetailsRepository::class
    singleOf(::TvShowDetailsRepositoryImpl) bind TvShowDetailsRepository::class
    singleOf(::WatchedMediaRepositoryImpl) bind ContinueWatchingRepository::class
    singleOf(::MovieRepositoryImpl) bind MovieRepository::class
    singleOf(::AuthenticationRepositoryImpl) bind AuthenticationRepository::class
    singleOf(::HomeRepositoryImpl) bind HomeRepository::class
}