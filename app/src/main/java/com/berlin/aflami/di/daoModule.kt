package com.berlin.aflami.di

import com.berlin.local.SearchDatabase
import com.berlin.local.dao.CategoriesPreferencesDao
import com.berlin.local.dao.WatchedMediaDao
import com.berlin.local.dao.RecentHistoryDao
import com.berlin.local.dao.SearchDao
import org.koin.dsl.module

val daoModule = module{
    single<SearchDao> { get<SearchDatabase>().searchDao() }
    single<RecentHistoryDao> { get<SearchDatabase>().recentHistoryDao() }
    single<CategoriesPreferencesDao> { get<SearchDatabase>().categoriesPreferencesDao() }
    single<WatchedMediaDao> { get<SearchDatabase>().watchedMediaDao() }

}