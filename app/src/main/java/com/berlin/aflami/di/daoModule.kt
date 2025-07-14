package com.berlin.aflami.di

import com.berlin.local.SearchDatabase
import com.berlin.local.dao.SearchDao
import org.koin.dsl.module

val daoModule = module{
    single<SearchDao> { get<SearchDatabase>().searchDao() }
}