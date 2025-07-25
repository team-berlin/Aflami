package com.berlin.aflami.di

import com.berlin.aflami.BuildConfig
import com.berlin.aflami.util.ApiKeyInterceptor
import com.berlin.remote.network.ApiService
//import com.berlin.remote.network.HomeApiService
import com.berlin.remote.network.HomeApiService
import com.berlin.remote.network.MovieApiService
import com.berlin.remote.network.SearchApiService
import com.berlin.remote.network.TVShowApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

@OptIn(ExperimentalSerializationApi::class)
val networkModule = module {

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder().addInterceptor(ApiKeyInterceptor())
            .addInterceptor(get<HttpLoggingInterceptor>()).build()
    }

    single {
        Json {
            ignoreUnknownKeys = true
            classDiscriminator = "media_type"
        }
    }

    single {
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(get()).addConverterFactory(
                get<Json>().asConverterFactory("application/json".toMediaType())
            ).build()
    }

    single { get<Retrofit>().create(HomeApiService::class.java) }
    single { get<Retrofit>().create(MovieApiService::class.java) }
    single { get<Retrofit>().create(TVShowApiService::class.java) }
    single { get<Retrofit>().create(SearchApiService::class.java) }
    single { get<Retrofit>().create(ApiService::class.java) }
}
