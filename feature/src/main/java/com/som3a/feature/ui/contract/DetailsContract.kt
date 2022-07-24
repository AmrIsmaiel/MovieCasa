package com.som3a.feature.ui.contract

import com.som3a.base.UiEffect
import com.som3a.base.UiEvent
import com.som3a.base.UiState
import com.som3a.feature.model.MovieDetailsUiModel

class DetailsContract {
    sealed class Event : UiEvent {
        data class OnFetchMovie(val id: Int) : Event()
    }

    data class State(
        val movieState: MovieState,
        val selectedMovie: MovieDetailsUiModel? = null
    ) : UiState

    sealed class MovieState {
        object Idle : MovieState()
        object Loading : MovieState()
        data class Success(val movie: MovieDetailsUiModel) : MovieState()
    }

    sealed class Effect : UiEffect {
        data class ShowError(val message: String?) : Effect()
    }
}