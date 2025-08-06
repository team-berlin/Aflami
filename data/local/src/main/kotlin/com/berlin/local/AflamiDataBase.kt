package com.berlin.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.berlin.repository.datasource.Converters
import com.berlin.local.dao.CategoriesPreferencesDao
import com.berlin.local.dao.ContinueWatchingDao
import com.berlin.local.dao.GenreDao
import com.berlin.local.dao.HomeMovieDao
import com.berlin.local.dao.RecentHistoryDao
import com.berlin.local.dao.SearchDao
import com.berlin.local.dao.HomeTVShowDao
import com.berlin.repository.datasource.local.dto.CategoriesPreferencesEntity

import com.berlin.repository.datasource.local.dto.GenreEntity
import com.berlin.repository.datasource.local.dto.MovieHomeEntity
import com.berlin.repository.datasource.local.dto.TVShowHomeEntity
import com.berlin.repository.datasource.local.dto.RecentHistoryEntity
import com.berlin.repository.datasource.local.dto.RecentlyWatchedMovieEntity
import com.berlin.repository.datasource.local.dto.RecentlyWatchedTvShowEntity
import com.berlin.repository.datasource.local.dto.SearchingEntity

@TypeConverters(Converters::class)
@Database(
    entities = [
        SearchingEntity::class,
        RecentHistoryEntity::class,
        CategoriesPreferencesEntity::class,
        RecentlyWatchedMovieEntity::class,
        RecentlyWatchedTvShowEntity::class,
        GenreEntity::class,
        MovieHomeEntity::class,
        TVShowHomeEntity::class
    ], version = 2, exportSchema = false
)
abstract class AflamiDatabase : RoomDatabase() {
    abstract fun searchDao(): SearchDao
    abstract fun recentHistoryDao(): RecentHistoryDao
    abstract fun categoriesPreferencesDao(): CategoriesPreferencesDao
    abstract fun continueWatchingDao(): ContinueWatchingDao
    abstract fun genreDao(): GenreDao
    abstract fun homeMovieDao(): HomeMovieDao
    abstract fun homeTVShowDao(): HomeTVShowDao


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