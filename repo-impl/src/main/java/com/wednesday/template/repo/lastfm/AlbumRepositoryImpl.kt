package com.wednesday.template.repo.lastfm

import com.wednesday.template.domain.lastfm.Album
import com.wednesday.template.service.weather.LastFMRemoteService

class AlbumRepositoryImpl(
    private val lastFMRemoteService: LastFMRemoteService,
    private val domainAlbumsMapper: DomainAlbumsMapper
): AlbumRepository {

    override suspend fun searchAlbums(searchQuery: String): List<Album> {
        return lastFMRemoteService.searchAlbums(album = searchQuery)
            .let { response -> domainAlbumsMapper.map(response.results?.matches?.album) }
    }

}