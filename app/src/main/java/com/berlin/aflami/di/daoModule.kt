package com.berlin.aflami.di

import com.berlin.local.AflamiDatabase
import com.berlin.local.dao.CategoriesPreferencesDao
import com.berlin.local.dao.ContinueWatchingDao
import com.berlin.local.dao.GenreDao
import com.berlin.local.dao.RecentHistoryDao
import com.berlin.local.dao.SearchDao
import com.berlin.local.dao.UserProfileDao
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
    fun provideSearchDao(db: AflamiDatabase): SearchDao {
        return db.searchDao()
    }

    @Provides
    @Singleton
    fun provideRecentHistoryDao(db: AflamiDatabase): RecentHistoryDao {
        return db.recentHistoryDao()
    }

    @Provides
    @Singleton
    fun provideCategoriesPreferencesDao(db: AflamiDatabase): CategoriesPreferencesDao {
        return db.categoriesPreferencesDao()
    }

    @Provides
    @Singleton
    fun provideContinueWatchingDao(db: AflamiDatabase): ContinueWatchingDao {
        return db.continueWatchingDao()
    }

    @Provides
    @Singleton
    fun provideGenreDao(db: AflamiDatabase): GenreDao {
        return db.genreDao()
    }

    @Provides
    @Singleton
    fun provideUserProfileDao(db: AflamiDatabase): UserProfileDao {
        return db.userProfileDao()
    }
}