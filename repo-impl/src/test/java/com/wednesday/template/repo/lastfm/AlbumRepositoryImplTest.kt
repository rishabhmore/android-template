package com.wednesday.template.repo.lastfm

import com.wednesday.template.domain.lastfm.Album
import com.wednesday.template.repo.lastfm.models.album
import com.wednesday.template.repo.lastfm.models.remoteAlbum
import com.wednesday.template.repo.lastfm.models.remoteAlbumResults
import com.wednesday.template.service.lastfm.remote.RemoteAlbum
import com.wednesday.template.service.weather.LastFMRemoteService
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import kotlin.test.assertEquals

class AlbumRepositoryImplTest {

    private lateinit var lastFMRemoteService: LastFMRemoteService
    private lateinit var domainAlbumsMapper: DomainAlbumsMapper
    private lateinit var albumRepository: AlbumRepositoryImpl

    @Before
    fun setup() {
        lastFMRemoteService = mock()
        domainAlbumsMapper = mock()
        albumRepository = AlbumRepositoryImpl(lastFMRemoteService, domainAlbumsMapper)
    }

    @Test
    fun `Given a search string, When searchAlbums is called, Then list of albums is returned`(): Unit =
        runTest {
            //Given
            val searchTerm = "magnatron"
            val remoteAlbums: List<RemoteAlbum> = listOf(remoteAlbum)
            val domainAlbums: List<Album> = listOf(album)
            val remoteAlbumsResponse = remoteAlbumResults
            whenever(lastFMRemoteService.searchAlbums(album = searchTerm))
                .thenReturn(remoteAlbumsResponse)
            whenever(domainAlbumsMapper.map(same(remoteAlbumsResponse.results?.matches?.album)))
                .thenReturn(domainAlbums)

            //When
            val result = albumRepository.searchAlbums(searchTerm)

            //Then
            assertEquals(expected = listOf(album), actual = result)
            verify(lastFMRemoteService, times(1)).searchAlbums(album = searchTerm)
            verify(domainAlbumsMapper, times(1))
                .map(remoteAlbumsResponse.results?.matches?.album)
            verifyNoMoreInteractions()
        }

    private fun verifyNoMoreInteractions() {
        org.mockito.kotlin.verifyNoMoreInteractions(
            lastFMRemoteService
        )
    }
}