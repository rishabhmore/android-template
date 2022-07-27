package com.wednesday.template.service

import com.wednesday.template.service.base.getRoomDatabase
import com.wednesday.template.service.base.retrofit.getLastFMRetrofit
import com.wednesday.template.service.base.retrofit.getOpenWeatherRetrofit
import com.wednesday.template.service.base.retrofit.interceptors.OpenWeatherApiKeyInterceptor
import com.wednesday.template.service.openWeather.OpenWeatherLocalServiceImpl
import com.wednesday.template.service.room.AndroidTemplateDatabase
import com.wednesday.template.service.weather.LastFMRemoteService
import com.wednesday.template.service.weather.OpenWeatherLocalService
import com.wednesday.template.service.weather.OpenWeatherRemoteService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {

    // Retrofit
    factory { OpenWeatherApiKeyInterceptor() }

    single(named("OpenWeather")) {
        getOpenWeatherRetrofit(get(), get<OpenWeatherApiKeyInterceptor>())
    }

    single(named("LastFM")) { getLastFMRetrofit(get()) }

    // Room
    single { getRoomDatabase(get()) }

    // OpenWeather
    single { getWeatherRemoteService(get(qualifier = named("OpenWeather"))) }

    single<OpenWeatherLocalService> { getWeatherLocalService(get()) }

    //Last FM
    single { getLastFMRemoteService(get(qualifier = named("LastFM"))) }
}

fun getWeatherLocalService(database: AndroidTemplateDatabase): OpenWeatherLocalServiceImpl {
    return database.databaseDao()
}

fun getWeatherRemoteService(retrofit: Retrofit): OpenWeatherRemoteService {
    return retrofit.create(OpenWeatherRemoteService::class.java)
}

//Last FM
fun getLastFMRemoteService(retrofit: Retrofit): LastFMRemoteService {
    return retrofit.create(LastFMRemoteService::class.java)
}
