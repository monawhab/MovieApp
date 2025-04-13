package com.movieapp.presentation.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.movieapp.R
import com.movieapp.databinding.ItemListMoviesBinding
import com.movieapp.domain.dto.MovieDto
import com.movieapp.utils.BasePagingAdapter
import com.movieapp.utils.loadImage


class MoviesPagingAdapter(private val onFavClick: (movieDto: MovieDto) -> Unit) : BasePagingAdapter<MovieDto, ItemListMoviesBinding>(DiffCallback()) {

    override fun getLayoutInflater(parent: ViewGroup): ItemListMoviesBinding {
        return ItemListMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ItemListMoviesBinding>, position: Int) {
        val currentItem = getItem(position)

        holder.binding.apply {
            titleTV.text = currentItem?.title
            releaseDateTV.text = currentItem?.date
            posterIV.loadImage(currentItem?.posterPath.orEmpty())
            favIV.setImageResource(if (currentItem!!.isFav) R.drawable.fav else R.drawable.un_fav)
            favIV.setOnClickListener {
                onFavClick.invoke(currentItem)
                notifyItemChanged(position)
            }
        }

        holder.itemView.setOnClickListener {
            listener?.invoke(it, currentItem!!, position)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MovieDto>() {
        override fun areItemsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean {
            return oldItem.movieId == newItem.movieId
        }

        override fun areContentsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean {
            return oldItem == newItem
        }
    }
}