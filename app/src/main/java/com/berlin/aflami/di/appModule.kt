package com.berlin.aflami.di

import androidx.room.Room
import com.berlin.aflami.BuildConfig
import com.berlin.local.SearchDatabase
import com.berlin.local.dao.SearchDao

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext

import org.koin.dsl.module

val appModule = module {
    single {
        HttpClient(CIO) {
            defaultRequest {
                url { takeFrom(BuildConfig.BASE_URL) }
                url { parameters.append("api_key", BuildConfig.API_KEY) }
            }
            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.BODY
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            SearchDatabase::class.java,
            "Aflami_Database"
        ).fallbackToDestructiveMigration(false).build()
    }

    single<SearchDao> {
        get<SearchDatabase>().searchDao()
    }
}
