package com.wednesday.template.domain

import com.wednesday.template.domain.base.datetime.ConvertDateToLongUseCase
import com.wednesday.template.domain.base.datetime.ConvertDateToLongUseCaseImpl
import com.wednesday.template.domain.base.datetime.FormatDateUseCase
import com.wednesday.template.domain.base.datetime.FormatDateUseCaseImpl
import com.wednesday.template.domain.lastfm.SearchAlbumsUseCase
import com.wednesday.template.domain.lastfm.SearchAlbumsUseCaseImpl
import com.wednesday.template.domain.weather.*
import org.koin.dsl.module

val domainModule = module {

    // Date Time
    single<ConvertDateToLongUseCase> { ConvertDateToLongUseCaseImpl(get()) }

    single<FormatDateUseCase> { FormatDateUseCaseImpl(get()) }

    // Weather
    single<GetFavouriteCitiesFlowUseCase> { GetFavouriteCitiesFlowUseCaseImpl(get()) }

    single<SetCityFavouriteUseCase> { SetCityFavouriteUseCaseImpl(get()) }

    single<RemoveCityFavouriteUseCase> { RemoveCityFavouriteUseCaseImpl(get()) }

    single<SearchCitiesUseCase> { SearchCitiesUseCaseImpl(get()) }

    single<GetFavouriteCitiesWeatherFlowUseCase> { GetFavouriteCitiesWeatherFlowUseCaseImpl(get()) }

    single<FetchFavouriteCitiesWeatherUseCase> { FetchFavouriteCitiesWeatherUseCaseImpl(get()) }

    //Last FM
    single<SearchAlbumsUseCase> { SearchAlbumsUseCaseImpl(get()) }
}
