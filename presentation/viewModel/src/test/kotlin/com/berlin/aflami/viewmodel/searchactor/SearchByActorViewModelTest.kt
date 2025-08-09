package com.berlin.aflami.viewmodel.search_by_actor

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.searchactor.SearchByActorScreenEffect
import com.berlin.aflami.viewmodel.searchactor.SearchByActorViewModel
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import usecase.movie.SearchByActorNameUseCase

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class SearchByActorViewModelTest {

    private val searchByActorNameUseCase: SearchByActorNameUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: SearchByActorViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        mockkStatic(Dispatchers::class)
        every { Dispatchers.IO } returns testDispatcher

        viewModel = SearchByActorViewModel(searchByActorNameUseCase)

        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `onBackClicked should send NavigatedBack effect`() = runTest {
        // Arrange
        val job = launch(testDispatcher) {
            viewModel.effect.collect { EFFECTS.add(it) }
        }

        // Act
        viewModel.onBackClicked()
        advanceUntilIdle()

        // Assert
        assertThat(EFFECTS).contains(SearchByActorScreenEffect.NavigatedBack)
        job.cancel()
    }

    @Test
    fun `onMediaCardClicked should send NavigatedToMediaDetailsScreen effect`() = runTest {
        // Arrange
        val job = launch(testDispatcher) {
            viewModel.effect.collect { effect ->
                effect.let { EFFECTS.add(it) }
            }
        }

        // Act
        viewModel.onMediaCardClicked(MOVIE_ID, MEDIA_TYPE)
        advanceUntilIdle()

        // Assert
        assertThat(EFFECTS).contains(
            SearchByActorScreenEffect.NavigatedToMediaDetailsScreen(MOVIE_ID, MEDIA_TYPE)
        )
        job.cancel()
    }

    @Test
    fun `onActorNameChanged should update actorName in state`() = runTest {
        // Arrange
        val actorName = TextFieldValue("Tom Hanks")

        // Act
        viewModel.onActorNameChanged(actorName)
        advanceUntilIdle()

        // Assert
        val currentState = viewModel.state.first()
        assertThat(currentState.actorName).isEqualTo(actorName)
    }

    @Test
    fun `updateScreenStateWithError updates state with error message and isLoading false`() =
        runTest {
            // Arrange
            val errorUiState = ErrorUiState(message = ERROR_MESSAGE)

            // Act
            viewModel.updateScreenStateWithError(errorUiState)

            // Assert
            val currentState = viewModel.state.first()
            assertThat(currentState.errorMessage).isEqualTo(ERROR_MESSAGE)
            assertThat(currentState.isLoading).isFalse()
        }

    @Test
    fun `getActorMediaContributionsAsFlow emits PagingData for given actorName`() = runTest {
        // Act
        val flow = viewModel.getActorMediaContributionsAsFlow(ACTOR_NAME)
        val pagingData = flow.first()

        // Assert
        assertThat(pagingData).isNotNull()
    }

    companion object {
        const val MOVIE_ID = 123L
        val MEDIA_TYPE = MediaType.MOVIE
        val EFFECTS = mutableListOf<SearchByActorScreenEffect>()
        const val ERROR_MESSAGE = "Error to fetch actor"
        const val ACTOR_NAME = "Tom Hanks"
    }
}
