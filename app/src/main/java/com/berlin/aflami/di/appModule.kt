package com.berlin.aflami.di

import com.berlin.aflami.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
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
                    }
                )
            }
        }
    }
}