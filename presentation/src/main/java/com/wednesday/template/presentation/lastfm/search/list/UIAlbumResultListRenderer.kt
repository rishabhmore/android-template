package com.wednesday.template.presentation.lastfm.search.list

import android.view.ViewGroup
import com.wednesday.template.presentation.base.list.renderer.ListItemRenderer
import com.wednesday.template.presentation.base.list.viewholder.BaseViewHolder
import com.wednesday.template.presentation.databinding.ItemAlbumSearchBinding
import com.wednesday.template.presentation.lastfm.UIAlbum

class UIAlbumResultListRenderer : ListItemRenderer<UIAlbum>() {

    override fun getViewHolder(viewGroup: ViewGroup): BaseViewHolder<UIAlbum> {
        return UIAlbumResultViewHolder(
            binding = viewGroup bind ItemAlbumSearchBinding::inflate
        )
    }
}