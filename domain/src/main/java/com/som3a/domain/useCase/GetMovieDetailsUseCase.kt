package com.som3a.domain.useCase

import com.amosh.domain.qualifiers.IoDispatcher
import com.som3a.common.Resource
import com.som3a.domain.entity.MovieDetails
import com.som3a.domain.repository.Repository
import com.som3a.domain.useCase.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: Repository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<MovieDetails, Int>() {

    override suspend fun buildRequest(params: Int?): Flow<Resource<MovieDetails>> {
        if (params == null) {
            return flow {
                emit(Resource.Error(Exception("id can't be null")))
            }.flowOn(dispatcher)
        }
        return repository.getMovieDetails(
            id = params
        ).flowOn(dispatcher)
    }
}