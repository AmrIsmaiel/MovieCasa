package com.som3a.feature.ui.main

import androidx.lifecycle.viewModelScope
import com.som3a.base.BaseViewModel
import com.som3a.common.Resource
import com.som3a.domain.useCase.GetMoviesListUseCase
import com.som3a.feature.mapper.MoviesItemUiMapper
import com.som3a.feature.model.MoviesItemUiModel
import com.som3a.feature.ui.contract.MainContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMoviesListUseCase: GetMoviesListUseCase,
    private val movieMapper: MoviesItemUiMapper
) : BaseViewModel<MainContract.Event, MainContract.State, MainContract.Effect>() {

    private val listOfMovies: MutableList<MoviesItemUiModel> = mutableListOf()
    var page = 1
    var pageCount = 0

    override fun createInitialState(): MainContract.State {
        return MainContract.State(
            movieState = MainContract.MovieState.Idle,
            selectedMovie = null
        )
    }

    override fun handleEvent(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.OnFetchMoviesList -> fetchMoviesList()
            is MainContract.Event.OnMovieSelected -> {
                event.id
            }
        }
    }

    private fun fetchMoviesList() =
        viewModelScope.launch {
            getMoviesListUseCase.execute(
                type = page
            )
                .onStart { emit(Resource.Loading) }
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            setState { copy(movieState = MainContract.MovieState.Loading) }
                        }
                        is Resource.Empty -> {
                            setState { copy(movieState = MainContract.MovieState.Idle) }
                        }
                        is Resource.Error -> {
                            setEffect { MainContract.Effect.ShowError(message = it.exception.message) }
                        }
                        is Resource.Success -> {
                            if (it.data.results != null && it.data.results?.isNotEmpty() == true) {
                                val moviesList = movieMapper.mapList(it.data.results!!)
                                listOfMovies.addAll(moviesList)
                                page += 1
                                if (it.data.total_pages != null)
                                    pageCount = it.data.total_pages!!
                            }

                            setState {
                                copy(
                                    movieState = MainContract.MovieState.Success(
                                        moviesList = listOfMovies
                                    )
                                )
                            }
                        }
                    }
                }
        }

    fun isLastPage(): Boolean {
        return page > pageCount
    }
}