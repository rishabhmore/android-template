package com.wednesday.template.presentation.lastfm.search

import com.wednesday.template.presentation.base.intent.Intent
import com.wednesday.template.presentation.lastfm.UIAlbum

sealed interface AlbumSearchScreenIntent : Intent {

    data class SearchAlbums(
        val query: String
    ) : AlbumSearchScreenIntent

    data class ToggleSavedAlbum(
        val album: UIAlbum
    ) : AlbumSearchScreenIntent

    object Back : AlbumSearchScreenIntent
}
