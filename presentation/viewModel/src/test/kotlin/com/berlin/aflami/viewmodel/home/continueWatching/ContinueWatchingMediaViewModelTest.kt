package com.berlin.aflami.viewmodel.home.continueWatching

import app.cash.turbine.test
import com.berlin.aflami.viewmodel.home.toprating.TopRatingScreenEffect
import com.berlin.aflami.viewmodel.home.toprating.TopRatingViewModel
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.entity.CompanyProduction
import com.berlin.entity.Genre
import com.berlin.entity.Movie
import com.berlin.entity.Review
import com.berlin.entity.TVShow
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import usecase.movie.ContinueWatchingMovieUseCase
import usecase.movie.GetTopRatedMoviesUseCase
import usecase.tvshow.ContinueWatchingTVShowUseCase
import usecase.tvshow.GetTopRatedTVShowUseCase


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
class ContinueWatchingMediaViewModelTest {

    private lateinit var getContinueWatchingMoviesUseCase: ContinueWatchingMovieUseCase
    private lateinit var getContinueWatchingTVShowsUseCase: ContinueWatchingTVShowUseCase

    private val viewModel: ContinueWatchingMediaViewModel by lazy {
       ContinueWatchingMediaViewModel (getContinueWatchingMoviesUseCase, getContinueWatchingTVShowsUseCase)
    }

    @Before
    fun setUp() {
        getContinueWatchingMoviesUseCase = mockk(relaxed = true)
        getContinueWatchingTVShowsUseCase = mockk(relaxed = true)
    }

    @Test
    fun `getTopRatingMedia should update state with top rated media`() = runTest {


        coEvery { getContinueWatchingMoviesUseCase.invoke(1) } returns listOf(movie)
        coEvery { getContinueWatchingTVShowsUseCase.invoke(1) } returns listOf(tvShow)

        val vm = ContinueWatchingMediaViewModel( getContinueWatchingMoviesUseCase,getContinueWatchingTVShowsUseCase)

        vm.state.test {
            val state = awaitItem()
            Truth.assertThat(state.isLoading).isFalse()
            Truth.assertThat(state.continueWatchingMediaFlow).isNotNull()

        }

    }

    @Test
    fun `onBackClicked should send NavigateBack effect`() = runTest {
        viewModel
        viewModel.effect.test {
            viewModel.onBackClicked()
            Truth.assertThat(awaitItem()).isEqualTo(ContinueWatchingScreenEffect.NavigateBack)
        }
    }

    @Test
    fun `onMediaCardClicked should send NavigateToMediaDetailsScreen effect`() = runTest {
        val mediaId = 1L
        val mediaType = MediaType.MOVIE

        viewModel.effect.test {
            viewModel.onMediaCardClicked(mediaId, mediaType)
            Truth.assertThat(awaitItem()).isEqualTo(
                ContinueWatchingScreenEffect.NavigateToDetailsScreen(
                    mediaId,
                    mediaType
                )
            )
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