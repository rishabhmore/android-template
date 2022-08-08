package com.wednesday.template.domain.lastfm

data class Album(
    val artist: String,
    val name: String,
    val smallImage: String?,
    val mediumImage: String?,
    val xlImage: String?,
    val url: String
)
