package com.wednesday.template.repo.lastfm

import com.wednesday.template.domain.lastfm.Album
import com.wednesday.template.service.weather.LastFMLocalService
import com.wednesday.template.service.weather.LastFMRemoteService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AlbumRepositoryImpl(
    private val lastFMLocalService: LastFMLocalService,
    private val lastFMRemoteService: LastFMRemoteService,
    private val domainAlbumsMapper: DomainAlbumsMapper,
    private val localAlbumsMapper: LocalAlbumsMapper
) : AlbumRepository {

    override suspend fun searchAlbums(searchQuery: String): List<Album> {
        val resp = lastFMRemoteService.searchAlbums(album = searchQuery)
        val mappedResult = domainAlbumsMapper.mapRemoteAlbum(resp.results?.matches?.album)
        return mappedResult
    }

    override fun getFavouriteAlbumsFlow(): Flow<List<Album>> {
        return lastFMLocalService
            .getFavouriteAlbumsFlow()
            .map { domainAlbumsMapper.map(it) }
    }

    override suspend fun getFavouriteAlbums(): List<Album> {
        return lastFMLocalService.getFavouriteAlbums().let(domainAlbumsMapper::map)
    }

    override suspend fun saveAlbumToFavourites(album: Album) {
        lastFMLocalService.saveAlbumToFavourites(localAlbumsMapper.map(album))
    }

    override suspend fun removeAlbumFromFavourites(album: Album) {
        lastFMLocalService.removeAlbumFromFavourites(localAlbumsMapper.map(album))
    }
}
