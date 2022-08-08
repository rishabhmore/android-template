package com.wednesday.template.interactor.lastfm

import com.wednesday.template.domain.lastfm.Album
import com.wednesday.template.interactor.base.Mapper2
import com.wednesday.template.presentation.lastfm.UIAlbum

interface UIAlbumMapper : Mapper2<Album, Boolean, UIAlbum> {

    fun mapUIAlbum(from: UIAlbum): Album

    fun mapSavedAlbum(from: Album): UIAlbum

    fun mapSavedAlbum(from: List<Album>?): List<UIAlbum> =
        from?.map(::mapSavedAlbum) ?: emptyList()
}

class UIAlbumMapperImpl : UIAlbumMapper {

    override fun map(from1: Album, from2: Boolean): UIAlbum {
        return with(from1) {
            UIAlbum(
                albumArtist = "$name - $artist",
                artist = artist,
                name = name,
                smallImage = smallImage,
                mediumImage = mediumImage,
                xlImage = xlImage,
                url = url,
                isSaved = from2
            )
        }
    }

    override fun mapSavedAlbum(from: Album): UIAlbum {
        return with(from) {
            UIAlbum(
                albumArtist = "$name - $artist",
                artist = artist,
                name = name,
                smallImage = smallImage,
                mediumImage = mediumImage,
                xlImage = xlImage,
                url = url,
                isSaved = true
            )
        }
    }

    override fun mapUIAlbum(from: UIAlbum): Album {
        return with(from) {
            Album(
                artist = artist,
                name = name,
                smallImage = smallImage,
                mediumImage = mediumImage,
                xlImage = xlImage,
                url = url
            )
        }
    }
}
