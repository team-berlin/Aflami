package com.berlin.aflami.di

import com.berlin.repository.AuthenticationRepositoryImpl
import com.berlin.repository.MovieDetailsRepositoryImpl
import com.berlin.repository.MovieRepositoryImpl
import com.berlin.repository.TVShowRepositoryImpl
import com.berlin.repository.TvShowDetailsRepositoryImpl
import org.koin.dsl.module
import repository.AuthenticationRepository
import repository.MovieDetailsRepository
import repository.MovieRepository
import repository.TVShowDetailsRepository
import repository.TVShowRepository

val repositoryModule = module {
    single<MovieDetailsRepository> { MovieDetailsRepositoryImpl(get()) }
    single<TVShowDetailsRepository> { TvShowDetailsRepositoryImpl(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get(), get()) }
    single<TVShowRepository> { TVShowRepositoryImpl(get(), get()) }
}