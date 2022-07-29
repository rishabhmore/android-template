package com.wednesday.template.repo.lastfm

import com.wednesday.template.domain.lastfm.Album
import com.wednesday.template.service.weather.LastFMRemoteService

class AlbumRepositoryImpl(
    private val lastFMRemoteService: LastFMRemoteService,
    private val domainAlbumsMapper: DomainAlbumsMapper
): AlbumRepository {

    override suspend fun searchAlbums(searchQuery: String): List<Album> {
        val resp = lastFMRemoteService.searchAlbums(album = searchQuery)
        val mappedResult = domainAlbumsMapper.map(resp.results?.matches?.album)
        return mappedResult
    }

}