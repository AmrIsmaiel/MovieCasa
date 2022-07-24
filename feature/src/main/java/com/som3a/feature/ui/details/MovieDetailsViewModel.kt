package com.som3a.feature.ui.details

import androidx.lifecycle.viewModelScope
import com.som3a.base.BaseViewModel
import com.som3a.common.Resource
import com.som3a.domain.useCase.GetMovieDetailsUseCase
import com.som3a.feature.mapper.MovieDetailsUiMapper
import com.som3a.feature.ui.contract.DetailsContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val movieDetailsUiMapper: MovieDetailsUiMapper
) : BaseViewModel<DetailsContract.Event, DetailsContract.State, DetailsContract.Effect>() {
    override fun createInitialState(): DetailsContract.State {
        return DetailsContract.State(
            movieState = DetailsContract.MovieState.Idle,
            selectedMovie = null
        )
    }

    override fun handleEvent(event: DetailsContract.Event) {
        when (event) {
            is DetailsContract.Event.OnFetchMovie -> {fetchMovieDetails(event.id)}
        }
    }

    private fun fetchMovieDetails(id: Int) =
        viewModelScope.launch {
            getMovieDetailsUseCase.execute(
                type = id
            )
                .onStart { emit(Resource.Loading) }
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            setState { copy(movieState = DetailsContract.MovieState.Loading) }
                        }
                        is Resource.Empty -> {
                            setState { copy(movieState = DetailsContract.MovieState.Idle) }
                        }
                        is Resource.Error -> {
                            setEffect { DetailsContract.Effect.ShowError(message = it.exception.message) }
                        }
                        is Resource.Success -> {

                            setState {
                                copy(
                                    movieState = DetailsContract.MovieState.Success(
                                        movie = movieDetailsUiMapper.map(it.data)
                                    )
                                )
                            }
                        }
                    }
                }
        }
}

