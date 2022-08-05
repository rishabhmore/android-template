package com.wednesday.template.service.lastfm.local

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "saved_albums")
data class LocalAlbum(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val artist: String,
    val name: String,
    val smallImage: String?,
    val mediumImage: String?,
    val xlImage: String?,
    val url: String
)
