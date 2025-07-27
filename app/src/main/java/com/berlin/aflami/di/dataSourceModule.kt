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
import com.berlin.remote.HomeRemoteDataSourceImpl
import com.berlin.repository.datasource.local.AuthenticationLocalDataSource
import com.berlin.repository.datasource.local.CategoriesPreferencesDataSource
import com.berlin.repository.datasource.local.RecentHistoryLocalDataSource
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.remote.AuthenticationRemoteDataSource
import com.berlin.repository.datasource.local.ContinueWatchingLocalDataSource
import com.berlin.repository.datasource.remote.HomeRemoteDataSource
import com.berlin.repository.datasource.remote.RemoteDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind

import org.koin.dsl.module

val dataSourceModule = module {
    singleOf (::SearchLocalDataSourceImpl) bind SearchLocalDataSource::class
    singleOf(::RecentHistoryLocalDataSourceImpl) bind RecentHistoryLocalDataSource::class
    singleOf(::CategoriesPreferencesDataSourceImpl) bind CategoriesPreferencesDataSource::class
    singleOf(::DataSourceImpl) bind RemoteDataSource::class
    singleOf(::AuthenticationRemoteDataSourceImpl) bind AuthenticationRemoteDataSource::class
    singleOf(::AuthenticationLocalDataSourceImp) bind AuthenticationLocalDataSource::class
    singleOf(::ContinueWatchingLocalDataSourceImpl) bind ContinueWatchingLocalDataSource::class
    singleOf(::HomeRemoteDataSourceImpl) bind HomeRemoteDataSource::class
}