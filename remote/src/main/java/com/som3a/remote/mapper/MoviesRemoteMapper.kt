package com.som3a.remote.mapper

import com.som3a.common.Mapper
import com.som3a.domain.entity.Movie
import com.som3a.remote.model.MovieRemote
import javax.inject.Inject

class MoviesRemoteMapper @Inject constructor() :
    Mapper<MovieRemote, Movie> {

    override fun map(input: MovieRemote): Movie {
        return Movie(
            posterPath = input.posterPath,
            adult = input.adult,
            overview = input.overview,
            releaseDate = input.releaseDate,
            genreIds = input.genreIds,
            id = input.id,
            originalTitle = input.originalTitle,
            originalLanguage = input.originalLanguage,
            title = input.title,
            backdropPath = input.backdropPath,
            popularity = input.popularity,
            voteCount = input.voteCount,
            video = input.video,
            voteAverage = input.voteAverage
        )
    }
}