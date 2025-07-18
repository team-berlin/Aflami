package com.berlin.aflami.di

import com.berlin.local.dao.RecentHistoryDao
import com.berlin.local.dao.SearchDao
import com.berlin.local.datasource.RecentHistoryLocalDataSourceImpl
import com.berlin.local.datasource.SearchLocalDataSourceImpl
import com.berlin.remote.SearchRemoteDataSourceImp
import com.berlin.repository.datasource.local.RecentHistoryLocalDataSource
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import io.ktor.client.HttpClient
import org.koin.dsl.module

val dataSourceModule = module {
    single<SearchRemoteDataSource> { SearchRemoteDataSourceImp(get<HttpClient>()) }
    single<SearchLocalDataSource> { SearchLocalDataSourceImpl(get<SearchDao>()) }
    single<RecentHistoryLocalDataSource> { RecentHistoryLocalDataSourceImpl(get<RecentHistoryDao>()) }
}