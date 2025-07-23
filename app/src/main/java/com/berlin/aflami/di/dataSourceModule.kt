package com.berlin.aflami.di

import com.berlin.local.dao.CategoriesPreferencesDao
import com.berlin.local.dao.RecentHistoryDao
import com.berlin.local.dao.SearchDao
import com.berlin.local.datasource.CategoriesPreferencesDataSourceImpl
import com.berlin.local.datasource.WatchedMediaLocalDataSourceImpl
import com.berlin.local.datasource.RecentHistoryLocalDataSourceImpl
import com.berlin.local.datasource.SearchLocalDataSourceImpl
import com.berlin.remote.MovieDetailsRemoteDataSourceImpl
import com.berlin.remote.SearchRemoteDataSourceImpl
import com.berlin.remote.TvShowDetailsRemoteDataSourceImpl
import com.berlin.repository.datasource.local.CategoriesPreferencesDataSource
import com.berlin.repository.datasource.local.RecentHistoryLocalDataSource
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.local.WatchedMediaLocalDataSource
import com.berlin.repository.datasource.remote.MovieDetailsRemoteDataSource
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import com.berlin.repository.datasource.remote.TvShowDetailsRemoteDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single<SearchRemoteDataSource> { SearchRemoteDataSourceImpl(get()) }
    single<SearchLocalDataSource> { SearchLocalDataSourceImpl(get<SearchDao>()) }
    single<RecentHistoryLocalDataSource> { RecentHistoryLocalDataSourceImpl(get<RecentHistoryDao>()) }
    single<CategoriesPreferencesDataSource> { CategoriesPreferencesDataSourceImpl(get<CategoriesPreferencesDao>()) }
    single<MovieDetailsRemoteDataSource> { MovieDetailsRemoteDataSourceImpl(get()) }
    single<TvShowDetailsRemoteDataSource> { TvShowDetailsRemoteDataSourceImpl(get()) }
    single<WatchedMediaLocalDataSource>{ WatchedMediaLocalDataSourceImpl(get()) }

}