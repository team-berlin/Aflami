package com.berlin.aflami.di

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import android.content.Context
import com.berlin.local.SearchDatabase
import org.koin.android.ext.koin.androidApplication
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
    single { provideSharedPref(androidContext()) }


}
fun provideSharedPref(context:  Context): SharedPreferences {
    return context.getSharedPreferences(
        "sharedPreferences",
         Context.MODE_PRIVATE)
}