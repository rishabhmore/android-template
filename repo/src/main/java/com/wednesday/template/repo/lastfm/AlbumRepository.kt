package com.wednesday.template.repo.lastfm

import com.wednesday.template.domain.lastfm.Album
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {

    suspend fun searchAlbums(searchQuery: String): List<Album>

    fun getFavouriteAlbumsFlow(): Flow<List<Album>>

    suspend fun getFavouriteAlbums(): List<Album>

    suspend fun saveAlbumToFavourites(album: Album)

    suspend fun removeAlbumFromFavourites(album: Album)
}
