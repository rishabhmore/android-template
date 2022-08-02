package com.wednesday.template.service

import com.wednesday.template.service.base.getRoomDatabase
import com.wednesday.template.service.base.retrofit.getLastFMRetrofit
import com.wednesday.template.service.base.retrofit.getOpenWeatherRetrofit
import com.wednesday.template.service.base.retrofit.interceptors.OpenWeatherApiKeyInterceptor
import com.wednesday.template.service.lastfm.LastFMLocalServiceImpl
import com.wednesday.template.service.openWeather.OpenWeatherLocalServiceImpl
import com.wednesday.template.service.room.AndroidTemplateDatabase
import com.wednesday.template.service.weather.LastFMLocalService
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

    single<LastFMLocalService> { getLastFMLocalService(get()) }
}

fun getWeatherLocalService(database: AndroidTemplateDatabase): OpenWeatherLocalServiceImpl {
    return database.weatherDao()
}

fun getWeatherRemoteService(retrofit: Retrofit): OpenWeatherRemoteService {
    return retrofit.create(OpenWeatherRemoteService::class.java)
}

//Last FM
fun getLastFMLocalService(database: AndroidTemplateDatabase): LastFMLocalServiceImpl {
    return database.albumDao()
}

fun getLastFMRemoteService(retrofit: Retrofit): LastFMRemoteService {
    return retrofit.create(LastFMRemoteService::class.java)
}
