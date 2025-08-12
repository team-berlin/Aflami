package com.berlin.aflami.viewmodel.quizgame

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.mapper.toActorUiState
import com.berlin.aflami.viewmodel.mapper.toMediaUiState
import com.berlin.aflami.viewmodel.shareduistate.ActorUiState
import com.berlin.aflami.viewmodel.shareduistate.GenreUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.aflami.viewmodel.shareduistate.toGenreUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import usecase.movie.GetMovieCastUseCase
import usecase.movie.GetMovieGameUseCase
import usecase.movie.GetMovieGenresUseCase
import usecase.tvshow.GetTVShowCastUseCase
import usecase.tvshow.GetTVShowGameUseCase
import usecase.tvshow.GetTVShowGenresUseCase
import javax.inject.Inject

@HiltViewModel
class QuizGameViewModel @Inject constructor(
    private val getMovieGameUseCase: GetMovieGameUseCase,
    private val getTVShowGameUseCase: GetTVShowGameUseCase,
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
    private val getTVGenresUseCase: GetTVShowGenresUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val getTVShowCastUseCase: GetTVShowCastUseCase
) : BaseViewModel<QuizGameUiState, QuizGameEffect>(
    QuizGameUiState()
) {
    private val mediaList = MutableStateFlow<List<MediaUiState>>(emptyList())
    private val mediaGenre = MutableStateFlow<List<GenreUiState>>(emptyList())
    private val mediaCast = MutableStateFlow<List<ActorUiState>>(emptyList())

    init {
        mediaGame()
    }



    private fun getMediaByCharacter() {


    }

    //question-> poster
    // answer-> media name
     fun getMediaByPoster() {
        val mediaItems = mediaList.value
        Log.e("meddiaa",mediaItems.toString())

        if (mediaItems.isEmpty()) return
        val questions = mediaItems.map { media ->
            val wrongOptions = mediaItems.asSequence()
                .filter { it.id != media.id }
                .map { it.title }
                .take(3)
                .toList()
            val allOptions = (wrongOptions + media.title).shuffled()
            Log.e("Posterrr",allOptions.toString())

            Question(
                question = media.poster,
                options = allOptions,
                correctAnswer = media.title
            )

        }
        updateState { it.copy(questions = questions, loading = false) }
    }

    //question-> media name
    // answer-> release date
    private fun getMediaByReleaseDate() {
        val mediaItems = mediaList.value
        if (mediaItems.isEmpty()) return
        val questions = mediaItems.map { media ->
            val wrongOptions = mediaItems.asSequence()
                .filter { it.id != media.id }
                .map { it.title }
                .take(3)
                .toList()
            val allOptions = (wrongOptions + media.title).shuffled()
            Question(
                question = media.releaseYear,
                options = allOptions,
                correctAnswer = media.title
            )
        }
        updateState { it.copy(questions = questions, loading = false) }
    }

    //question-> media name
    // answer -> genre
    private fun getMediaByGenres() {
        val mediaItems = mediaList.value
        val genreItems = mediaGenre.value

        if (mediaItems.isEmpty() || genreItems.isEmpty()) return
        val questions = mediaItems.map { media ->
            val mediaGenres = media.genre
                .mapNotNull { id -> genreItems.find { it.id == id }?.name }

            if (mediaGenres.isEmpty()) return
            val correctAnswer = mediaGenres.random()
            val wrongOptions = genreItems
                .filter { it.name !in mediaGenres }
                .map { it.name }
                .shuffled()
                .take(3)
            val allOptions = (wrongOptions + correctAnswer).shuffled()
            Question(
                question = media.title,
                options = allOptions,
                correctAnswer = correctAnswer
            )
        }
        updateState { it.copy(questions = questions, loading = false) }
    }


    private fun mediaGame() {
        updateScreenStateToLoading()
        viewModelScope.launch {
            val movie = async { getMovieGameUseCase() }
            val tvShow = async { getTVShowGameUseCase() }
            val movieList = movie.await().map { it.toMediaUiState() }
            val tvShowList = tvShow.await().map { it.toMediaUiState() }
            mediaList.value = interleaveMoviesAndTvShowsEqually(movieList, tvShowList)
            Log.e("mediaaaa",mediaList.value.toString())
            getMediaByPoster()


        }
    }

    private fun genreGame() {
        updateScreenStateToLoading()
        viewModelScope.launch {
            val movieGenre = async { (getMovieGenresUseCase()) }
            val tvShowGenre = async { getTVGenresUseCase() }
            val movieGenreList = movieGenre.await().map { it.toGenreUiState() }
            val tvShowGenreList = tvShowGenre.await().map { it.toGenreUiState() }
            mediaGenre.value = (movieGenreList + tvShowGenreList).shuffled().take(20)

        }
    }

//    private fun getCast(){
//        updateScreenStateToLoading()
//        viewModelScope.launch {
//            val movieCast = async { getMovieCastUseCase() }
//            val tvShowCast = async { getTVShowCastUseCase() }
//            val movieCastList = movieCast.await().map { it.toActorUiState() }
//            val tvShowCastList = tvShowCast.await().map { it.toActorUiState() }
//            mediaCast.value = (movieCastList + tvShowCastList).shuffled()
//        }
//    }

    private fun interleaveMoviesAndTvShowsEqually(
        movies: List<MediaUiState>,
        tvShows: List<MediaUiState>,
    ): List<MediaUiState> {
        val arrangedList = mutableListOf<MediaUiState>()

        //TODO() question count
        repeat(20) { counter ->
            with(arrangedList) {
                add(movies[counter])
                add(tvShows[counter])
            }
        }
        return arrangedList
    }

    private fun updateScreenStateToLoading() =
        updateState { screenState -> screenState.copy(loading = true) }


}