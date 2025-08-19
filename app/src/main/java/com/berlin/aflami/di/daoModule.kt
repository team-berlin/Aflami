package com.berlin.aflami.di

import com.berlin.local.AflamiDatabase
import com.berlin.local.dao.AppEntryDao
import com.berlin.local.dao.GenrePreferencesDao
import com.berlin.local.dao.ContinueWatchingDao
import com.berlin.local.dao.GenreDao
import com.berlin.local.dao.HomeMovieDao
import com.berlin.local.dao.HomeTVShowDao
import com.berlin.local.dao.RecentHistoryDao
import com.berlin.local.dao.SearchDao
import com.berlin.local.dao.UserPointsDao
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
    fun provideCategoriesPreferencesDao(db: AflamiDatabase): GenrePreferencesDao {
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

    @Provides
    @Singleton
    fun provideAppEntryDao(db: AflamiDatabase): AppEntryDao {
        return db.appEntryDao()
    }

    @Provides
    @Singleton
    fun provideMovieHomeDao(db: AflamiDatabase): HomeMovieDao {
        return db.homeMovieDao()
    }

    @Provides
    @Singleton
    fun provideTVShowHomeDao(db: AflamiDatabase): HomeTVShowDao {
        return db.homeTVShowDao()
    }

    @Provides
    @Singleton
    fun provideUserPointsDao(db: AflamiDatabase): UserPointsDao {
        return db.userPointsDao()
    }


}