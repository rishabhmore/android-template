package com.wednesday.template.interactor.lastfm

import com.wednesday.template.domain.lastfm.GetFavouriteAlbumsFlowUseCase
import com.wednesday.template.domain.lastfm.RemoveFavouriteAlbumUseCase
import com.wednesday.template.domain.lastfm.SaveFavouriteAlbumUseCase
import com.wednesday.template.interactor.base.BaseInteractor
import com.wednesday.template.interactor.base.CoroutineContextController
import com.wednesday.template.interactor.base.mapToUIResult
import com.wednesday.template.presentation.base.UIList
import com.wednesday.template.presentation.base.UIResult
import com.wednesday.template.presentation.lastfm.UIAlbum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class SavedAlbumsInteractorImpl(
    private val getFavouriteAlbumsFlowUseCase: GetFavouriteAlbumsFlowUseCase,
    private val saveFavouriteAlbumsUseCase: SaveFavouriteAlbumUseCase,
    private val removeFavouriteAlbumUseCase: RemoveFavouriteAlbumUseCase,
    private val uiAlbumMapper: UIAlbumMapper,
    private val coroutineContextController: CoroutineContextController
) : BaseInteractor(), SavedAlbumsInteractor {

    override fun getSavedAlbumsUIList(): Flow<UIResult<UIList>> {
        return getFavouriteAlbumsFlowUseCase(Unit)
            .mapToUIResult(
                success = { UIList(this.data.map(uiAlbumMapper::mapSavedAlbum)) }
            )
            .flowOn(coroutineContextController.dispatcherDefault)
    }

    override suspend fun saveAlbum(uiAlbum: UIAlbum): UIResult<Unit> {
        return coroutineContextController.switchToDefault {
            val album = uiAlbumMapper.mapUIAlbum(uiAlbum)
            saveFavouriteAlbumsUseCase(album).let(::mapResult)
        }
    }

    override suspend fun removeAlbum(uiAlbum: UIAlbum): UIResult<Unit> {
        return coroutineContextController.switchToDefault {
            val album = uiAlbumMapper.mapUIAlbum(uiAlbum)
            removeFavouriteAlbumUseCase(album).let(::mapResult)
        }
    }
}
