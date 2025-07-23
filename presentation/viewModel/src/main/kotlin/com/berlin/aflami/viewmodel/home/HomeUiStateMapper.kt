package com.berlin.aflami.viewmodel.home

import android.annotation.SuppressLint
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import com.berlin.entity.Movie

class HomeUiStateMapper {

    @SuppressLint("DefaultLocale")
    fun movieToMovieUiState(movie: Movie): MovieUIState {
        return MovieUIState(
            id = movie.id,
            title = movie.title,
            rating = String.format("%.1f", movie.rating),
            poster = movie.poster,
            releaseYear = movie.releaseYear.toString()
        )
    }

    fun moviesToMoviesUiState(movies: List<Movie>) = movies.map(::movieToMovieUiState)
}