package com.som3a.feature.mapper

import com.som3a.common.Mapper
import com.som3a.domain.entity.Movie
import com.som3a.feature.model.MoviesItemUiModel
import javax.inject.Inject

class MoviesItemUiMapper @Inject constructor() : Mapper<Movie, MoviesItemUiModel> {
    override fun map(input: Movie): MoviesItemUiModel {
        return MoviesItemUiModel(
            id = input.id ?: -1,
            adult = input.adult ?: false,
            original_title = input.originalTitle ?: "",
            overview = input.overview ?: "",
            popularity = input.popularity ?: 0.0,
            poster_path = setPosterPath(input.posterPath),
            release_date = input.releaseDate ?: "",
            original_language = input.originalLanguage ?: "",
            title = input.title ?: ""
        )
    }

    private fun setPosterPath(posterPath: String?): String {
        return if (posterPath == null) ""
        else "https://image.tmdb.org/t/p/w500/$posterPath"
    }
}