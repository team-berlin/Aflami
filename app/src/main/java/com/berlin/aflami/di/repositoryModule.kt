package com.berlin.aflami.di

import com.berlin.repository.AuthenticationRepositoryImpl
import com.berlin.repository.MovieDetailsRepositoryImpl
import com.berlin.repository.MovieRepositoryImpl
import com.berlin.repository.RatedMediaRepositoryImp
import com.berlin.repository.RatingRepositoryImpl
import com.berlin.repository.SettingsRepositoryImpl
import com.berlin.repository.TVShowRepositoryImpl
import com.berlin.repository.TvShowDetailsRepositoryImpl
import com.berlin.repository.UserFavouriteListRepositoryImpl
import com.berlin.repository.UserProfileRepositoryImpl
import com.berlin.repository.datasource.AppEntryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import repository.AppEntryRepository
import repository.AuthenticationRepository
import repository.MovieDetailsRepository
import repository.MovieRepository
import repository.RatedMediaRepository
import repository.RatingRepository
import repository.SettingsRepository
import repository.TVShowDetailsRepository
import repository.TVShowRepository
import repository.UserFavouriteListRepository
import repository.UserProfileRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    @Singleton
    abstract fun bindMovieDetailsRepository(
        impl: MovieDetailsRepositoryImpl,
    ): MovieDetailsRepository

    @Binds
    @Singleton
    abstract fun bindTvShowDetailsRepository(
        impl: TvShowDetailsRepositoryImpl,
    ): TVShowDetailsRepository


    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        impl: MovieRepositoryImpl,
    ): MovieRepository

    @Binds
    @Singleton
    abstract fun bindAuthenticationRepository(
        impl: AuthenticationRepositoryImpl,
    ): AuthenticationRepository

    @Binds
    @Singleton
    abstract fun bindTvShowRepository(
        impl: TVShowRepositoryImpl,
    ): TVShowRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        impl: UserProfileRepositoryImpl
    ): UserProfileRepository

    @Binds
    @Singleton
    abstract fun bindAppEntryRepository(
        impl: AppEntryRepositoryImpl,
    ): AppEntryRepository

    @Binds
    @Singleton
    abstract fun bindUserFavouriteListRepository(
        impl: UserFavouriteListRepositoryImpl,
    ): UserFavouriteListRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        impl: SettingsRepositoryImpl
    ): SettingsRepository

    @Binds
    @Singleton
    abstract fun bindRatingRepository(
        impl: RatingRepositoryImpl
    ): RatingRepository

    @Binds
    @Singleton
    abstract fun bindRatedMediaRepository(
        impl: RatedMediaRepositoryImp
    ): RatedMediaRepository


}