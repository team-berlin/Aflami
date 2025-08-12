package com.berlin.aflami.viewmodel.quizgame

import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.mapper.toMediaUiState
import com.berlin.aflami.viewmodel.shareduistate.ActorUiState
import com.berlin.aflami.viewmodel.shareduistate.GenreUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.aflami.viewmodel.shareduistate.toGenreUiState
import dagger.hilt.android.lifecycle.HiltViewModel
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
), QuizGameInteractionListener {
    private val mediaList = MutableStateFlow<List<MediaUiState>>(emptyList())
    private val mediaGenre = MutableStateFlow<List<GenreUiState>>(emptyList())
    private val mediaCast = MutableStateFlow<List<ActorUiState>>(emptyList())

    init {
        viewModelScope.launch {
            mediaGame()
            getMediaByPoster()
        }
    }

    private fun getMediaByCharacter() {


    }

    //question-> poster
    // answer-> media name
    fun getMediaByPoster() {
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


    private suspend fun mediaGame() {
        updateScreenStateToLoading()
        val movie = getMovieGameUseCase()
        val tvShow = getTVShowGameUseCase()
        val movieList = movie.map { it.toMediaUiState() }
        val tvShowList = tvShow.map { it.toMediaUiState() }
        mediaList.value = interleaveMoviesAndTvShowsEqually(movieList, tvShowList)

    }

    private suspend fun genreGame() {
        updateScreenStateToLoading()
        val movieGenre = (getMovieGenresUseCase())
        val tvShowGenre = getTVGenresUseCase()
        val movieGenreList = movieGenre.map { it.toGenreUiState() }
        val tvShowGenreList = tvShowGenre.map { it.toGenreUiState() }
        mediaGenre.value = (movieGenreList + tvShowGenreList).shuffled().take(20)

    }
//
//    private suspend fun getCast(mediaId: Long) {
//        updateScreenStateToLoading()
//        val movieCast = getMovieCastUseCase()
//        val tvShowCast = getTVShowCastUseCase()
//        val movieCastList = movieCast.map { it.toActorUiState() }
//        val tvShowCastList = tvShowCast.map { it.toActorUiState() }
//        mediaCast.value = (movieCastList + tvShowCastList).shuffled()
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

    override fun nextQuestionClicked() {
        updateState {
            it.copy(currentQuestionIndex = if (it.currentQuestionIndex < it.questions.size) it.currentQuestionIndex + 1 else it.currentQuestionIndex)
        }
    }

    override fun answerClicked(answer: String) {
        updateState {
            val checkAnswer =
                it.selectedAnswer == it.questions[it.currentQuestionIndex].correctAnswer
            it.copy(
                selectedAnswer = answer,
                isAnswerCorrect = it.selectedAnswer == it.questions[it.currentQuestionIndex].correctAnswer,
                totalPoint = if (checkAnswer) it.totalPoint + 5 else it.totalPoint - 5
            )
        }
    }

    override fun hintClicked() {
        updateState {
            if (it.totalPoint >= 10) {
                it.copy(
                    enableHint = true,
                    totalPoint = it.totalPoint - 10 ,
                    imageBlur = it.imageBlur-3,
//                    questions = it.questions[it.currentQuestionIndex].copy(
//                        options =
//                    )
                )
            }
            else{
                it.copy(
                    enableHint = false,
                )
            }
        }
    }


}