package com.som3a.feature.ui.contract

import com.som3a.base.UiEffect
import com.som3a.base.UiEvent
import com.som3a.base.UiState
import com.som3a.feature.model.MoviesItemUiModel

class MainContract {

    sealed class Event : UiEvent {
        object OnFetchMoviesList : Event()
        data class OnMovieSelected(val id: Int) : Event()
    }

    data class State(
        val movieState: MovieState,
        val selectedMovie: MoviesItemUiModel? = null
    ) : UiState

    sealed class MovieState {
        object Idle : MovieState()
        object Loading : MovieState()
        data class Success(val moviesList: List<MoviesItemUiModel>) : MovieState()
    }

    sealed class Effect : UiEffect {
        data class ShowError(val message: String?) : Effect()
    }
}