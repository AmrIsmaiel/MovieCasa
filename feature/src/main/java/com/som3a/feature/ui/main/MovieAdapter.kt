package com.som3a.feature.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.som3a.base.BaseRecyclerAdapter
import com.som3a.base.BaseViewHolder
import com.som3a.feature.databinding.ItemMovieBinding
import com.som3a.feature.model.MoviesItemUiModel

class MovieAdapter constructor(
    private val onClickItem: ((MoviesItemUiModel?) -> Unit)? = null,
) : BaseRecyclerAdapter<MoviesItemUiModel, ItemMovieBinding, MovieAdapter.MovieViewHolder>(
    MovieItemDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding = binding, onClickItem = onClickItem)
    }

    inner class MovieViewHolder(
        private val binding: ItemMovieBinding,
        private val onClickItem: ((MoviesItemUiModel?) -> Unit)? = null
    ) : BaseViewHolder<MoviesItemUiModel, ItemMovieBinding>(binding) {

        override fun bind() {

            getRowItem()?.let {
                with(binding) {
                    root.setOnClickListener {
                        onClickItem?.invoke(getRowItem())
                    }
                    movieImage.apply {

                        Glide.with(this)
                            .load(it.poster_path)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(android.R.drawable.stat_notify_error)
                            .into(this)
                    }

                    movieTitleTextView.text = it.title
                    movieReleasedYearTextView.text = it.release_date
                }
            }
        }
    }
}

class MovieItemDiffUtil : DiffUtil.ItemCallback<MoviesItemUiModel>() {
    override fun areItemsTheSame(oldItem: MoviesItemUiModel, newItem: MoviesItemUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: MoviesItemUiModel,
        newItem: MoviesItemUiModel
    ): Boolean {
        return oldItem == newItem
    }
}