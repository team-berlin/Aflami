package com.berlin.aflami.di


import android.content.Context
import androidx.room.Room
import com.berlin.local.SearchDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SearchDatabase {
        return Room.databaseBuilder(
            context,
            SearchDatabase::class.java,
            "Aflami_Database"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }
}
