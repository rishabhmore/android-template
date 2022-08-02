package com.wednesday.template.service.weather

import com.wednesday.template.service.lastfm.local.LocalAlbum
import kotlinx.coroutines.flow.Flow

interface LastFMLocalService {

    fun getFavouriteAlbumsFlow() : Flow<List<LocalAlbum>>

    suspend fun getFavouriteAlbums() : List<LocalAlbum>

    suspend fun saveAlbumToFavourites(album: LocalAlbum)

    suspend fun removeAlbumFromFavourites(album: LocalAlbum)

    suspend fun upsertSavedAlbum(album: LocalAlbum)
}