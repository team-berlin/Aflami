package com.berlin.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.berlin.local.converters.Converters
import com.berlin.local.dao.SearchDao
import com.berlin.repository.datasource.local.dto.MovieEntity

@TypeConverters(Converters::class)
@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class SearchDatabase : RoomDatabase() {
    abstract fun searchDao(): SearchDao
}