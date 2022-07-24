package com.som3a.domain.useCase.base

import com.som3a.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class FlowUseCase<Model, Params> {

    abstract suspend fun buildRequest(
        params: Params?
    ): Flow<Resource<Model>>

    suspend fun execute(type: Params?): Flow<Resource<Model>> {
        return try {
            buildRequest(type)
        } catch (exception: Exception) {
            flow { emit(Resource.Error(exception)) }
        }
    }
}