package com.wednesday.template.interactor.lastfm

import com.wednesday.template.domain.lastfm.Album
import com.wednesday.template.interactor.base.Mapper2
import com.wednesday.template.presentation.base.UIList

interface UIAlbumSearchResultsMapper : Mapper2<List<Album>, List<Album>, UIList>

class UIAlbumSearchResultsMapperImpl(
    private val uiAlbumMapper: UIAlbumMapper
) : UIAlbumSearchResultsMapper {

    override fun map(from1: List<Album>, from2: List<Album>): UIList {
        val uiAlbums = from2.map {
            val isSaved = from1.contains(it)
            uiAlbumMapper.map(it, isSaved)
        }
        return UIList(uiAlbums)
    }
}
