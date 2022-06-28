package com.wednesday.template.presentation.weather.search

import androidx.lifecycle.viewModelScope
import com.wednesday.template.interactor.weather.FavouriteWeatherInteractor
import com.wednesday.template.interactor.weather.SearchCityInteractor
import com.wednesday.template.presentation.R
import com.wednesday.template.presentation.base.effect.ShowSnackbarEffect
import com.wednesday.template.presentation.base.intent.IntentHandler
import com.wednesday.template.presentation.base.list.UIList
import com.wednesday.template.presentation.base.result.UIResult
import com.wednesday.template.presentation.base.text.UIText
import com.wednesday.template.presentation.base.toolbar.UIToolbar
import com.wednesday.template.presentation.base.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchCityInteractor: SearchCityInteractor,
    private val favouriteWeatherInteractor: FavouriteWeatherInteractor,
) : BaseViewModel<SearchScreen, SearchScreenState>(initialState),
    IntentHandler<SearchScreenIntent> {

    private val searchCityResponseMutableStateFlow: MutableStateFlow<String> = MutableStateFlow("")

    companion object {
        private val initialState = SearchScreenState(
            toolbar = UIToolbar(
                title = UIText { block("Search") },
                hasBackButton = true,
                menuIcon = null
            ),
            showLoading = false,
            searchList = UIList()
        )
    }

    override fun onCreate(fromRecreate: Boolean) {

        searchCityInteractor.searchResultsFlow.onEach {
            when (it) {
                is UIResult.Success -> {
                    setState {
                        copy(showLoading = false, searchList = it.data)
                    }
                }
                is UIResult.Error -> {
                    setState {
                        copy(showLoading = false)
                    }
                    setEffect(
                        ShowSnackbarEffect(
                            message = UIText {
                                block(R.string.something_went_wrong)
                            }
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)

        searchCityResponseMutableStateFlow
            .debounce(500)
            .map { it.trim() }
            .onEach {

                if (it.isBlank()) {
                    setState { copy(searchList = UIList()) }
                    return@onEach
                }

                setState {
                    copy(showLoading = true)
                }
                searchCityInteractor.search(it)
            }
            .launchIn(viewModelScope)
    }

    override fun onIntent(intent: SearchScreenIntent) {
        when (intent) {
            is SearchScreenIntent.SearchCities -> {
                viewModelScope.launch {
                    searchCityResponseMutableStateFlow.value = intent.city
                }
            }
            is SearchScreenIntent.ToggleFavourite -> {
                viewModelScope.launch {
                    if (intent.city.isFavourite) {
                        favouriteWeatherInteractor.removeCityFavourite(intent.city)
                    } else {
                        favouriteWeatherInteractor.setCityFavourite(intent.city)
                    }
                }
            }
            SearchScreenIntent.Back -> {
            }
        }
    }
}
