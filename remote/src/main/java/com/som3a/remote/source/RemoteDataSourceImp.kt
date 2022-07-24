package com.som3a.remote.source

import com.som3a.data.repository.RemoteDataSource
import com.som3a.domain.entity.Movie
import com.som3a.domain.entity.MovieDetails
import com.som3a.domain.entity.ResultWrapper
import com.som3a.remote.api.ApiService
import com.som3a.remote.mapper.MovieDetailsRemoteMapper
import com.som3a.remote.mapper.MoviesRemoteMapper
import javax.inject.Inject


class RemoteDataSourceImp @Inject constructor(
    private val apiService: ApiService,
    private val movieMapper: MoviesRemoteMapper,
    private val movieDetailsRemoteMapper: MovieDetailsRemoteMapper
) : RemoteDataSource {

    override suspend fun getMoviesList(page: Int): ResultWrapper<Movie> {
        val networkData = apiService.getMovieList(page)
        val moviesList: MutableList<Movie> = mutableListOf()
        networkData.results?.forEach {
            moviesList.add(movieMapper.map(it))
        }
        return ResultWrapper(
            page = networkData.page,
            results = moviesList,
            total_results = networkData.total_results,
            total_pages = networkData.total_pages
        )
    }

    override suspend fun getMovieDetails(id: Int): MovieDetails {
        val data = apiService.getMovieDetails(id)
        return movieDetailsRemoteMapper.map(data)
    }
}