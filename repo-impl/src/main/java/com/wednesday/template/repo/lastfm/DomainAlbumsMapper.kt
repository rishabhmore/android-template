package com.wednesday.template.repo.lastfm

import com.wednesday.template.domain.lastfm.Album
import com.wednesday.template.repo.util.Mapper
import com.wednesday.template.service.lastfm.remote.RemoteAlbum

interface DomainAlbumsMapper : Mapper<RemoteAlbum, Album>

class DomainAlbumsMapperImpl : DomainAlbumsMapper {

    override fun map(from: RemoteAlbum): Album {
        return with(from) {
            Album(
                artist = artist,
                name = name,
                smallImage = image.firstOrNull { it.size == "small" }?.text,
                mediumImage = image.firstOrNull { it.size == "medium" }?.text,
                xlImage = image.firstOrNull { it.size == "extralarge" }?.text,
                url = url
            )
        }
    }
}