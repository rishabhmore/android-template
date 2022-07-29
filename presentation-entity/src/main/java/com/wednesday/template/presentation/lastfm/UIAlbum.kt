package com.wednesday.template.presentation.lastfm

import com.wednesday.template.presentation.base.UIListItemBase
import kotlinx.parcelize.Parcelize

@Parcelize
data class UIAlbum(
    val albumArtist: String,
    val artist: String,
    val name: String,
    val smallImage: String?,
    val mediumImage: String?,
    val xlImage:String?,
    val url: String
) : UIListItemBase(id = "Album $name")
