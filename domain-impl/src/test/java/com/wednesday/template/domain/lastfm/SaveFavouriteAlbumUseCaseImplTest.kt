package com.wednesday.template.domain.lastfm

import com.wednesday.template.domain.TestException
import com.wednesday.template.domain.base.Result
import com.wednesday.template.domain.lastfm.models.album
import com.wednesday.template.repo.lastfm.AlbumRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SaveFavouriteAlbumUseCaseImplTest {

    private lateinit var albumRepository: AlbumRepository
    private lateinit var saveFavouriteAlbumUseCase: SaveFavouriteAlbumUseCaseImpl

    @Before
    fun setup() {
        albumRepository = mock()
        saveFavouriteAlbumUseCase = SaveFavouriteAlbumUseCaseImpl(albumRepository)
    }

    @Test
    fun `Given album added as fav by repo, When invoked, Then Success is returned`(): Unit =
        runTest {
            //Given
            val album = album
            whenever(albumRepository.saveAlbumToFavourites(album)).thenReturn(Unit)

            //When
            val result = saveFavouriteAlbumUseCase(album)

            //Then
            assertTrue(result is Result.Success)
            verify(albumRepository, times(1)).saveAlbumToFavourites(same(album))
            verifyNoMoreInteractions(albumRepository)
        }

    @Test
    fun `Given repo throws exception, When invoked, Then Error is returned`(): Unit =
        runTest {
            //Given
            val album = album
            val testException = TestException()
            whenever(albumRepository.saveAlbumToFavourites(album)).thenThrow(testException)

            //When
            val result = saveFavouriteAlbumUseCase(album)

            //Then
            assertTrue(result is Result.Error)
            verify(albumRepository, times(1)).saveAlbumToFavourites(same(album))
            verifyNoMoreInteractions(albumRepository)
        }
}