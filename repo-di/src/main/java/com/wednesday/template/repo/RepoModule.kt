package com.wednesday.template.repo

import com.wednesday.template.repo.date.DateRepo
import com.wednesday.template.repo.date.DateRepoImpl
import com.wednesday.template.repo.lastfm.AlbumRepository
import com.wednesday.template.repo.lastfm.AlbumRepositoryImpl
import com.wednesday.template.repo.lastfm.DomainAlbumsMapper
import com.wednesday.template.repo.lastfm.DomainAlbumsMapperImpl
import com.wednesday.template.repo.lastfm.LocalAlbumsMapper
import com.wednesday.template.repo.lastfm.LocalAlbumsMapperImpl
import com.wednesday.template.repo.weather.DomainCityMapper
import com.wednesday.template.repo.weather.DomainCityMapperImpl
import com.wednesday.template.repo.weather.DomainWeatherMapper
import com.wednesday.template.repo.weather.DomainWeatherMapperImpl
import com.wednesday.template.repo.weather.LocalCityMapper
import com.wednesday.template.repo.weather.LocalCityMapperImpl
import com.wednesday.template.repo.weather.LocalWeatherMapper
import com.wednesday.template.repo.weather.LocalWeatherMapperImpl
import com.wednesday.template.repo.weather.WeatherRepository
import com.wednesday.template.repo.weather.WeatherRepositoryImpl
import org.koin.dsl.module

val repoModule = module {

    // Date Time
    single<DateRepo> { DateRepoImpl() }

    // Weather
    single<DomainCityMapper> { DomainCityMapperImpl() }

    single<LocalCityMapper> { LocalCityMapperImpl() }

    single<DomainWeatherMapper> { DomainWeatherMapperImpl() }

    single<LocalWeatherMapper> { LocalWeatherMapperImpl(get()) }

    single<WeatherRepository> {
        WeatherRepositoryImpl(get(), get(), get(), get(), get(), get(), get())
    }

    // Last FM
    single<DomainAlbumsMapper> { DomainAlbumsMapperImpl() }

    single<LocalAlbumsMapper> { LocalAlbumsMapperImpl() }

    single<AlbumRepository> { AlbumRepositoryImpl(get(), get(), get(), get()) }
}
