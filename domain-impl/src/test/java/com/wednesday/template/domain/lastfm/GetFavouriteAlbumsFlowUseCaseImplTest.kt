package com.wednesday.template.domain.lastfm

import app.cash.turbine.test
import com.wednesday.template.domain.base.Result
import com.wednesday.template.domain.lastfm.models.album
import com.wednesday.template.repo.lastfm.AlbumRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GetFavouriteAlbumsFlowUseCaseImplTest {

    private lateinit var albumRepository: AlbumRepository
    private lateinit var getFavouriteAlbumsFlowUseCase: GetFavouriteAlbumsFlowUseCaseImpl

    @Before
    fun setup() {
        albumRepository = mock()
        getFavouriteAlbumsFlowUseCase = GetFavouriteAlbumsFlowUseCaseImpl(albumRepository)
    }

    @Test
    fun `Given repository returns flow, When invoked, Then flow of list of albums is returned`(): Unit =
        runTest {
            // Given
            val albums = listOf(album)
            whenever(albumRepository.getFavouriteAlbumsFlow()).thenReturn(flowOf(albums))

            // When
            getFavouriteAlbumsFlowUseCase(Unit).test {
                val result = awaitItem()

                // Then
                assertTrue(result is Result.Success)
                assertEquals(expected = albums, actual = result.data)
                cancelAndIgnoreRemainingEvents()
            }
        }
}
