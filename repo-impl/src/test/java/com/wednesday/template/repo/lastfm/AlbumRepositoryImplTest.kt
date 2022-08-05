package com.wednesday.template.repo.lastfm

import app.cash.turbine.test
import com.wednesday.template.domain.lastfm.Album
import com.wednesday.template.repo.lastfm.models.album
import com.wednesday.template.repo.lastfm.models.localAlbum
import com.wednesday.template.repo.lastfm.models.remoteAlbumResults
import com.wednesday.template.service.weather.LastFMLocalService
import com.wednesday.template.service.weather.LastFMRemoteService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.same
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime

@ExperimentalTime
@OptIn(ExperimentalCoroutinesApi::class)
class AlbumRepositoryImplTest {

    private lateinit var lastFMLocalService: LastFMLocalService
    private lateinit var lastFMRemoteService: LastFMRemoteService
    private lateinit var domainAlbumsMapper: DomainAlbumsMapper
    private lateinit var localAlbumsMapper: LocalAlbumsMapper
    private lateinit var albumRepository: AlbumRepositoryImpl

    @Before
    fun setup() {
        lastFMLocalService = mock()
        lastFMRemoteService = mock()
        domainAlbumsMapper = mock()
        localAlbumsMapper = mock()
        albumRepository = AlbumRepositoryImpl(
            lastFMLocalService,
            lastFMRemoteService,
            domainAlbumsMapper,
            localAlbumsMapper
        )
    }

    @Test
    fun `Given a search string, When searchAlbums is called, Then list of albums is returned`(): Unit =
        runTest {
            // Given
            val searchTerm = "magnatron"
            val domainAlbums: List<Album> = listOf(album)
            val remoteAlbumsResponse = remoteAlbumResults
            whenever(lastFMRemoteService.searchAlbums(album = searchTerm))
                .thenReturn(remoteAlbumsResponse)
            whenever(domainAlbumsMapper.mapRemoteAlbum(same(remoteAlbumsResponse.results?.matches?.album)))
                .thenReturn(domainAlbums)

            // When
            val result = albumRepository.searchAlbums(searchTerm)

            // Then
            assertEquals(expected = listOf(album), actual = result)
            verify(lastFMRemoteService, times(1)).searchAlbums(album = searchTerm)
            verify(domainAlbumsMapper, times(1))
                .mapRemoteAlbum(remoteAlbumsResponse.results?.matches?.album)
            verifyNoMoreInteractions()
        }

    @Test
    fun `Given getFavouriteAlbumsFlow is called, Then it returns flow of albums`(): Unit =
        runTest {
            // Given
            val localAlbums = listOf(localAlbum)
            val albums = listOf(album)
            whenever(lastFMLocalService.getFavouriteAlbumsFlow()).thenReturn(
                flowOf(
                    localAlbums, localAlbums
                )
            )
            whenever(domainAlbumsMapper.map(localAlbums)).thenReturn(albums)

            // When
            albumRepository.getFavouriteAlbumsFlow().test {
                val firstAlbum = awaitItem()

                // Then
                assertEquals(expected = albums, actual = firstAlbum)
                cancelAndIgnoreRemainingEvents()
            }
            verify(lastFMLocalService, times(1)).getFavouriteAlbumsFlow()
            verify(domainAlbumsMapper, times(2)).map(localAlbums)
            verifyNoMoreInteractions()
        }

    @Test
    fun `Given getFavouriteAlbums is called, Then it returns list of albums`(): Unit =
        runTest {
            // Given
            val localAlbums = listOf(localAlbum)
            val albums = listOf(album)
            whenever(lastFMLocalService.getFavouriteAlbums()).thenReturn(localAlbums)
            whenever(domainAlbumsMapper.map(localAlbums)).thenReturn(albums)

            // When
            val result = albumRepository.getFavouriteAlbums()

            // Then
            assertEquals(expected = albums, actual = result)
            verify(lastFMLocalService, times(1)).getFavouriteAlbums()
            verify(domainAlbumsMapper, times(1)).map(localAlbums)
            verifyNoMoreInteractions()
        }

    @Test
    fun `Given saveAlbumToFavourites is called, Then it marks the album as favourite`(): Unit =
        runTest {
            // Given
            val localAlbum = localAlbum
            val album = album
            whenever(localAlbumsMapper.map(album)).thenReturn(localAlbum)

            // When
            albumRepository.saveAlbumToFavourites(album)

            // Then
            verify(lastFMLocalService, times(1)).saveAlbumToFavourites(same(localAlbum))
            verify(localAlbumsMapper, times(1)).map(same(album))
        }

    @Test
    fun `Given removeAlbumFromFavourites is called, Then it removes the album from favourites`(): Unit =
        runTest {
            // Given
            val localAlbum = localAlbum
            val album = album
            whenever(localAlbumsMapper.map(album)).thenReturn(localAlbum)

            // When
            albumRepository.removeAlbumFromFavourites(album)

            // Then
            verify(lastFMLocalService, times(1)).removeAlbumFromFavourites(same(localAlbum))
            verify(localAlbumsMapper, times(1)).map(same(album))
        }

    private fun verifyNoMoreInteractions() {
        verifyNoMoreInteractions(
            lastFMLocalService,
            lastFMRemoteService,
            domainAlbumsMapper,
            localAlbumsMapper
        )
    }
}
