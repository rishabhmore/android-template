package com.wednesday.template.repo.lastfm

import com.wednesday.template.domain.lastfm.Album
import com.wednesday.template.repo.util.Mapper
import com.wednesday.template.service.lastfm.local.LocalAlbum

interface LocalAlbumsMapper : Mapper<Album, LocalAlbum>

class LocalAlbumsMapperImpl : LocalAlbumsMapper {

    override fun map(from: Album): LocalAlbum {
        return with(from) {
            LocalAlbum(
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
