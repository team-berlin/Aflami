package com.berlin.aflami.di

import com.berlin.repository.SearchRepositoryImpl
import org.koin.dsl.module
import repository.SearchRepository

val repositoryModule = module {

    single<SearchRepository> { SearchRepositoryImpl(get(), get()) }

}