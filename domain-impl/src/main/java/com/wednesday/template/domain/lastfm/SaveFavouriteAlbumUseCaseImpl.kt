package com.wednesday.template.domain.lastfm

import com.wednesday.template.repo.lastfm.AlbumRepository

class SaveFavouriteAlbumUseCaseImpl(
    val repository: AlbumRepository
) : SaveFavouriteAlbumUseCase {

    override suspend fun invokeInternal(param: Album) {
        return repository.saveAlbumToFavourites(param)
    }
}
