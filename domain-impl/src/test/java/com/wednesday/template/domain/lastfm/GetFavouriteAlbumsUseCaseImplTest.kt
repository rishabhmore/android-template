package com.wednesday.template.domain.lastfm

import com.wednesday.template.domain.TestException
import com.wednesday.template.domain.base.Result
import com.wednesday.template.repo.lastfm.AlbumRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GetFavouriteAlbumsUseCaseImplTest {

    private lateinit var albumRepository: AlbumRepository
    private lateinit var getFavouriteAlbumsUseCase: GetFavouriteAlbumsUseCaseImpl

    @Before
    fun setup() {
        albumRepository = mock()
        getFavouriteAlbumsUseCase = GetFavouriteAlbumsUseCaseImpl(albumRepository)
    }

    @Test
    fun `Given fetch is successful, When invoked, Then Success is returned`(): Unit =
        runTest {
            // Given

            // When
            val result = getFavouriteAlbumsUseCase(Unit)

            // Then
            assertTrue(result is Result.Success)
            verify(albumRepository, times(1)).getFavouriteAlbums()
            verifyNoMoreInteractions(albumRepository)
        }

    @Test
    fun `Given fetch is not successful, When invoked, Then Error is returned`(): Unit =
        runTest {
            // Given
            val testException = TestException()
            whenever(albumRepository.getFavouriteAlbums()).thenThrow(testException)

            // When
            val result = getFavouriteAlbumsUseCase(Unit)

            // Then
            assertTrue(result is Result.Error)
            verify(albumRepository, times(1)).getFavouriteAlbums()
            verifyNoMoreInteractions(albumRepository)
        }
}
