package com.wednesday.template.interactor.lastfm

import com.wednesday.template.domain.base.Result
import com.wednesday.template.domain.lastfm.Album
import com.wednesday.template.domain.lastfm.GetFavouriteAlbumsFlowUseCase
import com.wednesday.template.domain.lastfm.SearchAlbumsUseCase
import com.wednesday.template.interactor.base.CoroutineContextController
import com.wednesday.template.presentation.base.UIList
import com.wednesday.template.presentation.base.UIResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber

class SearchAlbumInteractorImpl(
    private val searchAlbumsUseCase: SearchAlbumsUseCase,
    favouriteAlbumsFlowUseCase: GetFavouriteAlbumsFlowUseCase,
    private val uiAlbumSearchResultsMapper: UIAlbumSearchResultsMapper,
    private val coroutineContextController: CoroutineContextController
) : SearchAlbumInteractor {

    private val searchResultStateFlow = Channel<List<Album>>()

    override val searchAlbumResults: Flow<UIResult<UIList>> = favouriteAlbumsFlowUseCase(Unit)
        .combine(searchResultStateFlow.receiveAsFlow()) { favouriteAlbums, searchAlbumResults ->
            when {
                searchAlbumResults.isEmpty() -> {
                    UIResult.Success(UIList())
                }
                favouriteAlbums is Result.Success -> {
                    UIResult.Success(
                        uiAlbumSearchResultsMapper.map(favouriteAlbums.data, searchAlbumResults)
                    )
                }
                favouriteAlbums is Result.Error -> {
                    Timber.e(favouriteAlbums.exception)
                    UIResult.Error(favouriteAlbums.exception)
                }
                else -> error("Something went wrong")
            }
        }
        .flowOn(coroutineContextController.dispatcherDefault)
        .catch { e ->
            emit(UIResult.Error(e as Exception))
        }

    override suspend fun search(query: String): Unit = coroutineContextController.switchToDefault {
        Timber.tag(TAG).d("search: album = $query")
        val list = when (val results = searchAlbumsUseCase(query)) {
            is Result.Error -> emptyList()
            is Result.Success -> results.data
        }
        searchResultStateFlow.send(list)
    }

    companion object {
        const val TAG = "SearchAlbumInteractorImpl"
    }
}
