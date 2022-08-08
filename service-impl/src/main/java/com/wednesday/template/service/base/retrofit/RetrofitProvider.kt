package com.wednesday.template.service.base.retrofit

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.wednesday.template.service.BuildConfig
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/**
 * Common Function to return an OkHttpClient instance of base configuration for
 * two different apis: OpenWeather and LastFM
 */
private fun getBaseClient(context: Context, vararg interceptors: Interceptor): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()

    httpLoggingInterceptor.level = when (BuildConfig.DEBUG) {
        true -> HttpLoggingInterceptor.Level.BODY
        false -> HttpLoggingInterceptor.Level.NONE
    }

    return OkHttpClient().newBuilder().run {

        interceptors.forEach { addInterceptor(it) }

        if (BuildConfig.DEBUG) {
            addInterceptor(
                ChuckerInterceptor
                    .Builder(context)
                    .alwaysReadResponseBody(true)
                    .build()
            )
        }

        addInterceptor(httpLoggingInterceptor)

        build()
    }
}

@OptIn(ExperimentalSerializationApi::class)
fun getOpenWeatherRetrofit(context: Context, vararg interceptors: Interceptor): Retrofit {

    val apiBaseUrl = "https://api.openweathermap.org/"
    val contentType = "application/json".toMediaType()
    // FIXME Check if varargs parameters is passed correctly or not by adding this *
    val client = getBaseClient(context, *interceptors)
    val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    return Retrofit.Builder()
        .client(client)
        .baseUrl(apiBaseUrl)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()
}

@OptIn(ExperimentalSerializationApi::class)
fun getLastFMRetrofit(context: Context): Retrofit {
    val baseUrl = "https://ws.audioscrobbler.com"
    val contentType = "application/json".toMediaType()
    val client = getBaseClient(context)
    val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    return Retrofit.Builder()
        .client(client)
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()
}
