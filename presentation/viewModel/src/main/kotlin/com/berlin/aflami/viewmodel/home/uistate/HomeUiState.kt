package com.berlin.aflami.viewmodel.home.uistate

//import com.berlin.aflami.viewmodel.search.GenreType
//import com.berlin.aflami.viewmodel.search.GenreUiState
//import com.berlin.aflami.viewmodel.search.Selectable
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUIState


data class HomeUiState(

    val mediaContinueWatching:List<MediaUiState> = emptyList(),
    val upcomingMovies: UpComingUiState = UpComingUiState(),
    val popularMedia: PopularMediaUiState = PopularMediaUiState(),
    val isLoading: Boolean = false,
    val error: String? = null,

    )

data class UpComingUiState(
    val upcomingMovies: List<MovieUIState> = emptyList(),
//    val upcomingMovieGenres: List<GenreUiState> = defaultMovieGenres,
    val isLoading: Boolean = false,
    val error: String? = null,
    val notFound: Boolean = false,
)

data class PopularMediaUiState (
    val isLoading: Boolean = false,
    val popularMedia: List<MediaUiState> = emptyList(),
    val error: String? = null
)

//val defaultMovieGenres = GenreType.entries.toTypedArray().mapIndexed { index, category ->
//    GenreUiState(
//        genres = Selectable(
//            type = category,
//            isSelected = index == 0,
//        ),
//    )
//}