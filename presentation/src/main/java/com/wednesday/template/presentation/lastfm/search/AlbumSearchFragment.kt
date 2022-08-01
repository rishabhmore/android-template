package com.wednesday.template.presentation.lastfm.search

import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import com.wednesday.template.navigation.BaseNavigator
import com.wednesday.template.presentation.base.common.HideKeyboardComponent
import com.wednesday.template.presentation.base.effect.Effect
import com.wednesday.template.presentation.base.effect.HideKeyboardEffect
import com.wednesday.template.presentation.base.effect.ShowSnackbarEffect
import com.wednesday.template.presentation.base.fragment.BindingProvider
import com.wednesday.template.presentation.base.fragment.MainFragment
import com.wednesday.template.presentation.base.snackbar.SnackbarComponent
import com.wednesday.template.presentation.base.toolbar.ToolbarComponent
import com.wednesday.template.presentation.lastfm.search.list.AlbumResultsAdapter
import com.wednesday.template.resources.databinding.FragmentAlbumSearchBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class AlbumSearchFragment : MainFragment<
    FragmentAlbumSearchBinding,
    AlbumSearchScreen,
    AlbumSearchScreenState,
    BaseNavigator,
    AlbumSearchViewModel>()
{
    private lateinit var adapter: AlbumResultsAdapter

    override val toolbarComponent: ToolbarComponent = ToolbarComponent(this, onBackClicked = {
        viewModel.onIntent(AlbumSearchScreenIntent.Back)
    })

    override val viewModel: AlbumSearchViewModel by viewModel()

    override val navigator: BaseNavigator by navigator()

    override val bindingProvider: BindingProvider<FragmentAlbumSearchBinding> =
        FragmentAlbumSearchBinding::inflate

    private val snackbarComponent by component {
        SnackbarComponent(this)
    }

    private val hideKeyboardComponent by component {
        HideKeyboardComponent(requireActivity())
    }

    override fun onViewCreated(binding: FragmentAlbumSearchBinding) {
        super.onViewCreated(binding)
        setupRecyclerView(binding)
        addTextListener(binding)
    }

    override fun onState(screenState: AlbumSearchScreenState) {
        super.onState(screenState)
        //We will fill our recyclerview with album results when the state gets updated
        adapter.differ.submitList(screenState.searchList.items)
    }

    override fun onEffect(effect: Effect) {
        when (effect) {
            is HideKeyboardEffect -> hideKeyboardComponent.setData(effect)
            is ShowSnackbarEffect -> snackbarComponent.setData(effect)
            else -> unhandledEffect(effect)
        }
    }

    private fun setupRecyclerView(binding: FragmentAlbumSearchBinding) {
        adapter = AlbumResultsAdapter()
        binding.searchAlbumsRecyclerView.adapter = adapter
    }

    private fun addTextListener(binding: FragmentAlbumSearchBinding) = with(binding) {

        searchAlbumsEditText.addTextChangedListener {
            it?.let { viewModel.onIntent(AlbumSearchScreenIntent.SearchAlbums(it.toString())) }
        }

        searchAlbumsEditText.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                onEffect(HideKeyboardEffect)
                Timber.d("User Completed Search")
            }
            false
        }
    }
}