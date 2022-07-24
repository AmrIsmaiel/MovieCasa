package com.som3a.remote.api

import com.som3a.domain.entity.ResultWrapper
import com.som3a.remote.BuildConfig
import com.som3a.remote.model.MovieDetailsRemote
import com.som3a.remote.model.MovieRemote
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieDetailsRemote

    @GET("discover/movie")
    suspend fun getMovieList(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): ResultWrapper<MovieRemote>
}