package com.wednesday.template.domain.lastfm

class SearchAlbumsUseCaseImpl() : SearchAlbumsUseCase {

    override suspend fun invokeInternal(param: String): List<Album> {
        TODO("Not yet implemented")
    }

    companion object {
        private const val TAG = "SearchAlbumsUseCaseImpl"
    }
}