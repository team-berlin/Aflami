package com.berlin.aflami.viewmodel.home.toprating

import app.cash.turbine.test
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.entity.CompanyProduction
import com.berlin.entity.Genre
import com.berlin.entity.Movie
import com.berlin.entity.Review
import com.berlin.entity.TVShow
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import usecase.movie.GetTopRatedMoviesUseCase
import usecase.tvshow.GetTopRatedTVShowUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Flow


class TestExtensions @OptIn(ExperimentalCoroutinesApi::class) constructor(
        private val testDispatcher: TestDispatcher= UnconfinedTestDispatcher()
) :BeforeEachCallback,AfterEachCallback{

    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun afterEach(context: ExtensionContext?) {
        Dispatchers.resetMain()
    }


}

@ExtendWith(TestExtensions::class)
class TopRatingViewModelTest {
    private lateinit var getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
    private lateinit var getTopRatedTvShowsUseCase: GetTopRatedTVShowUseCase

    private val viewModel: TopRatingViewModel by lazy {
        TopRatingViewModel(getTopRatedMoviesUseCase, getTopRatedTvShowsUseCase)
    }

    @Before
    fun setUp() {
        getTopRatedMoviesUseCase = mockk(relaxed = true)
        getTopRatedTvShowsUseCase = mockk(relaxed = true)

        coEvery { getTopRatedMoviesUseCase(any()) }returns listOf(movie)
        coEvery { getTopRatedTvShowsUseCase(any()) }returns listOf(tvShow)
        viewModel
    }

    @Test
    fun `getTopRatingMedia should update state with top rated media`() = runTest{
        viewModel

        viewModel.state.test {
            val state = awaitItem()
            assertThat(state.isLoading).isFalse()
            assertThat(state.topRatedMediaFlow).isNotNull()

        }

    }
    @Test
    fun `getTopRatingMedia should update state with with error when `() = runTest{

        val errorMessage="Network error"
        val errorUiState = ErrorUiState(errorMessage)

        viewModel.state.test {
            val state = awaitItem()
            assertThat(state.errorMessage).isEqualTo(errorUiState)
        }

    }

    @Test
    fun `onBackClicked should send NavigateBack effect`() = runTest{
        viewModel
        viewModel.effect.test {
            viewModel.onBackClicked()
            assertThat(awaitItem()).isEqualTo(TopRatingScreenEffect.NavigateBack)
        }
    }

    @Test
    fun `onMediaCardClicked should send NavigateToMediaDetailsScreen effect`() = runTest{
        val mediaId = 1L
        val mediaType = MediaType.MOVIE

        viewModel.effect.test {
            viewModel.onMediaCardClicked(mediaId, mediaType)
            assertThat(awaitItem()).isEqualTo(TopRatingScreenEffect.NavigateToMediaDetailsScreen(mediaId, mediaType))
        }

    }


    companion object{
        val movie = Movie(
            id = 1L,
            title = "Example Movie",
            rating = 8.5,
            releaseDate = "2024-01-01",
            posterURL = "https://example.com/poster.jpg",
            screenShot = "https://example.com/screenshot.jpg",
            description = "This is an example movie description.",
            genres = listOf(Genre(
                id = 1,
                name = "Action"
            )),
            duration = 120,
            hasVideo = true,
            companyProductions = listOf(CompanyProduction(
                id = 1,
                name = "Production Company",
                posterURL = "https://example.com/logo.jpg",
                originCountry = "USA"
            )),
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