package com.wednesday.template.presentation.lastfm.search

import com.wednesday.template.presentation.base.UIList
import com.wednesday.template.presentation.base.UIToolbar
import com.wednesday.template.presentation.screen.MainScreenState
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumSearchScreenState(
    override val toolbar: UIToolbar,
    override val showLoading: Boolean,
    val searchList: UIList = UIList()
) : MainScreenState
