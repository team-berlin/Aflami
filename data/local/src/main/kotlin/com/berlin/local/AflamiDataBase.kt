package com.berlin.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
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
import com.berlin.repository.datasource.Converters
import com.berlin.repository.datasource.local.dto.AppEntryEntity
import com.berlin.repository.datasource.local.dto.CategoriesPreferencesEntity
import com.berlin.repository.datasource.local.dto.HomeMovieEntity
import com.berlin.repository.datasource.local.dto.MoviesGenreEntity
import com.berlin.repository.datasource.local.dto.RecentSearchHistoryEntity
import com.berlin.repository.datasource.local.dto.RecentlyWatchedMovieEntity
import com.berlin.repository.datasource.local.dto.RecentlyWatchedTvShowEntity
import com.berlin.repository.datasource.local.dto.TVShowGenreEntity
import com.berlin.repository.datasource.local.dto.HomeTVShowEntity
import com.berlin.repository.datasource.local.dto.UserPointsEntity
import com.berlin.repository.datasource.local.dto.UserProfileEntity

@TypeConverters(Converters::class)
@Database(
    entities = [
        RecentSearchHistoryEntity::class,
        CategoriesPreferencesEntity::class,
        RecentlyWatchedMovieEntity::class,
        RecentlyWatchedTvShowEntity::class,
        AppEntryEntity::class,
        TVShowGenreEntity::class,
        MoviesGenreEntity::class,
        HomeMovieEntity::class,
        HomeTVShowEntity::class,
        UserProfileEntity::class,
       UserPointsEntity::class

    ], version =0, exportSchema = false
)
abstract class AflamiDatabase : RoomDatabase() {
    abstract fun searchDao(): SearchDao
    abstract fun recentHistoryDao(): RecentHistoryDao
    abstract fun categoriesPreferencesDao(): GenrePreferencesDao
    abstract fun continueWatchingDao(): ContinueWatchingDao
    abstract fun genreDao(): GenreDao
    abstract fun appEntryDao():AppEntryDao
    abstract fun homeMovieDao(): HomeMovieDao
    abstract fun homeTVShowDao(): HomeTVShowDao
    abstract fun userProfileDao(): UserProfileDao
    abstract fun userPointsDao(): UserPointsDao


    companion object {
        private const val DATABASE_NAME = "Aflami_Database"

        @Volatile
        private var instance: AflamiDatabase? = null

        fun getInstance(context: Context): AflamiDatabase {
            return instance ?: synchronized(this) {
                buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): AflamiDatabase {
            return Room.databaseBuilder(context, AflamiDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration(false).build()
        }
    }
}