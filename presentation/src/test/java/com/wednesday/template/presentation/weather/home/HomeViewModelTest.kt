package com.wednesday.template.presentation.weather.home

import com.wednesday.template.interactor.weather.FavouriteWeatherInteractor
import com.wednesday.template.navigation.home.HomeNavigator
import com.wednesday.template.presentation.base.BaseViewModelTest
import com.wednesday.template.presentation.base.UIList
import com.wednesday.template.presentation.base.UIResult
import com.wednesday.template.presentation.base.UIText
import com.wednesday.template.presentation.base.UIToolbar
import com.wednesday.template.presentation.lastfm.search.AlbumSearchScreen
import com.wednesday.template.presentation.weather.home.models.city
import com.wednesday.template.resources.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyZeroInteractions
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class HomeViewModelTest : BaseViewModelTest() {

    private lateinit var interactor: FavouriteWeatherInteractor
    private lateinit var navigator: HomeNavigator
    private lateinit var viewModel: HomeViewModel

    override fun before() {
        interactor = mock()
        navigator = mock()
        viewModel = HomeViewModel(
            favouriteWeatherInteractor = interactor
        )
    }

    override fun after() = Unit

    @Test
    fun `Given _, When getDefaultScreenState, Then it returns correct state`() {
        // Given

        // When
        val screenState = viewModel.getDefaultScreenState()

        // Then
        val expected = getInitialState()
        assertEquals(expected, screenState)
    }

    @Test
    fun `Given fromRecreate = true, When onCreate, Then interactor was not called`() =
        runTest {
            // Given
            whenever(interactor.getFavouriteCitiesFlow())
                .thenReturn(flowOf())
            whenever(interactor.getFavouriteWeatherUIList())
                .thenReturn(flowOf())
            val fromRecreate = true

            // When
            viewModel.onCreate(fromRecreate = fromRecreate)

            // Then
            verifyZeroInteractions(interactor)
        }

    @Test
    fun `Given fromRecreate = false, When onCreate, Then FavouriteCitiesFlow and FavouriteWeatherUIList were called`(): Unit =
        runTest {
            // Given
            val fromRecreate = false
            whenever(interactor.getFavouriteCitiesFlow())
                .thenReturn(flowOf())
            whenever(interactor.getFavouriteWeatherUIList())
                .thenReturn(flowOf())

            // When
            viewModel.onCreate(fromRecreate = fromRecreate)

            // Then
            advanceUntilIdle()
            verify(interactor, times(1)).getFavouriteWeatherUIList()
            verify(interactor, times(1)).getFavouriteCitiesFlow()
        }

    @Test
    fun `Given _, When search intent received, Then app navigates to search screen`() {
        // Given
        whenever(interactor.getFavouriteCitiesFlow())
            .thenReturn(flowOf())
        whenever(interactor.getFavouriteWeatherUIList())
            .thenReturn(flowOf())
        viewModel.onCreate(null, navigator)

        // When
        viewModel.onIntent(HomeScreenIntent.Search)

        // Then
        verify(navigator, times(1)).navigateTo(AlbumSearchScreen)
    }

    @Test
    fun `Given favourite city flow emits value, When new favourite city added, Then favourite city weather is fetched`(): Unit =
        runTest {
            // Given
            val favCityList = UIResult.Success(listOf(city))
            whenever(interactor.getFavouriteCitiesFlow())
                .thenReturn(flowOf(favCityList, favCityList))
            whenever(interactor.getFavouriteWeatherUIList())
                .thenReturn(flowOf())

            // When
            viewModel.onCreate(false)

            // Then
            advanceUntilIdle()
            verify(interactor, times(2)).fetchFavouriteCitiesWeather()
        }

    @Test
    fun `Given weather ui list emits, When flow is collected, Then state is updated with the UI list`(): Unit =
        runTest {
            // Given
            val uiList = UIResult.Success(UIList(city))
            whenever(interactor.getFavouriteCitiesFlow())
                .thenReturn(flowOf())
            whenever(interactor.getFavouriteWeatherUIList())
                .thenReturn(flowOf(uiList))

            // When
            val observer = mockObserver<HomeScreenState>()
            viewModel.screenState.observeForever(observer)
            viewModel.onCreate(null, navigator)

            // Then
            val initialState = getInitialState()
            advanceUntilIdle()
            observer.inOrder {
                verify().onChanged(null)
                verify().onChanged(initialState)
                verify().onChanged(initialState.copy(items = uiList.data))
                verifyNoMoreInteractions()
            }
            verify(interactor, times(1)).getFavouriteWeatherUIList()
        }

    private fun getInitialState() = HomeScreenState(
        toolbar = UIToolbar(
            title = UIText { block("Weather") },
            hasBackButton = false,
            menuIcon = R.drawable.ic_search,
        ),
        showLoading = false,
        items = UIList()
    )
}
