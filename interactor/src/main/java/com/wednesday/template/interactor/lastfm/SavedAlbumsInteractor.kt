package com.wednesday.template.interactor.lastfm

import com.wednesday.template.presentation.base.UIList
import com.wednesday.template.presentation.base.UIResult
import com.wednesday.template.presentation.lastfm.UIAlbum
import kotlinx.coroutines.flow.Flow

interface SavedAlbumsInteractor {

    suspend fun saveAlbum(uiAlbum: UIAlbum): UIResult<Unit>

    suspend fun removeAlbum(uiAlbum: UIAlbum): UIResult<Unit>

    fun getSavedAlbumsUIList(): Flow<UIResult<UIList>>
}
