package com.wednesday.template.service.weather

interface LastFMLocalService {

    fun getFavouriteAlbumsFlow()// : Flow<List<LocalAlbumsWithImages>>

    suspend fun getFavouriteAlbums()// : List<LocalAlbumsWithImages>

    suspend fun saveAlbumToFavourites(/*album: LocalAlbum*/)

    suspend fun removeAlbumFromFavourites(/*album: LocalAlbum*/)

    suspend fun upsertSavedAlbum(/*album: LocalAlbum*/)
}