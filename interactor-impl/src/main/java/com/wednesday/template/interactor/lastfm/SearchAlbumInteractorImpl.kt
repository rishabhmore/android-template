package com.wednesday.template.interactor.lastfm

import com.wednesday.template.domain.base.Result
import com.wednesday.template.domain.lastfm.Album
import com.wednesday.template.domain.lastfm.SearchAlbumsUseCase
import com.wednesday.template.interactor.base.CoroutineContextController
import com.wednesday.template.presentation.base.UIList
import com.wednesday.template.presentation.base.UIResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber

class SearchAlbumInteractorImpl(
    private val searchAlbumsUseCase: SearchAlbumsUseCase,
    private val uiAlbumSearchResultsMapper: UIAlbumSearchResultsMapper,
    private val coroutineContextController: CoroutineContextController
) : SearchAlbumInteractor {

    private val searchResultsFlow: Channel<Result<List<Album>>> = Channel()

    override val albumResults: Flow<UIResult<UIList>> = searchResultsFlow
        .receiveAsFlow().map { albumResults ->
            when (albumResults) {
                is Result.Success -> {
                    UIResult.Success(
                        uiAlbumSearchResultsMapper.map(albumResults.data)
                    )
                }
                is Result.Error -> {
                    Timber.e(albumResults.exception)
                    UIResult.Error(albumResults.exception)
                }
                else -> {
                    error("Something went wrong")
                }
            }
        }
        .flowOn(coroutineContextController.dispatcherDefault)
        .catch { e ->
            emit(UIResult.Error(e as Exception))
        }

    override suspend fun search(query: String) : Unit = coroutineContextController.switchToDefault {
        Timber.tag(TAG).d("search: album = $query")
        val results = searchAlbumsUseCase(query)
        searchResultsFlow.send(results)
    }

    companion object {
        const val TAG = "SearchAlbumInteractorImpl"
    }
}