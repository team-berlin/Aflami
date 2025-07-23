package com.berlin.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.berlin.local.converters.Converters
import com.berlin.local.dao.CategoriesPreferencesDao
import com.berlin.local.dao.WatchedMediaDao
import com.berlin.local.dao.RecentHistoryDao
import com.berlin.local.dao.SearchDao
import com.berlin.repository.datasource.local.dto.CategoriesPreferencesEntity
import com.berlin.repository.datasource.local.dto.MediaContinueWatchingEntity
import com.berlin.repository.datasource.local.dto.RecentHistoryEntity
import com.berlin.repository.datasource.local.dto.SearchingEntity

@TypeConverters(Converters::class)
@Database(
    entities = [SearchingEntity::class, RecentHistoryEntity::class, CategoriesPreferencesEntity::class, MediaContinueWatchingEntity::class],
    version = 1
)
abstract class SearchDatabase : RoomDatabase() {
    abstract fun searchDao(): SearchDao
    abstract fun recentHistoryDao(): RecentHistoryDao
    abstract fun categoriesPreferencesDao(): CategoriesPreferencesDao
    abstract fun watchedMediaDao(): WatchedMediaDao

}