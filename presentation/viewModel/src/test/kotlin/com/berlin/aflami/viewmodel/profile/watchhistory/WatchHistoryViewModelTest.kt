package com.berlin.aflami.viewmodel.profile.watchhistory

import app.cash.turbine.test
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.home.toprating.TopRatingViewModel
import com.berlin.aflami.viewmodel.search.TabOption
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.entity.CompanyProduction
import com.berlin.entity.Genre
import com.berlin.entity.Movie
import com.berlin.entity.Review
import com.berlin.entity.TVShow
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import usecase.movie.ContinueWatchingMovieUseCase
import usecase.tvshow.ContinueWatchingTVShowUseCase

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
class WatchHistoryViewModelTest {

    private val getContinueWatchingMovieUseCase: ContinueWatchingMovieUseCase = mockk(relaxed = true)
    private val getContinueWatchingTVShowUseCase: ContinueWatchingTVShowUseCase = mockk(relaxed = true)

    private val viewModel: WatchHistoryViewModel by lazy {
        WatchHistoryViewModel (getContinueWatchingMovieUseCase, getContinueWatchingTVShowUseCase)
    }

    @Before
    fun setUp() {
        viewModel
        coEvery { getContinueWatchingMovieUseCase(any()) } returns listOf(movie)
        coEvery { getContinueWatchingTVShowUseCase(any()) } returns listOf(tvShow)
    }

    @Test
    fun `init should load movies and tv shows`() = runTest {

        viewModel.state.test {
            val state = awaitItem()
            assertThat(state.isLoading).isFalse()
            assertThat(state.movies).isNotNull()
            assertThat(state.tvShows).isNotNull()
            assertThat(state.errorMessage).isNull()
        }
    }

    @Test
    fun `onTabOptionClicked should change selected tab`() = runTest {
        val selectedOption=TabOption.TV_SHOWS
        viewModel.onTabOptionClicked(selectedOption)

        viewModel.state.test {
            val state=awaitItem()
            assertThat(state.selectedTabOption).isEqualTo(selectedOption)
            assertThat(state.isLoading).isFalse()
        }
    }

    @Test
    fun `onBackClicked should emit NavigateBack effect`() = runTest {
        viewModel.effect.test {
            viewModel.onBackClicked()
            assertThat(awaitItem()).isEqualTo(WatchHistoryScreenEffect.NavigateBack)
        }
    }

    @Test
    fun `onMediaCardClicked should emit NavigateToDetailsScreen effect`() = runTest {
        viewModel.effect.test {
            viewModel.onMediaCardClicked(10L, MediaType.MOVIE)
            assertThat(awaitItem()).isEqualTo(
                WatchHistoryScreenEffect.NavigateToDetailsScreen(10L, MediaType.MOVIE)
            )
        }
    }

    @Test
    fun `should update state with error when movie loading fails`() = runTest {
        val errorMessage = "Network error"
        val errorUiState = ErrorUiState(errorMessage)

        viewModel.updateScreenStateToError(errorUiState)

        viewModel.state.test {
            val state = awaitItem()
            assertThat(state.errorMessage).isEqualTo(errorUiState.message)
            assertThat(state.isLoading).isFalse()
        }
    }

    companion object {
        val movie = Movie(
            id = 1L,
            title = "Example Movie",
            rating = 8.5,
            releaseDate = "2024-01-01",
            posterURL = "https://example.com/poster.jpg",
            screenShot = "https://example.com/screenshot.jpg",
            description = "This is an example movie description.",
            genres = listOf(
                Genre(
                    id = 1,
                    name = "Action"
                )
            ),
            duration = 120,
            hasVideo = true,
            companyProductions = listOf(
                CompanyProduction(
                    id = 1,
                    name = "Production Company",
                    posterURL = "https://example.com/logo.jpg",
                    originCountry = "USA"
                )
            ),
            originCountry = "USA",
            galleryUrl = listOf(
                "https://example.com/gallery1.jpg",
                "https://example.com/gallery2.jpg"
            ),
            reviews = listOf(
                Review(
                    id = "1",
                    name = "John Doe",
                    userName = "John Doe2",
                    avatarImage = "/avatar.jpg",
                    rating = 2.5,
                    content = "good",
                    date = "20/7"
                ),
            ),
            isFavourite = false
        )
        val tvShow = TVShow(
            id = 1L,
            title = "Example Movie",
            rating = 8.5,
            releaseDate = "2024-01-01",
            posterURL = "https://example.com/poster.jpg",
            screenShot = "https://example.com/screenshot.jpg",
            description = "This is an example movie description.",
            genres = listOf(
                Genre(
                    id = 1,
                    name = "Action"
                )
            ),
            duration = 120,
            hasVideo = true,
            companyProductions = listOf(
                CompanyProduction(
                    id = 1,
                    name = "Production Company",
                    posterURL = "https://example.com/logo.jpg",
                    originCountry = "USA"
                )
            ),
            originCountry = "USA",
            galleryUrl = listOf(
                "https://example.com/gallery1.jpg",
                "https://example.com/gallery2.jpg"
            ),
            numberOfSeasons = 20
        )
    }
}
