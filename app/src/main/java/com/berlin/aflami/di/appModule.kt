package com.berlin.aflami.di

import androidx.room.Room
import com.berlin.aflami.BuildConfig
import com.berlin.local.SearchDatabase
import com.berlin.remote.network.MovieApiService
import com.berlin.remote.network.SearchApiService
import com.berlin.remote.network.TVShowApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {

    // JSON setup for Kotlinx Serialization
    single {
        Json {
            ignoreUnknownKeys = true
            classDiscriminator = "media_type"
        }
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
            .build()
    }

    single { get<Retrofit>().create(MovieApiService::class.java) }
    single { get<Retrofit>().create(TVShowApiService::class.java) }
    single { get<Retrofit>().create(SearchApiService::class.java) }

    single {
        Room.databaseBuilder(
            androidContext(),
            SearchDatabase::class.java,
            "Aflami_Database"
        ).fallbackToDestructiveMigration(false).build()
    }
}