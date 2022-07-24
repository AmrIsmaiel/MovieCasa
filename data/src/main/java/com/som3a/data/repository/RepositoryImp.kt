package com.som3a.data.repository

import com.som3a.common.Resource
import com.som3a.domain.entity.Movie
import com.som3a.domain.entity.MovieDetails
import com.som3a.domain.entity.ResultWrapper
import com.som3a.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class RepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : Repository {

    override suspend fun getMoviesList(
        page: Int
    ): Flow<Resource<ResultWrapper<Movie>>> {
        return flow {
            val data = remoteDataSource.getMoviesList(page)
            emit(Resource.Success(data))
        }
    }

    override suspend fun getMovieDetails(id: Int): Flow<Resource<MovieDetails>> {
        return flow {
            val data = remoteDataSource.getMovieDetails(id)
            emit(Resource.Success(data))
        }
    }
}