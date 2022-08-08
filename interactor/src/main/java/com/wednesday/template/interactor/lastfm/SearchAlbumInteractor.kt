package com.wednesday.template.interactor.lastfm

import com.wednesday.template.presentation.base.UIList
import com.wednesday.template.presentation.base.UIResult
import kotlinx.coroutines.flow.Flow

interface SearchAlbumInteractor {

    val searchAlbumResults: Flow<UIResult<UIList>>

    suspend fun search(query: String)
}
