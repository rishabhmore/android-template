package com.wednesday.template.presentation.lastfm.search.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Size
import com.wednesday.template.presentation.base.UIListItemBase
import com.wednesday.template.presentation.databinding.ItemAlbumSearchBinding
import com.wednesday.template.presentation.lastfm.UIAlbum

class AlbumResultsAdapter : RecyclerView.Adapter<AlbumResultsAdapter.AlbumResultsViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<UIListItemBase>() {

        override fun areItemsTheSame(
            oldItem: UIListItemBase, newItem: UIListItemBase
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: UIListItemBase, newItem: UIListItemBase
        ): Boolean = oldItem == newItem
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumResultsViewHolder {
        return AlbumResultsViewHolder(
            ItemAlbumSearchBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(
        holder: AlbumResultsViewHolder, position: Int
    ) = holder.bind(differ.currentList[position] as UIAlbum)

    inner class AlbumResultsViewHolder(
        val binding: ItemAlbumSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(album: UIAlbum) {
            binding.albumName.text = album.name
            binding.artistName.text = album.artist
            binding.albumPreview.load(album.mediumImage) {
                size(Size.ORIGINAL)
            }
        }
    }
}