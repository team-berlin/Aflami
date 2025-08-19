package com.berlin.aflami.di

import com.berlin.local.dataStore.SettingsLocalDataSourceImpl
import com.berlin.local.datasource.AppEntryLocalDataSourceImpl
import com.berlin.local.datasource.AuthenticationLocalDataSourceImpl
import com.berlin.local.datasource.CategoriesPreferencesDataSourceImpl
import com.berlin.local.datasource.GameLocalDataSourceImpl
import com.berlin.local.datasource.GenreLocalDataSourceImpl
import com.berlin.local.datasource.HomeLocalDataSourceImp
import com.berlin.local.datasource.RecentHistoryLocalDataSourceImpl
import com.berlin.local.datasource.RecentlyWatchedLocalDataSourceImpl
import com.berlin.local.datasource.UserProfileLocalDataSourceImp
import com.berlin.remote.AuthenticationRemoteDataSourceImpl
import com.berlin.remote.RetrofitRemoteDataSource
import com.berlin.remote.UserRemoteDataSourceImpl
import com.berlin.repository.datasource.local.datasource.AppEntryLocalDataSource
import com.berlin.repository.datasource.local.datasource.AuthenticationLocalDataSource
import com.berlin.repository.datasource.local.datasource.CategoriesPreferencesDataSource
import com.berlin.repository.datasource.local.datasource.GameLocalDataSource
import com.berlin.repository.datasource.local.datasource.GenreLocalDataSource
import com.berlin.repository.datasource.local.datasource.HomeLocalDataSource
import com.berlin.repository.datasource.local.datasource.RecentHistoryLocalDataSource
import com.berlin.repository.datasource.local.datasource.RecentlyWatchedLocalDataSource
import com.berlin.repository.datasource.local.datasource.UserProfileLocalDataSource
import com.berlin.repository.datasource.local.dataStore.SettingsLocalDataSource
import com.berlin.repository.datasource.remote.AuthenticationRemoteDataSource
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.datasource.remote.UserRemoteDataSource
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
    abstract fun bindRecentHistoryLocalDataSource(
        impl: RecentHistoryLocalDataSourceImpl,
    ): RecentHistoryLocalDataSource

    @Binds
    @Singleton
    abstract fun bindCategoriesPreferencesDataSource(
        impl: CategoriesPreferencesDataSourceImpl,
    ): CategoriesPreferencesDataSource

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(
        impl: RetrofitRemoteDataSource,
    ): RemoteDataSource

    @Binds
    @Singleton
    abstract fun bindAuthenticationRemoteDataSource(
        impl: AuthenticationRemoteDataSourceImpl,
    ): AuthenticationRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindAuthenticationLocalDataSource(
        impl: AuthenticationLocalDataSourceImpl,
    ): AuthenticationLocalDataSource

    @Binds
    @Singleton
    abstract fun bindContinueWatchingLocalDataSource(
        impl: RecentlyWatchedLocalDataSourceImpl,
    ): RecentlyWatchedLocalDataSource

    @Binds
    @Singleton
    abstract fun bindGenreLocalDataSource(
        impl: GenreLocalDataSourceImpl,
    ): GenreLocalDataSource

    @Binds
    @Singleton
    abstract fun bindUserLocalDataSource(
        impl: UserProfileLocalDataSourceImp
    ): UserProfileLocalDataSource

    @Binds
    @Singleton
    abstract fun bindUserRemoteDataSource(
        impl: UserRemoteDataSourceImpl
    ): UserRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindAppEntryDataSource(
        impl: AppEntryLocalDataSourceImpl,
    ): AppEntryLocalDataSource

    @Binds
    @Singleton
    abstract fun bindHomeLocalDataSource(
        impl: HomeLocalDataSourceImp,
    ): HomeLocalDataSource

    @Binds
    @Singleton
    abstract fun bindSettingsLocalDataSource(
        impl: SettingsLocalDataSourceImpl
    ): SettingsLocalDataSource

    @Binds
    @Singleton
    abstract fun bindGameLocalDataSource(
        impl: GameLocalDataSourceImpl
    ): GameLocalDataSource
}
