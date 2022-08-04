package com.wednesday.template.domain

import com.wednesday.template.domain.base.datetime.ConvertDateToLongUseCase
import com.wednesday.template.domain.base.datetime.ConvertDateToLongUseCaseImpl
import com.wednesday.template.domain.base.datetime.FormatDateUseCase
import com.wednesday.template.domain.base.datetime.FormatDateUseCaseImpl
import com.wednesday.template.domain.lastfm.*
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
    single<GetFavouriteAlbumsFlowUseCase> { GetFavouriteAlbumsFlowUseCaseImpl(get()) }

    single<GetFavouriteAlbumsUseCase> { GetFavouriteAlbumsUseCaseImpl(get()) }

    single<SaveFavouriteAlbumUseCase> { SaveFavouriteAlbumUseCaseImpl(get()) }

    single<RemoveFavouriteAlbumUseCase> { RemoveFavouriteAlbumUseCaseImpl(get()) }

    single<SearchAlbumsUseCase> { SearchAlbumsUseCaseImpl(get()) }
}
