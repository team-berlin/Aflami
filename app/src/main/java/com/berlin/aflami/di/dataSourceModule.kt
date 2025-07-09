package com.berlin.aflami.di

import com.berlin.remote.SearchRemoteDataSourceImpl
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single<SearchRemoteDataSource> { SearchRemoteDataSourceImpl(get()) }
}