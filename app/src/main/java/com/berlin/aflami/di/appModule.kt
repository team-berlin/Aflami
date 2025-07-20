package com.berlin.aflami.di

import androidx.room.Room
import com.berlin.local.SearchDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            SearchDatabase::class.java,
            "Aflami_Database"
        ).fallbackToDestructiveMigration(false).build()
    }
}
