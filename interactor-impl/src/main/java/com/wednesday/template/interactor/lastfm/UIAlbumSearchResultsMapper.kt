package com.wednesday.template.interactor.lastfm

import com.wednesday.template.domain.lastfm.Album
import com.wednesday.template.interactor.base.Mapper
import com.wednesday.template.presentation.base.UIList

interface UIAlbumSearchResultsMapper : Mapper<List<Album>, UIList>

class UIAlbumSearchResultsMapperImpl(
    private val uiAlbumMapper: UIAlbumMapper
) : UIAlbumSearchResultsMapper {

    override fun map(from: List<Album>): UIList {
        return UIList(from.map { uiAlbumMapper.map(it) })
    }
}