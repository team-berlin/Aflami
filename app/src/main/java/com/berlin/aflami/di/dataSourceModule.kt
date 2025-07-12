package com.berlin.aflami.di

import com.berlin.local.dao.SearchDao
import com.berlin.local.datasource.SearchLocalDataSourceImpl
import com.berlin.remote.SearchRemoteDataSourceImpl
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single<SearchRemoteDataSource> { SearchRemoteDataSourceImpl(get()) }
    single<SearchLocalDataSource> { SearchLocalDataSourceImpl(get<SearchDao>()) }
}