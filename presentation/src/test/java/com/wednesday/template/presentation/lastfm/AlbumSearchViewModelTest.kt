package com.wednesday.template.presentation.lastfm

import com.wednesday.template.interactor.lastfm.SearchAlbumInteractor
import com.wednesday.template.navigation.BaseNavigator
import com.wednesday.template.presentation.base.*
import com.wednesday.template.presentation.lastfm.models.album
import com.wednesday.template.presentation.lastfm.search.AlbumSearchScreenState
import com.wednesday.template.presentation.lastfm.search.AlbumSearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.*
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class AlbumSearchViewModelTest : BaseViewModelTest() {

    private lateinit var interactor: SearchAlbumInteractor
    private lateinit var navigator: BaseNavigator
    private lateinit var viewModel:AlbumSearchViewModel

    override fun before() {
        interactor = mock()
        navigator = mock()
        viewModel = AlbumSearchViewModel(
            searchAlbumInteractor = interactor
        )
    }

    override fun after() = Unit

    @Test
    fun `Given _, When getDefaultScreenState, Then it returns correct state`() {
        //Given

        //When
        val screenState = viewModel.getDefaultScreenState()

        //Then
        val expected = getInitialState()
        assertEquals(expected, screenState)
    }

    @Test
    fun `Given album results list emits, When flow is collected, Then state is updated with the UI List`(): Unit =
        runTest {
            // Given
            val uiList = UIResult.Success(UIList(album))
            whenever(interactor.albumResults)
                .thenReturn(flowOf(uiList))

            // When
            val observer = mockObserver<AlbumSearchScreenState>()
            viewModel.screenState.observeForever(observer)
            viewModel.onCreate(null, navigator)

            // Then
            val initialState = getInitialState()
            advanceUntilIdle()
            observer.inOrder {
                verify().onChanged(null)
                verify().onChanged(initialState)
                verify().onChanged(initialState.copy(searchList = uiList.data))
                verify().onChanged(initialState)
                verifyNoMoreInteractions()
            }
            verify(interactor, times(1)).albumResults
        }

    private fun getInitialState() = AlbumSearchScreenState(
        toolbar = UIToolbar(
            title = UIText { block("Search Albums") },
            hasBackButton = true,
            menuIcon = null
        ),
        showLoading = false,
        searchList = UIList()
    )
}