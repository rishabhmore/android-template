package com.wednesday.template.domain.lastfm

import com.wednesday.template.repo.lastfm.AlbumRepository
import timber.log.Timber

class SearchAlbumsUseCaseImpl(
    private val albumRepository: AlbumRepository
) : SearchAlbumsUseCase {

    override suspend fun invokeInternal(param: String): List<Album> {
        Timber.tag(TAG).d("invokeInternal: param = $param")
        return albumRepository.searchAlbums(param)
    }

    companion object {
        private const val TAG = "SearchAlbumsUseCaseImpl"
    }
}