package com.berlin.aflami.di

import androidx.room.Room
import com.berlin.aflami.BuildConfig
import com.berlin.aflami.util.ApiKeyInterceptor
import com.berlin.local.SearchDatabase
import com.berlin.remote.network.MovieApiService
import com.berlin.remote.network.SearchApiService
import com.berlin.remote.network.TVShowApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import okhttp3.Interceptor
import okhttp3.Response

val appModule = module {

    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(logging)
            .build()

        val json = Json {
            ignoreUnknownKeys = true
            classDiscriminator = "media_type"
        }

        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
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
