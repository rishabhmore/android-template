package com.wednesday.template.domain.lastfm

import com.wednesday.template.domain.TestException
import com.wednesday.template.domain.base.Result
import com.wednesday.template.domain.lastfm.models.album
import com.wednesday.template.repo.lastfm.AlbumRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.same
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class RemoveFavouriteAlbumUseCaseImplTest {

    private lateinit var albumRepository: AlbumRepository
    private lateinit var removeFavouriteAlbumsUseCase: RemoveFavouriteAlbumUseCaseImpl

    @Before
    fun setup() {
        albumRepository = mock()
        removeFavouriteAlbumsUseCase = RemoveFavouriteAlbumUseCaseImpl(albumRepository)
    }

    @Test
    fun `Given album removed as fav by repo, When invoked, Then Success is returned`(): Unit =
        runTest {
            // Given
            val album = album
            whenever(albumRepository.removeAlbumFromFavourites(album)).thenReturn(Unit)

            // When
            val result = removeFavouriteAlbumsUseCase(album)

            // Then
            assertTrue(result is Result.Success)
            verify(albumRepository, times(1)).removeAlbumFromFavourites(same(album))
            verifyNoMoreInteractions(albumRepository)
        }

    @Test
    fun `Given repo throws exception, When invoked, Then Error is returned`(): Unit =
        runTest {
            // Given
            val album = album
            val testException = TestException()
            whenever(albumRepository.removeAlbumFromFavourites(album)).thenThrow(testException)

            // When
            val result = removeFavouriteAlbumsUseCase(album)

            // Then
            assertTrue(result is Result.Error)
            verify(albumRepository, times(1)).removeAlbumFromFavourites(same(album))
            verifyNoMoreInteractions(albumRepository)
        }
}
