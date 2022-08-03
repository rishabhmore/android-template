package com.wednesday.template.domain.lastfm

import com.wednesday.template.repo.lastfm.AlbumRepository
import kotlinx.coroutines.flow.Flow

class GetFavouriteAlbumsFlowUseCaseImpl(
    val repository: AlbumRepository
) : GetFavouriteAlbumsFlowUseCase {

    override fun invokeInternal(param: Unit): Flow<List<Album>> {
        return repository.getFavouriteAlbumsFlow()
    }
}