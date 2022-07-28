package com.wednesday.template.repo.lastfm

import com.wednesday.template.domain.lastfm.Album

interface AlbumRepository {

    suspend fun searchAlbums(searchQuery: String): List<Album>

}