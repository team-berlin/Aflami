package com.berlin.aflami.viewmodel.profile.watchhistory

import app.cash.turbine.test
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
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import usecase.movie.ContinueWatchingMovieUseCase
import usecase.tvshow.ContinueWatchingTVShowUseCase

@OptIn(ExperimentalCoroutinesApi::class)
class WatchHistoryViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getContinueWatchingMovieUseCase: ContinueWatchingMovieUseCase
    private lateinit var getContinueWatchingTVShowUseCase: ContinueWatchingTVShowUseCase
    private lateinit var viewModel: WatchHistoryViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getContinueWatchingMovieUseCase = mockk(relaxed = true)
        getContinueWatchingTVShowUseCase = mockk(relaxed = true)

        coEvery { getContinueWatchingMovieUseCase(any()) } returns listOf(movie)
        coEvery { getContinueWatchingTVShowUseCase(any()) } returns listOf(tvShow)

        viewModel = WatchHistoryViewModel(
            getContinueWatchingMovieUseCase,
            getContinueWatchingTVShowUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load movies and tv shows`() = runTest {
        advanceUntilIdle()
        val state = viewModel.state.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.movies).isNotNull()
        assertThat(state.tvShows).isNotNull()
        assertThat(state.errorMessage).isNull()
    }

    @Test
    fun `onTabOptionClicked should change selected tab`() = runTest {
        viewModel.onTabOptionClicked(TabOption.TV_SHOWS)
        val state = viewModel.state.value
        assertThat(state.selectedTabOption).isEqualTo(TabOption.TV_SHOWS)
        assertThat(state.isLoading).isFalse()
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
        coEvery { getContinueWatchingMovieUseCase(any()) } throws Exception(errorMessage)

        viewModel = WatchHistoryViewModel(
            getContinueWatchingMovieUseCase,
            getContinueWatchingTVShowUseCase
        )

        advanceUntilIdle()
        val state = viewModel.state.value
        assertThat(state.errorMessage).isEqualTo(errorMessage)
        assertThat(state.isLoading).isFalse()
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
