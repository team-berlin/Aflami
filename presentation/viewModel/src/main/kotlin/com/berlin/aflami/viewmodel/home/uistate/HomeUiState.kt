package com.berlin.aflami.viewmodel.home.uistate

//import com.berlin.aflami.viewmodel.search.GenreType
//import com.berlin.aflami.viewmodel.search.GenreUiState
//import com.berlin.aflami.viewmodel.search.Selectable
import arrow.optics.optics
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUIState

@optics
data class HomeUiState(

    val mediaContinueWatching: List<MediaUiState> = emptyList(),
    val upcomingMovies: UpComingUiState = UpComingUiState(),
    val topRatedMediaUiState: TopRatedMediaUiState = TopRatedMediaUiState(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

@optics
data class TopRatedMediaUiState(
    val topRatedMedia: List<MediaUiState>? = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
) {
    companion object
}

data class UpComingUiState(
    val upcomingMovies: List<MovieUIState> = emptyList(),
//    val upcomingMovieGenres: List<GenreUiState> = defaultMovieGenres,
    val isLoading: Boolean = false,
    val error: String? = null,
    val notFound: Boolean = false,
)

//val defaultMovieGenres = GenreType.entries.toTypedArray().mapIndexed { index, category ->
//    GenreUiState(
//        genres = Selectable(
//            type = category,
//            isSelected = index == 0,
//        ),
//    )
//}