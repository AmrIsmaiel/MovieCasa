package com.som3a.moviecasa.application.di

import com.som3a.common.Mapper
import com.som3a.domain.entity.Movie
import com.som3a.domain.entity.MovieDetails
import com.som3a.feature.mapper.MovieDetailsUiMapper
import com.som3a.feature.mapper.MoviesItemUiMapper
import com.som3a.feature.model.MovieDetailsUiModel
import com.som3a.feature.model.MoviesItemUiModel
import com.som3a.remote.mapper.MovieDetailsRemoteMapper
import com.som3a.remote.mapper.MoviesRemoteMapper
import com.som3a.remote.model.MovieDetailsRemote
import com.som3a.remote.model.MovieRemote
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    @Binds
    abstract fun bindsMoviesRemoteMapper(mapper: MoviesRemoteMapper): Mapper<MovieRemote, Movie>

    @Binds
    abstract fun bindsMovieDetailsRemoteMapper(mapper: MovieDetailsRemoteMapper): Mapper<MovieDetailsRemote, MovieDetails>

    @Binds
    abstract fun bindsMoviesItemUiMapper(mapper:MoviesItemUiMapper): Mapper<Movie, MoviesItemUiModel>

    @Binds
    abstract fun bindsMoviesDetailsUiMapper(mapper:MovieDetailsUiMapper): Mapper<MovieDetails, MovieDetailsUiModel>

}