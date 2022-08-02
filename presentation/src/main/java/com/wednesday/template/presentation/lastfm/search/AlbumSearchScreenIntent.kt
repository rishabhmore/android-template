package com.wednesday.template.presentation.lastfm.search

import com.wednesday.template.presentation.base.intent.Intent

sealed interface AlbumSearchScreenIntent : Intent {

    data class SearchAlbums(
        val query: String
    ) : AlbumSearchScreenIntent

    object Back : AlbumSearchScreenIntent
}