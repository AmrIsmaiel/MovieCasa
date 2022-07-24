package com.som3a.data.repository

import com.som3a.domain.entity.Movie
import com.som3a.domain.entity.MovieDetails
import com.som3a.domain.entity.ResultWrapper


interface RemoteDataSource {
    suspend fun getMoviesList(page: Int): ResultWrapper<Movie>
    suspend fun getMovieDetails(id: Int): MovieDetails
}