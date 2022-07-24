package com.som3a.domain.repository

import com.som3a.common.Resource
import com.som3a.domain.entity.Movie
import com.som3a.domain.entity.MovieDetails
import com.som3a.domain.entity.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getMoviesList(page: Int): Flow<Resource<ResultWrapper<Movie>>>
    suspend fun getMovieDetails(id:Int): Flow<Resource<MovieDetails>>
}