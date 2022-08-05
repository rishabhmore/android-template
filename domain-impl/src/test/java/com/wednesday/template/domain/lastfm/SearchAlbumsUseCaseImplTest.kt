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
import org.mockito.kotlin.whenever
import org.mockito.kotlin.verify
import org.mockito.kotlin.times
import org.mockito.kotlin.same
import org.mockito.kotlin.verifyNoMoreInteractions
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SearchAlbumsUseCaseImplTest {

    private lateinit var albumRepository: AlbumRepository
    private lateinit var searchAlbumsUseCase: SearchAlbumsUseCaseImpl

    @Before
    fun setup() {
        albumRepository = mock()
        searchAlbumsUseCase = SearchAlbumsUseCaseImpl(albumRepository)
    }

    @Test
    fun `Given album searched by repo, When invoke called, Then Success is returned`() =
        runTest {
            // Given
            val searchTerm = "magnatron"
            val albums = listOf(album)
            whenever(albumRepository.searchAlbums(searchTerm)).thenReturn(albums)

            // When
            val result = searchAlbumsUseCase(searchTerm)

            // Then
            assertTrue(result is Result.Success)
            verify(albumRepository, times(1)).searchAlbums(same(searchTerm))
            verifyNoMoreInteractions(albumRepository)
        }

    @Test
    fun `Given repo throws exception, When invoke called, Then Error is returned`() =
        runTest {
            // Given
            val searchTerm = "magnatron"
            val testException = TestException()
            whenever(albumRepository.searchAlbums(searchTerm)).thenThrow(testException)

            // When
            val result = searchAlbumsUseCase(searchTerm)

            // Then
            assertTrue(result is Result.Error)
            verify(albumRepository, times(1)).searchAlbums(same(searchTerm))
            verifyNoMoreInteractions(albumRepository)
        }
}
