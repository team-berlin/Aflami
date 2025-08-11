package com.berlin.aflami.viewmodel.details.cast

import app.cash.turbine.test
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.home.continueWatching.ContinueWatchingMediaViewModel
import com.berlin.aflami.viewmodel.mapper.toActorUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.entity.Actor
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import usecase.movie.GetMovieCastUseCase
import usecase.tvshow.GetTVShowCastUseCase

class TestExtensions @OptIn(ExperimentalCoroutinesApi::class) constructor(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : BeforeEachCallback, AfterEachCallback {

    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun afterEach(context: ExtensionContext?) {
        Dispatchers.resetMain()
    }


}

@ExtendWith(TestExtensions::class)
class CastViewModelScreenTest {


    private val getMovieCastUseCase: GetMovieCastUseCase= mockk(relaxed = true)
    private val getSeriesCastUseCase: GetTVShowCastUseCase= mockk(relaxed = true)
    private val castDetailsArgs: CastDetailsArgs= mockk{
        every { mediaId } returns 1L
        every { mediaType } returns MediaType.MOVIE
    }
    private val viewModel: CastViewModelScreen by lazy {
        CastViewModelScreen (getMovieCastUseCase, getSeriesCastUseCase,castDetailsArgs)
    }


    @Before
    fun setUp() {
        viewModel
        coEvery { getMovieCastUseCase(any()) } returns listOf(actor)
        coEvery { getSeriesCastUseCase(any()) } returns listOf(actor)
    }

    @Test
    fun `init loads all sections successfully`() = runTest {
        viewModel.state.test {
            val state = awaitItem()
            assertThat(state.isScreenLoading).isFalse()
            assertThat(state.errorMessage).isNull()
            assertThat(state.castList).isNotNull()
        }

    }


    @Test
    fun `GetMediaCast should update state with cast of media`() = runTest {

        viewModel.state.test {
            val state=awaitItem()
            assertThat(state.isScreenLoading).isFalse()
            assertThat(state.errorMessage).isNull()
            assertThat(state.castList).isNotNull()
        }

    }

    @Test
    fun `GetMediaCast should update state with with error when `() = runTest {
        val errorMessage = "Network error"
        val errorUiState = ErrorUiState(errorMessage)
        viewModel.updateScreenStateToError(errorUiState)

        viewModel.state.test {
           val state=awaitItem()
            assertThat(state.isScreenLoading).isFalse()
            assertThat(state.errorMessage).isEqualTo(errorUiState.message)
        }
    }

    @Test
    fun `onBackClicked should send NavigateBack effect`() = runTest {
        viewModel.effect.test {
            viewModel.onBackClicked()
            assertThat(awaitItem()).isEqualTo(CastDetailsScreenEffect.NavigationBack)
        }
    }

    companion object {
       val actor=Actor(
           id = 1L,
           name = "Ahmad helmy",
           posterURL = "/poster.jpg"
       )
    }

}