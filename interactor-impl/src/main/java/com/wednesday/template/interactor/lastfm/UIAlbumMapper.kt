package com.wednesday.template.interactor.lastfm

import com.wednesday.template.domain.lastfm.Album
import com.wednesday.template.interactor.base.Mapper
import com.wednesday.template.presentation.lastfm.UIAlbum

interface UIAlbumMapper : Mapper<Album, UIAlbum> {

    fun mapUIAlbum(from: UIAlbum): Album

}

class UIAlbumMapperImpl : UIAlbumMapper {

    override fun map(from: Album): UIAlbum {
        return with(from) {
            UIAlbum(
                albumArtist = "$name - $artist",
                artist = artist,
                name = name,
                smallImage = smallImage,
                mediumImage = mediumImage,
                xlImage = xlImage,
                url = url
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