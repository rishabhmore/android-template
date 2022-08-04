package com.wednesday.template.domain.lastfm

import com.wednesday.template.repo.lastfm.AlbumRepository

class RemoveFavouriteAlbumUseCaseImpl(
    val repository: AlbumRepository
) : RemoveFavouriteAlbumUseCase {

    override suspend fun invokeInternal(param: Album) {
        return repository.removeAlbumFromFavourites(param)
    }
}