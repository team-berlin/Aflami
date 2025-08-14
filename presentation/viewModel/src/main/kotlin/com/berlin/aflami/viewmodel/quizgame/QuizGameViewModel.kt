package com.berlin.aflami.viewmodel.quizgame

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.game.GameType
import com.berlin.aflami.viewmodel.mapper.toActorUiState
import com.berlin.aflami.viewmodel.mapper.toMediaUiState
import com.berlin.aflami.viewmodel.shareduistate.ActorUiState
import com.berlin.aflami.viewmodel.shareduistate.GenreUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.aflami.viewmodel.shareduistate.toGenreUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import usecase.game.AddPointsUseCase
import usecase.game.GetPointsUseCase
import usecase.movie.GetMovieCastUseCase
import usecase.movie.GetMovieGameUseCase
import usecase.movie.GetMovieGenresUseCase
import usecase.profile.ObserveUserProfileUseCase
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
    private val getTVShowCastUseCase: GetTVShowCastUseCase,
    private val savePoint: AddPointsUseCase,
    private val getPointsUseCase: GetPointsUseCase,
    private val observeUserProfileUseCase: ObserveUserProfileUseCase,
    guessGameScreenArgs: GuessGameScreenArgs
) : BaseViewModel<QuizGameUiState, QuizGameEffect>(
    QuizGameUiState()
), QuizGameInteractionListener {
    private val timer = guessGameScreenArgs.timer ?: 0
    private val gameType = guessGameScreenArgs.gameType ?: GameType.POSTER
    private val numberOfQuestion = guessGameScreenArgs.numberOfQuestion ?: 0
    private val numberOfPoints = guessGameScreenArgs.numberOfPoint ?: 0

    init {
        updateState {
            it.copy(
                numberOfPoint = numberOfPoints,
            )
        }

        viewModelScope.launch {
            handleGameType(gameType)
        }
    }

    private fun handleCharacterGame() {
        tryToCall(
            call = { castGame() },
            onSuccess = { getMediaByCharacter() },
            onError = ::updateScreenStateToError
        )
    }

    private fun handlePosterGame() {
        tryToCall(
            call = { mediaGame() },
            onSuccess = { getMediaByPoster() },
            onError = ::updateScreenStateToError
        )
    }

    private fun handleReleaseGame() {
        tryToCall(
            call = { mediaGame() },
            onSuccess = { getMediaByReleaseDate() },
            onError = ::updateScreenStateToError
        )
    }

    private fun handleGenreGame() {
        tryToCall(
            call = { genreGame() },
            onSuccess = { getMediaByGenres() },
            onError = ::updateScreenStateToError
        )
    }

    private fun handleGameType(gameType: GameType) {
        when (gameType) {
            GameType.CHARACTER -> {
                handleCharacterGame()
            }

            GameType.POSTER -> {
                handlePosterGame()
            }

            GameType.RELEASE -> {
                handleReleaseGame()
            }

            GameType.GENRE -> {
                handleGenreGame()
            }
        }

    }

    private fun getMediaByCharacter() {
        val mediaItem = state.value.cast.take(numberOfQuestion)

        if (mediaItem.isEmpty()) return
        val questions = mediaItem.map { media ->
            val wrongOptions = mediaItem.asSequence()
                .filter { it.mediaId == media.mediaId && it.name != media.name }
                .map { it.name }
                .take(3)
                .toList()
            val allOptions = (wrongOptions + media.name).shuffled()
            Question(
                question = media.poster,
                options = allOptions,
                correctAnswer = media.name
            )
        }
        updateState {
            it.copy(
                questions = questions,
                loading = false,
                type = QuestionType.Image,
                gameTypeName = gameType,
                time = timer
            )
        }
        Log.e("questions", questions.toString())
    }

    //question-> poster
    // answer-> media name
    private fun getMediaByPoster() {
        val mediaList = state.value.mediaList
        if (mediaList.isEmpty()) return

        val questions = mediaList.map { currentMedia ->
            val wrongOptions = mediaList.asSequence()
                .filter { it.id != currentMedia.id }
                .map { it.title }
                .shuffled()
                .take(3)
                .toList()

            val allOptions = (wrongOptions + currentMedia.title).shuffled()

            Question(
                question = currentMedia.poster,
                options = allOptions,
                correctAnswer = currentMedia.title
            )
        }

        updateState {
            it.copy(
                questions = questions,
                loading = false,
                type = QuestionType.Image,
                gameTypeName = gameType,
                time = timer
            )
        }
    }

    //question-> media name
    // answer-> release date
    private fun getMediaByReleaseDate() {
        val mediaList = state.value.mediaList
        if (mediaList.isEmpty()) return

        val questions = mediaList.map { media ->
            val correctYear = media.releaseYear

            val wrongOptions = mediaList
                .asSequence()
                .map { it.releaseYear }
                .filter { it != correctYear }
                .distinct()
                .shuffled()
                .take(3)
                .toList()

            val allOptions = (wrongOptions + correctYear).shuffled()

            Question(
                question = media.title,
                options = allOptions,
                correctAnswer = correctYear
            )
        }

        updateState {
            it.copy(
                questions = questions,
                loading = false,
                type = QuestionType.Text,
                gameTypeName = gameType,
                time = timer
            )
        }
    }


    //question-> media name
    // answer -> genre
    private fun getMediaByGenres() {
        val mediaList = state.value.mediaList
        val genreItems = state.value.genreList

        if (mediaList.isEmpty() || genreItems.isEmpty()) return
        val questions = mediaList.map { media ->
            val mediaGenres = media.genre
                .mapNotNull { id -> genreItems.find { it.id == id }?.name }

            if (mediaGenres.isEmpty()) return
            val correctAnswer = mediaGenres.random()
            val wrongOptions = genreItems.asSequence()
                .filter { it.name !in mediaGenres }
                .map { it.name }
                .shuffled()
                .take(3)
                .toList()
            val allOptions = (wrongOptions + correctAnswer).shuffled()
            Question(
                question = media.title,
                options = allOptions,
                correctAnswer = correctAnswer
            )
        }
        updateState {
            it.copy(
                questions = questions,
                loading = false,
                type = QuestionType.Text,
                gameTypeName = gameType,
                time = timer
            )
        }
    }

    private suspend fun fetchMovies(): List<MediaUiState> =
        getMovieGameUseCase().map { it.toMediaUiState() }

    private suspend fun fetchTvShows(): List<MediaUiState> =
        getTVShowGameUseCase().map { it.toMediaUiState() }

    private fun mediaGame() {
        updateScreenStateToLoading()
        tryToCall(
            call = {
                coroutineScope {
                    val moviesDeferred = async { fetchMovies() }
                    val tvShowsDeferred = async { fetchTvShows() }

                    val movies = moviesDeferred.await()
                    val shows = tvShowsDeferred.await()

                    (movies + shows).shuffled().take(numberOfQuestion)
                }
            },
            onSuccess = { mediaList ->
                updateState {
                    it.copy(
                        mediaList = mediaList,
                        loading = false
                    )
                }
            },
            onError = ::updateScreenStateToError,
        )
    }

    private suspend fun fetchMovieGenre(): List<GenreUiState> =
        getMovieGenresUseCase().map { it.toGenreUiState() }

    private suspend fun fetchTvShowGenre(): List<GenreUiState> =
        getTVGenresUseCase().map { it.toGenreUiState() }

    private fun genreGame() {
        updateScreenStateToLoading()
        tryToCall(
            call = {
                coroutineScope {
                    val moviesGenreDeferred = async { fetchMovieGenre() }
                    val tvShowsGenreDeferred = async { fetchTvShowGenre() }

                    val movies = moviesGenreDeferred.await()
                    val shows = tvShowsGenreDeferred.await()

                    (movies + shows).shuffled().take(numberOfQuestion)
                }
            },
            onSuccess = { genreList ->
                updateState {
                    it.copy(
                        genreList = genreList,
                        loading = false
                    )
                }
            },
            onError = ::updateScreenStateToError,
        )
    }


    private suspend fun fetchMovieCast(movieId: Long): List<ActorUiState> {
        return getMovieCastUseCase(movieId)
            .map { it.toActorUiState() }
            .filter { actor ->
                actor.poster.isNotBlank() &&
                        actor.poster != "https://image.tmdb.org/t/p/w500"
            }
    }

    private suspend fun fetchTvShowCast(tvShowId: Long): List<ActorUiState> {
        return getTVShowCastUseCase(tvShowId)
            .map { it.toActorUiState() }
            .filter { actor ->
                actor.poster.isNotBlank() &&
                        actor.poster != "https://image.tmdb.org/t/p/w500"
            }
    }

    private fun castGame() {
        updateScreenStateToLoading()

        tryToCall(
            call = {
                val movies = fetchMovies()
                val shows = fetchTvShows()

                val movieId = movies.map { it.id }
                val tvShow = shows.map { it.id }

                if (movieId.isEmpty() && tvShow.isEmpty()) {
                    return@tryToCall emptyList<ActorUiState>()
                }

                val accumulatedCasts = mutableListOf<ActorUiState>()
                var movieIndex = 0
                var tvShowIndex = 0

                while (accumulatedCasts.size < numberOfQuestion &&
                    (movieIndex < movieId.size || tvShowIndex < tvShow.size)
                ) {
                    if (movieIndex < movieId.size) {
                        accumulatedCasts.addAll(fetchMovieCast(movieId[movieIndex]))
                        movieIndex++
                    }
                    if (tvShowIndex < tvShow.size && accumulatedCasts.size < numberOfQuestion) {
                        accumulatedCasts.addAll(fetchTvShowCast(tvShow[tvShowIndex]))
                        tvShowIndex++
                    }
                }

                accumulatedCasts.shuffled()
            },
            onSuccess = { castList ->
                updateState {
                    it.copy(
                        cast = castList,
                        loading = false
                    )
                }
            },
            onError = ::updateScreenStateToError
        )
    }

    private fun updateScreenStateToLoading() =
        updateState { screenState -> screenState.copy(loading = true) }

    private fun updateScreenStateToError(errorState: ErrorUiState) {
        updateState { screenState ->
            screenState.copy(
                error = errorState,
                loading = false
            )
        }
    }

    override fun nextQuestionClicked() {
        updateState {
            it.copy(
                currentQuestionIndex =
                    if (it.currentQuestionIndex < it.questions.size - 1) it.currentQuestionIndex + 1 else it.currentQuestionIndex,
                selectedAnswer = "",
                imageBlur = 8f,
                time = timer,
                totalRemainingTime = it.totalRemainingTime + (it.time - it.remainingTime)
            )
        }
    }

    override fun answerClicked(answer: String) {
        viewModelScope.launch {
            updateState { state ->
                val correctAnswer = state.questions[state.currentQuestionIndex].correctAnswer
                val isCorrect = answer == correctAnswer

                state.copy(
                    selectedAnswer = answer,
                    isAnswerCorrect = isCorrect,
                    totalPoint = if (isCorrect) state.totalPoint + numberOfPoints else state.totalPoint,
                    imageBlur = if (isCorrect) 0f else state.imageBlur,
                )
            }
        }
    }

    override fun hintClicked() {
        updateState { state ->
            if (state.totalPoint >= 10) {
                when (state.type) {
                    QuestionType.Image -> state.copy(
                        totalPoint = (state.totalPoint - 10).coerceAtLeast(0),
                        imageBlur = (state.imageBlur - 4f).coerceAtLeast(0f),
                    )

                    QuestionType.Text -> {
                        val currentQuestion = state.questions[state.currentQuestionIndex]
                        val incorrectOptions = currentQuestion.options
                            .filter { it != currentQuestion.correctAnswer }

                        val optionToRemove = incorrectOptions.randomOrNull()

                        val updatedOptions = if (optionToRemove != null) {
                            currentQuestion.options.filter { it != optionToRemove }
                        } else {
                            currentQuestion.options
                        }
                        if (updatedOptions.size >= 2) {
                            val updatedQuestion = currentQuestion.copy(options = updatedOptions)
                            val updatedQuestions = state.questions.toMutableList().apply {
                                this[state.currentQuestionIndex] = updatedQuestion
                            }
                            state.copy(
                                totalPoint = (state.totalPoint - 10).coerceAtLeast(0),
                                questions = updatedQuestions
                            )
                        } else {
                            state
                        }
                    }
                }
            } else {
                state.copy(showDialog = true)
            }
        }
    }

    override fun onDismissLevelDialog() =
        updateState { it.copy(showDialog = false) }


    override fun closeGameClicked() {
        sendNewEffect(QuizGameEffect.CloseGameClicked)
    }

    override fun updateRemainingTime(remainingTime: Int) {
        updateState {
            it.copy(
                remainingTime = remainingTime
            )
        }
    }


    override fun navigateToResult() {
        sendNewEffect(QuizGameEffect.NavigateToResult)

        viewModelScope.launch {
            observeUserProfileUseCase()
                .collect { user ->
                    if (user != null) {
                        val currentPoints = getPointsUseCase(user.id)
                        val updatedPoints = currentPoints + state.value.totalPoint
                        savePoint(
                            user.id,
                            points = updatedPoints
                        )
                    }
                }
        }
    }


}