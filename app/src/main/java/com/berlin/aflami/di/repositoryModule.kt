package com.berlin.aflami.di

import com.berlin.repository.AuthenticationRepositoryImpl
import com.berlin.repository.HomeRepositoryImpl
import com.berlin.repository.MovieDetailsRepositoryImpl
import com.berlin.repository.MovieRepositoryImpl
import com.berlin.repository.SearchRepositoryImpl
import com.berlin.repository.TvShowDetailsRepositoryImpl
import com.berlin.repository.WatchedMediaRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import repository.AuthenticationRepository
import repository.ContinueWatchingRepository
import repository.HomeRepository
import repository.MovieDetailsRepository
import repository.MovieRepository
import repository.SearchRepository
import repository.TvShowDetailsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSearchRepository(
        impl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    @Singleton
    abstract fun bindMovieDetailsRepository(
        impl: MovieDetailsRepositoryImpl
    ): MovieDetailsRepository

    @Binds
    @Singleton
    abstract fun bindTvShowDetailsRepository(
        impl: TvShowDetailsRepositoryImpl
    ): TvShowDetailsRepository

    @Binds
    @Singleton
    abstract fun bindContinueWatchingRepository(
        impl: WatchedMediaRepositoryImpl
    ): ContinueWatchingRepository

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        impl: MovieRepositoryImpl
    ): MovieRepository

    @Binds
    @Singleton
    abstract fun bindAuthenticationRepository(
        impl: AuthenticationRepositoryImpl
    ): AuthenticationRepository

    @Binds
    @Singleton
    abstract fun bindHomeRepository(
        impl: HomeRepositoryImpl
    ): HomeRepository
}