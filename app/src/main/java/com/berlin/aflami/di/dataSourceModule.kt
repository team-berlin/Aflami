package com.berlin.aflami.di

import com.berlin.local.datasource.AuthenticationLocalDataSourceImp
import com.berlin.local.datasource.CategoriesPreferencesDataSourceImpl
import com.berlin.local.datasource.GenreLocalDataSourceImpl
import com.berlin.local.datasource.RecentHistoryLocalDataSourceImpl
import com.berlin.local.datasource.RecentlyWatchedLocalDataSourceImpl
import com.berlin.local.datasource.SearchLocalDataSourceImpl
import com.berlin.remote.AuthenticationRemoteDataSourceImpl
import com.berlin.remote.RetrofitRemoteDataSource
import com.berlin.repository.datasource.local.AuthenticationLocalDataSource
import com.berlin.repository.datasource.local.CategoriesPreferencesDataSource
import com.berlin.repository.datasource.local.GenreLocalDataSource
import com.berlin.repository.datasource.local.RecentHistoryLocalDataSource
import com.berlin.repository.datasource.local.RecentlyWatchedLocalDataSource
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.remote.AuthenticationRemoteDataSource
import com.berlin.repository.datasource.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindSearchLocalDataSource(
        impl: SearchLocalDataSourceImpl
    ): SearchLocalDataSource

    @Binds
    @Singleton
    abstract fun bindRecentHistoryLocalDataSource(
        impl: RecentHistoryLocalDataSourceImpl
    ): RecentHistoryLocalDataSource

    @Binds
    @Singleton
    abstract fun bindCategoriesPreferencesDataSource(
        impl: CategoriesPreferencesDataSourceImpl
    ): CategoriesPreferencesDataSource

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(
        impl: RetrofitRemoteDataSource
    ): RemoteDataSource

    @Binds
    @Singleton
    abstract fun bindAuthenticationRemoteDataSource(
        impl: AuthenticationRemoteDataSourceImpl
    ): AuthenticationRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindAuthenticationLocalDataSource(
        impl: AuthenticationLocalDataSourceImp
    ): AuthenticationLocalDataSource

    @Binds
    @Singleton
    abstract fun bindContinueWatchingLocalDataSource(
        impl: RecentlyWatchedLocalDataSourceImpl
    ): RecentlyWatchedLocalDataSource

    @Binds
    @Singleton
    abstract fun bindGenreLocalDataSource(
        impl: GenreLocalDataSourceImpl
    ): GenreLocalDataSource

    @Binds
    @Singleton
    abstract fun bindAppEntryDataSource(
        impl: AppEntryLocalDataSourceImpl
    ): AppEntryLocalDataSource

    @Binds
    @Singleton
    abstract fun bindHomeLocalDataSource(
        impl: HomeLocalDataSourceImpl
    ): HomeLocalDataSource
}