package com.wednesday.template.domain.lastfm

import com.wednesday.template.repo.lastfm.AlbumRepository

class GetFavouriteAlbumsUseCaseImpl(
    val repository: AlbumRepository
) : GetFavouriteAlbumsUseCase {

    override suspend fun invokeInternal(param: Unit): List<Album> {
        return repository.getFavouriteAlbums()
    }
}