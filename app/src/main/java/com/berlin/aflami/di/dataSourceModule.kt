package com.berlin.aflami.di

import com.berlin.local.dao.CategoriesPreferencesDao
import com.berlin.local.dao.RecentHistoryDao
import com.berlin.local.dao.SearchDao
import com.berlin.local.datasource.AuthenticationLocalDataSourceImp
import com.berlin.local.datasource.CategoriesPreferencesDataSourceImpl
import com.berlin.local.datasource.ContinueWatchingLocalDataSourceImpl
import com.berlin.local.datasource.RecentHistoryLocalDataSourceImpl
import com.berlin.local.datasource.SearchLocalDataSourceImpl
import com.berlin.remote.AuthenticationRemoteDataSourceImpl
import com.berlin.remote.DataSourceImpl
import com.berlin.repository.datasource.local.AuthenticationLocalDataSource
import com.berlin.repository.datasource.local.CategoriesPreferencesDataSource
import com.berlin.repository.datasource.local.RecentHistoryLocalDataSource
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.remote.AuthenticationRemoteDataSource
import com.berlin.repository.datasource.local.ContinueWatchingLocalDataSource
import com.berlin.repository.datasource.remote.RemoteDataSource

import org.koin.dsl.module

val dataSourceModule = module {
    single<SearchLocalDataSource> { SearchLocalDataSourceImpl(get<SearchDao>()) }
    single<RecentHistoryLocalDataSource> { RecentHistoryLocalDataSourceImpl(get<RecentHistoryDao>()) }
    single<CategoriesPreferencesDataSource> { CategoriesPreferencesDataSourceImpl(get<CategoriesPreferencesDao>()) }
    single<RemoteDataSource> { DataSourceImpl(get()) }
    single<AuthenticationRemoteDataSource> { AuthenticationRemoteDataSourceImpl(get()) }
    single<AuthenticationLocalDataSource> { AuthenticationLocalDataSourceImp(get()) }
    single<ContinueWatchingLocalDataSource>{ ContinueWatchingLocalDataSourceImpl(get()) }
}