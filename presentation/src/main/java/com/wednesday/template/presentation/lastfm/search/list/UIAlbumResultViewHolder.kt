package com.wednesday.template.presentation.lastfm.search.list

import androidx.core.content.ContextCompat
import coil.load
import coil.size.Size
import com.wednesday.template.presentation.base.intent.Intent
import com.wednesday.template.presentation.base.list.viewholder.BaseViewHolder
import com.wednesday.template.presentation.databinding.ItemAlbumSearchBinding
import com.wednesday.template.presentation.lastfm.UIAlbum
import com.wednesday.template.presentation.lastfm.search.AlbumSearchScreenIntent
import com.wednesday.template.resources.R
import kotlinx.coroutines.channels.Channel

class UIAlbumResultViewHolder(
    private val binding: ItemAlbumSearchBinding
) : BaseViewHolder<UIAlbum>(binding) {

    override fun onSetupIntents(intentChannel: Channel<Intent>) = with(binding) {
        savedAlbumsToggle.setOnClickListener {
            val value = AlbumSearchScreenIntent.ToggleSavedAlbum(item)
            intentChannel.trySend(value)
        }
    }

    override fun onBindInternal(): Unit = binding.run {
        with(item) {
            albumName.text = name
            artistName.text = artist
            albumPreview.load(mediumImage) {
                size(Size.ORIGINAL)
            }

            compareAndSet({ isSaved }) {
                val drawable = ContextCompat.getDrawable(
                    root.context,
                    if (it) R.drawable.ic_favorite else R.drawable.ic_favorite_border
                )
                savedAlbumsToggle.setImageDrawable(drawable)
            }
        }
    }
}
