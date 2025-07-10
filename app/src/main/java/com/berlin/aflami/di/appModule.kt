package com.berlin.aflami.di

import com.berlin.aflami.BuildConfig
import com.berlin.repository.datasource.remote.dto.MediaItem
import com.berlin.repository.datasource.remote.dto.MovieItem
import com.berlin.repository.datasource.remote.dto.TvItem
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClient(CIO) {
            defaultRequest {
                url { takeFrom(BuildConfig.BASE_URL) }
                url { parameters.append("api_key", BuildConfig.API_KEY) }
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        classDiscriminator = "media_type"
                        serializersModule = SerializersModule {
                            polymorphic(MediaItem::class) {
                                subclass(MovieItem::class, MovieItem.serializer())
                                subclass(TvItem::class, TvItem.serializer())
                            }

                        }
                    }
                )
            }
        }
    }
}
