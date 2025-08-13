package com.berlin.aflami.viewmodel.quizgame

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import usecase.auth.GetLoginStatusUseCase
import usecase.game.AddPointsUseCase
import usecase.movie.GetMovieCastUseCase
import usecase.movie.GetMovieGameUseCase
import usecase.movie.GetMovieGenresUseCase
import usecase.profile.GetUserProfileUseCase
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
    private val observeUserProfileUseCase:ObserveUserProfileUseCase,
    guessGameScreenArgs: GuessGameScreenArgs
) : BaseViewModel<QuizGameUiState, QuizGameEffect>(
    QuizGameUiState()
), QuizGameInteractionListener {
    private val mediaList = MutableStateFlow<List<MediaUiState>>(emptyList())
    private val mediaGenre = MutableStateFlow<List<GenreUiState>>(emptyList())
    private val movieIds = MutableStateFlow<List<Long>>(emptyList())
    private val tvShowIds = MutableStateFlow<List<Long>>(emptyList())
    private val mediaCast = MutableStateFlow<List<ActorUiState>>(emptyList())

    private val timer = guessGameScreenArgs.timer ?: 0
    private val gameType = guessGameScreenArgs.gameType ?: ""
    private val numberOfQuestion = guessGameScreenArgs.numberOfQuestion ?: 0
    private val numberOfPoints = guessGameScreenArgs.numberOfPoint ?: 0

    init {
        collectUserProfile()
        viewModelScope.launch {
            mediaGame()
            when (gameType.uppercase()) {
                GameType.CHARACTER.name -> {
                    getCast()
                    getMediaByCharacter()
                }
                GameType.POSTER.name -> getMediaByPoster()
                GameType.RELEASE.name -> getMediaByReleaseDate()
                GameType.GENRE.name -> {
                    genreGame()
                    getMediaByGenres()
                }

                else -> getMediaByCharacter()
            }
        }
    }


    private fun getMediaByCharacter() {
        val mediaItem = mediaCast.value.take(numberOfQuestion)
        Log.e("mediaItem", mediaItem.size.toString())

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
                gameTypeName = GameType.valueOf(gameType),
                time = timer
            )
        }
        Log.e("questions", questions.toString())
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
        updateState {
            it.copy(
                questions = questions,
                loading = false,
                type = QuestionType.Image,
                gameTypeName = GameType.valueOf(gameType),
                time = timer
            )
        }
    }

    //question-> media name
    // answer-> release date
    private fun getMediaByReleaseDate() {
        val mediaItems = mediaList.value
        if (mediaItems.isEmpty()) return

        val questions = mediaItems.map { media ->
            val correctYear = media.releaseYear

            val wrongOptions = mediaItems
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
                gameTypeName = GameType.valueOf(gameType),
                time = timer
            )
        }
    }


    //question-> media name
    // answer -> genre
    private fun getMediaByGenres() {
        val mediaItems = mediaList.value
        val genreItems = mediaGenre.value
        Log.e("o",mediaItems.size.toString())
        Log.e("t",genreItems.size.toString())

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
        updateState {
            it.copy(
                questions = questions,
                loading = false,
                type = QuestionType.Text,
                gameTypeName = GameType.valueOf(gameType),
                time = timer
            )
        }
    }


    private fun mediaGame() {
        updateScreenStateToLoading()
        tryToCall(
            call = {
                val movie = getMovieGameUseCase()
                val tvShow = getTVShowGameUseCase()
                val movieList = movie.map { it.toMediaUiState() }
                val tvShowList = tvShow.map { it.toMediaUiState() }
                movieIds.value = movieList.map { it.id }.shuffled()
                tvShowIds.value = tvShowList.map { it.id }.shuffled()
                mediaList.value = (movieList+tvShowList).shuffled().take(numberOfQuestion)
                Log.e("movieList",mediaList.toString())
            },
            onSuccess = {
                updateState {
                    it.copy(
                        loading = false
                    )
                }
            },
            onError = ::updateScreenStateToError,
        )

    }

    private fun genreGame() {
        updateScreenStateToLoading()
        tryToCall(
            call = {
                val movieGenre = (getMovieGenresUseCase())
                val tvShowGenre = getTVGenresUseCase()
                val movieGenreList = movieGenre.map { it.toGenreUiState() }
                val tvShowGenreList = tvShowGenre.map { it.toGenreUiState() }
                Log.e("movieGenreList",movieGenreList.size.toString())
                Log.e("tvShowGenreList",tvShowGenreList.size.toString())

                mediaGenre.value = (movieGenreList + tvShowGenreList)
            },
            onSuccess = {
                updateState {
                    it.copy(
                        loading = false
                    )
                }
            },
            onError =::updateScreenStateToError,
        )
    }
    private suspend fun getCast() {
        updateScreenStateToLoading()
        try {
            if (movieIds.value.isEmpty() && tvShowIds.value.isEmpty()) return

            val accumulatedCasts = mutableListOf<ActorUiState>()
            var movieIndex = 0
            var tvShowIndex = 0

            while (accumulatedCasts.size < 20 &&
                (movieIndex < movieIds.value.size || tvShowIndex < tvShowIds.value.size)
            ) {
                if (movieIndex < movieIds.value.size) {
                    val movieCast = getMovieCastUseCase(movieIds.value[movieIndex])
                        .map { it.toActorUiState() }
                        .filter { actor ->
                            !actor.poster.isNullOrBlank() &&
                                    actor.poster != "https://image.tmdb.org/t/p/w500"
                        }
                    accumulatedCasts.addAll(movieCast)
                    movieIndex++
                }
                if (tvShowIndex < tvShowIds.value.size && accumulatedCasts.size < 20) {
                    val tvShowCast = getTVShowCastUseCase(tvShowIds.value[tvShowIndex])
                        .map { it.toActorUiState() }
                        .filter { actor ->
                            !actor.poster.isNullOrBlank() &&
                                    actor.poster != "https://image.tmdb.org/t/p/w500"
                        }
                    accumulatedCasts.addAll(tvShowCast)
                    tvShowIndex++
                }
            }
            mediaCast.value = accumulatedCasts.shuffled()
        } finally {
            updateState { it.copy(loading = false) }
        }
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
                    if (it.currentQuestionIndex < it.questions.size) it.currentQuestionIndex + 1 else it.currentQuestionIndex,
                selectedAnswer = ""
            )
        }
    }

    override fun answerClicked(answer: String) {
        viewModelScope.launch {
        updateState {
            val checkAnswer =
                it.selectedAnswer == it.questions[it.currentQuestionIndex].correctAnswer
            it.copy(
                selectedAnswer = answer,
                isAnswerCorrect = it.selectedAnswer == it.questions[it.currentQuestionIndex].correctAnswer,
                totalPoint = if (checkAnswer) it.totalPoint + numberOfPoints else it.totalPoint - numberOfPoints
            )
        }
        }
    }
    override fun hintClicked() {
        updateState { state ->
            if (state.totalPoint >= 10) {
                when (state.type) {
                    QuestionType.Image -> state.copy(
                        enableHint = true,
                        totalPoint = state.totalPoint - 10,
                        imageBlur = state.imageBlur - 3
                    )
                    QuestionType.Text -> {
                        val currentQuestion = state.questions[state.currentQuestionIndex]
                        val incorrectOptions = currentQuestion.options.filter { it != currentQuestion.correctAnswer }
                        val optionToRemove = incorrectOptions.randomOrNull()
                        val updatedOptions = if (optionToRemove != null) {
                            currentQuestion.options.filter { it != optionToRemove }
                        } else {
                            currentQuestion.options
                        }
                        val updatedQuestion = currentQuestion.copy(options = updatedOptions)
                        val updatedQuestions = state.questions.toMutableList().apply {
                            this[state.currentQuestionIndex] = updatedQuestion
                        }
                        state.copy(
                            enableHint = true,
                            totalPoint = state.totalPoint - 10,
                            questions = updatedQuestions
                        )
                    }
                }
            } else {
                state.copy(
                    enableHint = false
                )
            }
        }
    }

    override fun closeGameClicked() {
        sendNewEffect(QuizGameEffect.CloseGameClicked)

    }

    override fun navigateToResult() {
        sendNewEffect(QuizGameEffect.NavigateToResult)
    }

    private fun collectUserProfile() {
        viewModelScope.launch {
            observeUserProfileUseCase()
                .collect { user ->
                    if (user != null) {
                        savePoint(
                            user.id,
                            points =state.value.totalPoint ,
                        )
                    }
                }
        }
    }


}