package com.wednesday.template.interactor.lastfm

import app.cash.turbine.test
import com.wednesday.template.domain.base.Result
import com.wednesday.template.domain.lastfm.GetFavouriteAlbumsFlowUseCase
import com.wednesday.template.domain.lastfm.SearchAlbumsUseCase
import com.wednesday.template.interactor.base.CoroutineContextController
import com.wednesday.template.interactor.base.InteractorTest
import com.wednesday.template.interactor.base.TestException
import com.wednesday.template.interactor.lastfm.models.album
import com.wednesday.template.interactor.lastfm.models.uiAlbum
import com.wednesday.template.presentation.base.UIList
import com.wednesday.template.presentation.base.UIResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class SearchAlbumInteractorImplTest : InteractorTest() {

    private lateinit var searchAlbumsUseCase: SearchAlbumsUseCase
    private lateinit var favouriteAlbumsFlowUseCase: GetFavouriteAlbumsFlowUseCase
    private lateinit var uiAlbumSearchResultsMapper: UIAlbumSearchResultsMapper
    private lateinit var coroutineContextController: CoroutineContextController
    private lateinit var interactor: SearchAlbumInteractorImpl

    @Before
    fun setup() {
        searchAlbumsUseCase = mock()
        favouriteAlbumsFlowUseCase = mock()
        uiAlbumSearchResultsMapper = mock()
        coroutineContextController = coroutineDispatcherRule.coroutineContextController
    }

    /**
     * When a class containing flow variables are initialized, the flows are instantly initialized.
     * This poses a problem because we need Mockito to first prepare the mocks for the various
     * dependencies required for the class.
     *
     * This is why unlike conventional testing flow, we initialize the interactor class later,
     * so that we can first prepare mocks for our use cases and the mappers
     */
    private fun createInteractor() {
        interactor = SearchAlbumInteractorImpl(
            searchAlbumsUseCase,
            favouriteAlbumsFlowUseCase,
            uiAlbumSearchResultsMapper,
            coroutineContextController
        )
    }

    private fun verifyNoMoreInteractions() {
        verifyNoMoreInteractions(
            searchAlbumsUseCase,
            uiAlbumSearchResultsMapper
        )
    }

    @Test
    fun `Given no error occurs, When search called, Then search album results flow emits UIList of results`(): Unit =
        runTest {
            //Given
            val searchQuery = "Magnatron"
            val albumList = listOf(album)
            val uiList = UIList(uiAlbum)
            whenever(searchAlbumsUseCase(searchQuery)).thenReturn(Result.Success(albumList))
            whenever(favouriteAlbumsFlowUseCase(Unit)).thenReturn(flowOf(Result.Success(albumList)))
            whenever(uiAlbumSearchResultsMapper.map(any(), any())).thenReturn(uiList)

            launchInTestScope {
                println("Creating Interactor")
                createInteractor()
                println("Interactor Created")

                //When
                interactor.searchAlbumResults.test {
                    interactor.search(searchQuery)

                    val result = awaitItem()

                    //Then
                    assertTrue(result is UIResult.Success)
                    assertEquals(actual = result.data, expected = uiList)
                    verify(searchAlbumsUseCase, times(1)).invoke(same(searchQuery))
                    verify(uiAlbumSearchResultsMapper, times(1)).map(same(albumList), same(albumList))
                    verify(favouriteAlbumsFlowUseCase, times(1)).invoke(Unit)
                    verifyNoMoreInteractions()
                    cancelAndIgnoreRemainingEvents()
                }
            }
        }

    @Test
    fun `Given search use case returns error, When search called, Then album results flow emits empty list`(): Unit =
        runTest {
            //Given
            val searchQuery = "Magnatron"
            val albumList = listOf(album)
            val uiList = UIList()
            val testException = TestException()
            whenever(searchAlbumsUseCase(searchQuery)).thenReturn(Result.Error(testException))
            whenever(favouriteAlbumsFlowUseCase(Unit)).thenReturn(flowOf(Result.Success(albumList)))
            whenever(uiAlbumSearchResultsMapper.map(any(), any())).thenReturn(uiList)

            launchInTestScope {
                println("Creating Interactor")
                createInteractor()
                println("Interactor Created")

                //When
                interactor.searchAlbumResults.test {
                    interactor.search(searchQuery)

                    val result = awaitItem()

                    //Then
                    assertTrue(result is UIResult.Success)
                    assertTrue(result.data.items.isEmpty())
                    verify(searchAlbumsUseCase, times(1)).invoke(same(searchQuery))
                    verify(favouriteAlbumsFlowUseCase, times(1)).invoke(Unit)
                    verifyNoMoreInteractions()
                    cancelAndIgnoreRemainingEvents()
                }
            }
        }

    @Test
    fun `Given mapper throws occur, When search called, Then album results flow emits empty list`(): Unit =
        runTest {
            //Given
            val searchQuery = "Magnatron"
            val albumList = listOf(album)
            val testException = TestException()
            whenever(searchAlbumsUseCase(searchQuery)).thenReturn(Result.Success(albumList))
            whenever(favouriteAlbumsFlowUseCase(Unit)).thenReturn(flowOf(Result.Success(albumList)))
            whenever(uiAlbumSearchResultsMapper.map(any(), any())).thenThrow(testException)

            launchInTestScope {
                println("Creating Interactor")
                createInteractor()
                println("Interactor Created")

                //When
                interactor.searchAlbumResults.test {
                    interactor.search(searchQuery)

                    val result = awaitItem()

                    //Then
                    assertTrue(result is UIResult.Error)
                    verify(searchAlbumsUseCase, times(1)).invoke(same(searchQuery))
                    verify(uiAlbumSearchResultsMapper, times(1)).map(same(albumList), same(albumList))
                    verify(favouriteAlbumsFlowUseCase, times(1)).invoke(Unit)
                    verifyNoMoreInteractions()
                    cancelAndIgnoreRemainingEvents()
                }
            }
        }
}