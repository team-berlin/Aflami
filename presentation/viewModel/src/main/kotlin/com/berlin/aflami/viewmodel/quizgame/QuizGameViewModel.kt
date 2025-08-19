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
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.aflami.viewmodel.shareduistate.toGenreUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
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
    // Movie use cases
    private val getMovieGameUseCase: GetMovieGameUseCase,
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase,
    // TV Show use cases
    private val getTVShowGameUseCase: GetTVShowGameUseCase,
    private val getTVGenresUseCase: GetTVShowGenresUseCase,
    private val getTVShowCastUseCase: GetTVShowCastUseCase,
    // Game use cases
    private val savePoint: AddPointsUseCase,
    private val getPointsUseCase: GetPointsUseCase,
    private val observeUserProfileUseCase: ObserveUserProfileUseCase,
    guessGameScreenArgs: GuessGameScreenArgs
) : BaseViewModel<QuizGameUiState, QuizGameEffect>(QuizGameUiState()),
    QuizGameInteractionListener {

    private val timer = guessGameScreenArgs.timer ?: 0
    private val gameType = guessGameScreenArgs.gameType ?: GameType.POSTER
    private val numberOfQuestion = guessGameScreenArgs.numberOfQuestion ?: 0
    private val numberOfPoints = guessGameScreenArgs.numberOfPoint ?: 0

    private var timerJob: Job? = null

    init {
        updateState { it.copy(numberOfPoint = numberOfPoints) }
        viewModelScope.launch { initializeGame() }
    }

    private fun initializeGame() {
        updateState { it.copy(loading = true) }
        when (gameType) {
            GameType.CHARACTER -> loadCastGame()
            GameType.POSTER -> loadPosterGame()
            GameType.RELEASE -> loadRelease()
            GameType.GENRE -> loadGenreGame()
        }
    }

    private suspend fun fetchAllMedia() = coroutineScope {
        val moviesDeferred = async { getMovieGameUseCase().map { it.toMediaUiState() } }
        val tvShowsDeferred = async { getTVShowGameUseCase().map { it.toMediaUiState() } }
        (moviesDeferred.await() + tvShowsDeferred.await()).shuffled().take(numberOfQuestion)
    }

    private suspend fun fetchAllGenres() = coroutineScope {
        val movieGenresDeferred = async { getMovieGenresUseCase().map { it.toGenreUiState() } }
        val tvGenresDeferred = async { getTVGenresUseCase().map { it.toGenreUiState() } }
        movieGenresDeferred.await() + tvGenresDeferred.await()
    }

    private suspend fun fetchCast(mediaId: Long, isMovie: Boolean): List<ActorUiState> {
        val cast = if (isMovie) getMovieCastUseCase(mediaId) else getTVShowCastUseCase(mediaId)
        return cast.map { it.toActorUiState() }
            .filter { it.poster.isNotBlank() && it.poster != "https://image.tmdb.org/t/p/w500" }
    }


    private fun loadRelease() = executeWithErrorHandling {
        val mediaList = fetchAllMedia()
        updateState { it.copy(mediaList = mediaList) }
        val questions = createReleaseQuestions(mediaList)
        updateGameState(questions, QuestionType.Text)
    }

    private fun loadPosterGame() = executeWithErrorHandling {
        val mediaList = fetchAllMedia()
        updateState { it.copy(mediaList = mediaList) }
        val questions = createPosterQuestions(mediaList)
        updateGameState(questions, QuestionType.Image)
    }

    private fun loadGenreGame() = executeWithErrorHandling {
        val mediaList = fetchAllMedia()
        val genreList = fetchAllGenres()

        updateState { it.copy(mediaList = mediaList, genreList = genreList) }

        val questions = createGenreQuestions(genreList, mediaList)
        updateGameState(questions, QuestionType.Text)
    }

    private fun loadCastGame() = executeWithErrorHandling {
        val mediaList = fetchAllMedia()
        val castList = mutableListOf<ActorUiState>()

        var index = 0
        while (castList.size < numberOfQuestion && index < mediaList.size) {
            val media = mediaList[index]
            val cast = fetchCast(media.id, media.mediaType == MediaType.MOVIE)
            castList.addAll(cast)
            index++
        }
        val castListCustom = castList.take(numberOfQuestion)
        updateState { it.copy(cast = castListCustom.shuffled()) }
        val questions = createCharacterQuestions(castListCustom)
        updateGameState(questions, QuestionType.Image)
    }

    private fun createPosterQuestions(mediaList: List<MediaUiState>) = mediaList.map { media ->
        val options = createMediaOptions(mediaList, media.id) { it.title } + media.title
        Question(media.poster, options.shuffled(), media.title)
    }

    private fun createReleaseQuestions(mediaList: List<MediaUiState>) = mediaList.map { media ->
        val correctYear = media.releaseYear.take(4)
        val wrongYears = mediaList
            .map { it.releaseYear.take(4) }
            .filter { it != correctYear }
            .distinct()
            .take(3)

        val options = (wrongYears + correctYear).shuffled()
        options.also {   Log.e("nour",it.toString())}
        Log.e("nour2",correctYear)

        Question(media.title, options, correctYear)
    }


    private fun createGenreQuestions(genreList: List<GenreUiState>, mediaList: List<MediaUiState>) =
        mediaList.map { media ->
            val mediaGenres = media.genre.mapNotNull { id -> genreList.find { it.id == id }?.name }
            val correctAnswer = mediaGenres.random()
            val wrongOptions = genreList.asSequence()
                .filterNot { it.name in mediaGenres }
                .map { it.name }
                .shuffled()
                .take(3)
                .toList()
            Question(media.title, (wrongOptions + correctAnswer).shuffled(), correctAnswer)
        }

    private fun createCharacterQuestions(castList: List<ActorUiState>) = castList.map { actor ->
        val options = castList.asSequence()
            .filter { it.mediaId == actor.mediaId && it.name != actor.name }
            .map { it.name }
            .take(3)
            .toList()
        Question(actor.poster, (options + actor.name).shuffled(), actor.name)
    }

    private fun <T> createMediaOptions(
        mediaList: List<MediaUiState>,
        excludeId: Long,
        selector: (MediaUiState) -> T
    ) =
        mediaList.asSequence()
            .filterNot { it.id == excludeId }
            .map(selector)
            .distinct()
            .shuffled()
            .take(3)
            .toList()

    private fun updateGameState(questions: List<Question>, type: QuestionType) {
        updateState {
            it.copy(
                questions = questions,
                loading = false,
                type = type,
                gameTypeName = gameType,
                time = timer,
                remainingTime = timer
            )
        }
        startTimer()
    }

    private fun startTimer() {
        stopTimer()
        timerJob = viewModelScope.launch {
            var currentTime = timer
            updateState { it.copy(remainingTime = currentTime) }

            while (currentTime > 0) {
                delay(1000)
                currentTime--
                updateState { it.copy(remainingTime = currentTime) }
            }

            handleTimeFinished()
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    private fun handleTimeFinished() {
        val currentState = state.value
        if (currentState.currentQuestionIndex < currentState.questions.size -1 ) {
            nextQuestionClicked()
        } else {
            navigateToResult()
        }
    }

    private fun executeWithErrorHandling(action: suspend () -> Unit) {
        tryToCall(
            call = { action() },
            onSuccess = { },
            onError = ::updateScreenStateToError
        )
    }


    private fun updateScreenStateToError(errorState: ErrorUiState) {
        updateState { it.copy(error = errorState, loading = false) }
    }

    override fun nextQuestionClicked() {
        stopTimer()
        updateState { state ->
            state.copy(
                currentQuestionIndex = minOf(
                    state.currentQuestionIndex + 1,
                    state.questions.size -1
                ),
                selectedAnswer = "",
                showScore = false,
                imageBlur = 8f,
                totalRemainingTime = state.totalRemainingTime + (state.time - state.remainingTime),
                isAnswerCorrect = null
            )
        }
        if (state.value.currentQuestionIndex < state.value.questions.size ) {
            startTimer()
        }
    }

    override fun answerClicked(answer: String) {
        stopTimer()
        updateState { state ->
            val isCorrect = answer == state.questions[state.currentQuestionIndex].correctAnswer
            state.copy(
                selectedAnswer = answer,
                isAnswerCorrect = isCorrect,
                totalPoint = if (isCorrect) state.totalPoint + numberOfPoints else state.totalPoint,
                imageBlur = if (isCorrect) 0f else state.imageBlur,
                showScore = true
            )

        }
        viewModelScope.launch {
            delay(2000)
            updateState { state ->
                state.copy(showScore = false)
            }
        }
    }

    override fun hintClicked() {
        updateState { state ->
            if (state.totalPoint < 10) return@updateState state.copy(showDialog = true)

            when (state.type) {
                QuestionType.Image -> state.copy(
                    totalPoint = (state.totalPoint - 10).coerceAtLeast(0),
                    imageBlur = (state.imageBlur - 4f).coerceAtLeast(0f)
                )

                QuestionType.Text -> handleTextHint(state)
            }
        }
    }

    private fun handleTextHint(state: QuizGameUiState): QuizGameUiState {
        val currentQuestion = state.questions[state.currentQuestionIndex]
        val incorrectOptions =
            currentQuestion.options.filter { it != currentQuestion.correctAnswer }
        val optionToRemove = incorrectOptions.randomOrNull() ?: return state

        val updatedOptions = currentQuestion.options.filter { it != optionToRemove }
        if (updatedOptions.size < 2) return state

        val updatedQuestions = state.questions.toMutableList().apply {
            this[state.currentQuestionIndex] = currentQuestion.copy(options = updatedOptions)
        }

        return state.copy(
            totalPoint = (state.totalPoint - 10).coerceAtLeast(0),
            questions = updatedQuestions
        )
    }

    override fun onDismissLevelDialog() = updateState { it.copy(showDialog = false) }

    override fun closeGameClicked() {
        stopTimer()
        sendNewEffect(QuizGameEffect.CloseGameClicked)
    }

    override fun navigateToResult() {
        stopTimer()
        sendNewEffect(QuizGameEffect.NavigateToResult)
        viewModelScope.launch {
            observeUserProfileUseCase().collect { user ->
                user?.let {
                    val currentPoints = getPointsUseCase(it.id)
                    savePoint(it.id, currentPoints + state.value.totalPoint)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }

    override fun retry() {
        updateState {
            it.copy(
                error = null,
                loading = true
            )
        }

        viewModelScope.launch {
            initializeGame()
        }
    }

}