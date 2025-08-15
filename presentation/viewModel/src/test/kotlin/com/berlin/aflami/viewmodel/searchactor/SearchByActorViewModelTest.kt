package com.berlin.aflami.viewmodel.search_by_actor

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.test
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.searchactor.SearchByActorScreenEffect
import com.berlin.aflami.viewmodel.searchactor.SearchByActorViewModel
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
        viewModel = SearchByActorViewModel(searchByActorNameUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should update state when actor name is changed`() = runTest {

        viewModel.onActorNameChanged(TextFieldValue(ACTOR_NAME))
        advanceUntilIdle()

        val state = viewModel.state.value
        assertThat(state.actorName.text).isEqualTo(ACTOR_NAME)
    }

    @Test
    fun `should emit NavigatedBack effect when onBackClicked called`() = runTest {
        viewModel.effect.test {
            viewModel.onBackClicked()
            assertThat(awaitItem()).isEqualTo(SearchByActorScreenEffect.NavigatedBack)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should emit NavigatedToMediaDetailsScreen effect when media card is clicked`() = runTest {
        val mediaType = MediaType.MOVIE
        viewModel.effect.test {
            viewModel.onMediaCardClicked(42L, mediaType)
            assertThat(awaitItem()).isEqualTo(
                SearchByActorScreenEffect.NavigatedToMediaDetailsScreen(42L, mediaType)
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should update state when screen state is updated with error`() =
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
        const val ERROR_MESSAGE = "Error to fetch actor"
        const val ACTOR_NAME = "Tom Hanks"
    }
}