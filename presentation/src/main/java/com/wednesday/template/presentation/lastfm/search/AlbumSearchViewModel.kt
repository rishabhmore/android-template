package com.wednesday.template.presentation.lastfm.search

import androidx.lifecycle.viewModelScope
import com.wednesday.template.interactor.lastfm.SavedAlbumsInteractor
import com.wednesday.template.interactor.lastfm.SearchAlbumInteractor
import com.wednesday.template.navigation.BaseNavigator
import com.wednesday.template.presentation.R
import com.wednesday.template.presentation.base.UIList
import com.wednesday.template.presentation.base.UIResult
import com.wednesday.template.presentation.base.UIText
import com.wednesday.template.presentation.base.UIToolbar
import com.wednesday.template.presentation.base.effect.ShowSnackbarEffect
import com.wednesday.template.presentation.base.intent.IntentHandler
import com.wednesday.template.presentation.base.viewmodel.BaseViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class AlbumSearchViewModel(
    private val savedAlbumsInteractor: SavedAlbumsInteractor,
    private val searchAlbumInteractor: SearchAlbumInteractor
) : BaseViewModel<AlbumSearchScreen, AlbumSearchScreenState, BaseNavigator>(),
    IntentHandler<AlbumSearchScreenIntent>
{
    /**
     * This state flow is like a search queue that is delayed by 500 ms
     * so that we can have real-time search without having to send request to api
     * for every keystore of soft input keyboard
     */
    private val albumSearchStringQueue: MutableStateFlow<String> = MutableStateFlow("")

    override fun getDefaultScreenState(): AlbumSearchScreenState {
        return AlbumSearchScreenState(
            toolbar = UIToolbar(
                title = UIText { block("Search Albums") },
                hasBackButton = true,
                menuIcon = null
            ),
            showLoading = false,
            searchList = UIList()
        )
    }

    override fun onCreate(fromRecreate: Boolean) {
        searchAlbumInteractor.searchAlbumResults.onEach {
            when (it) {
                is UIResult.Success -> {
                    setState { copy(showLoading = false, searchList = it.data) }
                }
                is UIResult.Error -> {
                    setState { copy(showLoading = false) }
                    setEffect(
                        ShowSnackbarEffect(
                            message = UIText { block(R.string.something_went_wrong) }
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)

        //initialize the search queue with a debounce of 500 ms
        albumSearchStringQueue
            .debounce(500)
            //we will trim our search query from any spaces left by soft input keyboard's
            // auto suggestions, likeGoogle Keyboard does
            .map { it.trim() }
            .onEach {

                //Initially we will not have anything to search,
                // so our initial flow will always be an empty string
                if (it.isBlank()) {
                    setState { copy(searchList = UIList()) }
                    return@onEach
                }

                setState {
                    copy(showLoading = true)
                }
                searchAlbumInteractor.search(it)
            }
            .launchIn(viewModelScope)
    }

    override fun onIntent(intent: AlbumSearchScreenIntent) {
        when (intent) {
            is AlbumSearchScreenIntent.SearchAlbums -> {
                viewModelScope.launch {
                    //Send our string query to the search queue
                    albumSearchStringQueue.value = intent.query
                }
            }
            is AlbumSearchScreenIntent.ToggleSavedAlbum -> {
                viewModelScope.launch {
                    if(intent.album.isSaved){
                        //If already saved, then we will remove it
                        savedAlbumsInteractor.removeAlbum(intent.album)
                    } else {
                        //We add this album to our saved list
                        savedAlbumsInteractor.saveAlbum(intent.album)
                    }
                }
            }
            is AlbumSearchScreenIntent.Back -> navigator.back()
        }
    }
}