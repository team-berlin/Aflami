package com.berlin.aflami.di

import com.berlin.local.SearchDatabase
import com.berlin.local.dao.CategoriesPreferencesDao
import com.berlin.local.dao.ContinueWatchingDao
import com.berlin.local.dao.GenreDao
import com.berlin.local.dao.RecentHistoryDao
import com.berlin.local.dao.SearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideSearchDao(db: SearchDatabase): SearchDao {
        return db.searchDao()
    }

    @Provides
    @Singleton
    fun provideRecentHistoryDao(db: SearchDatabase): RecentHistoryDao {
        return db.recentHistoryDao()
    }

    @Provides
    @Singleton
    fun provideCategoriesPreferencesDao(db: SearchDatabase): CategoriesPreferencesDao {
        return db.categoriesPreferencesDao()
    }

    @Provides
    @Singleton
    fun provideContinueWatchingDao(db: SearchDatabase): ContinueWatchingDao {
        return db.continueWatchingDao()
    }

    @Provides
    @Singleton
    fun provideGenreDao(db: SearchDatabase): GenreDao {
        return db.genreDao()
    }
}