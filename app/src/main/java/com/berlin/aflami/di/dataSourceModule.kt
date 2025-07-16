package com.berlin.aflami.di

import com.berlin.local.dao.SearchDao
import com.berlin.local.datasource.SearchLocalDataSourceImpl
import com.berlin.remote.SearchRemoteDataSourceImp
import com.berlin.repository.datasource.local.SearchLocalDataSource
import com.berlin.repository.datasource.remote.MovieDetailsRemoteDataSource
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import io.ktor.client.HttpClient
import org.koin.dsl.module

val dataSourceModule = module {
    single<SearchRemoteDataSource> { SearchRemoteDataSourceImpl(get()) }
    single<SearchLocalDataSource> { SearchLocalDataSourceImpl(get<SearchDao>()) }
    single<MovieDetailsRemoteDataSource> { MovieDetailsRemoteDataSourceImpl(get()) }
    single<SeriesDetailsRemoteDataSource> { SeriesDetailsRemoteDataSourceImpl(get()) }
}