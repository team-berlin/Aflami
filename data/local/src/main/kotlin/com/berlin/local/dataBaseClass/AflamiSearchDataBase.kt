package com.berlin.local.dataBaseClass

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.berlin.local.dao.SearchDao

@Database(
    entities = [SearchCaching::class,],
    version = 1
)
abstract class SearchDatabase : RoomDatabase() {
    abstract fun searchDao(): SearchDao
    companion object {
        @Volatile
        private var instance: SearchDatabase? = null
        fun getInstance(context: Context): SearchDatabase {
            return instance ?: synchronized(this) { buildDatabase(context).also { instance = it } }
        }

        private fun buildDatabase(context: Context): SearchDatabase {
            return Room.databaseBuilder(
                context, SearchDatabase::class.java, DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
        }
        const val DATABASE_NAME = "SearchDatabase"
    }
}
