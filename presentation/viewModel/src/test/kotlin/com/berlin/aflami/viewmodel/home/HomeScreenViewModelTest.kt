package com.berlin.aflami.viewmodel.home

import app.cash.turbine.test
import com.berlin.aflami.viewmodel.mapper.UserMood
import com.berlin.entity.Genre
import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import com.berlin.exception.NotFoundException
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.unmockkStatic
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import usecase.movie.ContinueWatchingMovieUseCase
import usecase.movie.GetMovieGenresUseCase
import usecase.movie.GetMoviesByMoodUseCase
import usecase.movie.GetPopularMoviesUseCase
import usecase.movie.GetTopRatedMoviesUseCase
import usecase.movie.GetUpComingMoviesUseCase
import usecase.tvshow.ContinueWatchingTVShowUseCase
import usecase.tvshow.GetPopularTVShowsUseCase
import usecase.tvshow.GetTVShowGenresUseCase
import usecase.tvshow.GetTopRatedTVShowUseCase
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase = mockk()
    private val getPopularTVShowsUseCase: GetPopularTVShowsUseCase = mockk()
    private val getUpComingMoviesUseCase: GetUpComingMoviesUseCase = mockk()
    private val getMovieGenresUseCase: GetMovieGenresUseCase = mockk()
    private val getTVShowGenresUseCase: GetTVShowGenresUseCase = mockk()
    private val getWatchedMovieUseCase: ContinueWatchingMovieUseCase = mockk()
    private val getWatchedTVShowUseCase: ContinueWatchingTVShowUseCase = mockk()
    private val getTopRatedSeriesUseCase: GetTopRatedTVShowUseCase = mockk()
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase = mockk()
    private val getMoviesByMoodUseCase: GetMoviesByMoodUseCase = mockk()
    private lateinit var viewModel: HomeScreenViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        
        coEvery { getPopularMoviesUseCase() } answers {

            List(20) { index ->
                fakeMovieEntity(
                    title = "Movie $index"
                )
            }
        }
        coEvery { getPopularTVShowsUseCase() } answers {

            List(20) { index ->
                fakeTVShowEntity(
                    title = "TV $index"
                )
            }
        }

        coEvery { getUpComingMoviesUseCase() } returns listOf(
            fakeMovieEntity("Upcoming1"), fakeMovieEntity("Upcoming2")
        )
        coEvery { getMovieGenresUseCase() } returns listOf(Genre(1, "Action"))
        coEvery { getTVShowGenresUseCase() } returns listOf(Genre(10, "Drama"))
        coEvery { getWatchedMovieUseCase(any()) } returns listOf(fakeMovieEntity("WatchedMovie"))
        coEvery { getWatchedTVShowUseCase(any()) } returns listOf(fakeTVShowEntity("WatchedShow"))
        coEvery { getTopRatedSeriesUseCase(any()) } returns listOf(
            fakeTVShowEntity(
                "TopSeries", rating = 9.0
            )
        )
        coEvery { getTopRatedMoviesUseCase(any()) } returns listOf(
            fakeMovieEntity(
                "TopMovie", rating = 8.5
            )
        )
        coEvery { getMoviesByMoodUseCase(any()) } returns listOf(fakeMovieEntity("MoodMovie"))

        viewModel = HomeScreenViewModel(
            getPopularMoviesUseCase,
            getPopularTVShowsUseCase,
            getUpComingMoviesUseCase,
            getMovieGenresUseCase,
            getTVShowGenresUseCase,
            getWatchedMovieUseCase,
            getWatchedTVShowUseCase,
            getTopRatedSeriesUseCase,
            getTopRatedMoviesUseCase,
            getMoviesByMoodUseCase,
            dispatcher
        )
    }

    @After
    fun tearDown() {
        unmockkStatic(Dispatchers::class)
        Dispatchers.resetMain()
    }

    @Test
    fun `init loads all sections successfully`() = runTest {
        viewModel
        advanceUntilIdle()
        delay(1000)
        val state = viewModel.state.value
        assertEquals(40, state.popularMediaUiState.popularMedia.size)
        assertTrue(state.continueWatchingUiState.continueWatchingMediaList.isNotEmpty())
        assertTrue(state.topRatedMediaUiState.topRatedMedia.isNotEmpty())
        assertTrue(state.upcomingMoviesUiState.upcomingMovies.isNotEmpty())
        assertEquals(2, state.movieGenres.size)
        assertEquals(2, state.tVShowGenres.size)
    }

    @Test
    fun `onDismissMoodPickerDialog updates moodPickerUiState correctly`() = runTest {

        viewModel.onDismissMoodPickerDialog()
        advanceUntilIdle()

        val moodState = viewModel.state.value.moodPickerUiState
        assertFalse(moodState.openMovieDialog)
        assertTrue(moodState.isLoading)
    }
    
    @Test
    fun `onSearchClicked sends NavigateToSearchScreen effect`() = runTest {
        viewModel.effect.test {
            viewModel.onSearchClicked()
            assertEquals(HomeScreenEffect.NavigateToSearchScreen, awaitItem())
        }
    }

    @Test
    fun `onShowAllContinueWatchingClicked sends NavigateToContinueWatchingScreen effect`() =
        runTest {
            viewModel.effect.test {
                viewModel.onShowAllContinueWatchingClicked()
                assertEquals(HomeScreenEffect.NavigateToContinueWatchingScreen, awaitItem())
            }
        }

    @Test
    fun `onShowAllTopRatingClicked sends NavigateToTopRatingScreen effect`() = runTest {
        viewModel.effect.test {
            viewModel.onShowAllTopRatingClicked()
            assertEquals(HomeScreenEffect.NavigateToTopRatingScreen, awaitItem())
        }
    }

    @Test
    fun `onMoodSelected updates selectedMood in state`() = runTest {
        val sampleMood = UserMood.ANGRY

        viewModel.onMoodSelected(sampleMood)


        val selectedMood = viewModel.state.value.moodPickerUiState.selectedMood?.userMood
        assertEquals(sampleMood, selectedMood)
    }

    @Test
    fun `onGetNowClicked updates openMovieDialog and loads movies`() = runTest {
        val sampleMood = UserMood.DEPRESSED
        val moviesFromUseCase = listOf(
            fakeMovieEntity("MoodMovie1"), fakeMovieEntity("MoodMovie2")
        )

        coEvery { getMoviesByMoodUseCase(any()) } returns moviesFromUseCase

        viewModel.onGetNowClicked(sampleMood)


        advanceUntilIdle()
        val uiState = viewModel.state.value.moodPickerUiState
        assertTrue(uiState.openMovieDialog)
        assertFalse(uiState.isLoading)
        assertTrue(uiState.movies.isNotEmpty())
        assertNull(uiState.error)
    }

    @Test
    fun `getContinueWatchingMedia should throw error`() = runTest {
        coEvery { getWatchedMovieUseCase(any()) } throws NotFoundException("Not Found")
        viewModel.getContinueWatchingMedia()
        advanceUntilIdle()

        assertEquals("Not Found", viewModel.state.value.continueWatchingUiState.errorMessage)
    }

    @Test
    fun `getTopRatingMovieAndTvShows should throw error`() = runTest {
        coEvery { getTopRatedMoviesUseCase(any()) } throws NotFoundException("Not Found")
        viewModel
        advanceUntilIdle()
        assertEquals("Not Found", viewModel.state.value.topRatedMediaUiState.errorMessage)
    }

    @Test
    fun `onSearchClicked sends navigation effect`() = runTest {

        viewModel.effect.test {
            viewModel.onSearchClicked()
            assertEquals(HomeScreenEffect.NavigateToSearchScreen, awaitItem())
        }
    }

    @Test
    fun `onMovieCardClicked sends navigation effect`() = runTest {
        viewModel.effect.test {
            viewModel.onMovieCardClicked(99L)
            assertEquals(HomeScreenEffect.NavigateToMovieDetailsScreen(99L), awaitItem())
        }
    }

    @Test
    fun `onClickViewDetails calls onDismissMoodPickerDialog and sends navigation effect`() =
        runTest {
            val spyViewModel = spyk(viewModel)

            spyViewModel.effect.test {
                spyViewModel.onClickViewDetails()

                verify(exactly = 1) { spyViewModel.onDismissMoodPickerDialog() }

                assertEquals(
                    HomeScreenEffect.NavigateToMovieDetailsScreen(0L), awaitItem()
                )

            }
        }

    @Test
    fun `onGetNowClicked triggers error handling on use case failure`() = runTest {
        val sampleMood = UserMood.DEPRESSED

        coEvery { getMoviesByMoodUseCase(any()) } throws NotFoundException("Failed to load movies")

        viewModel.onGetNowClicked(sampleMood)

        advanceUntilIdle()

        val uiState = viewModel.state.value.moodPickerUiState
        assertTrue(uiState.openMovieDialog)
        assertFalse(uiState.isLoading)
        assertEquals("Failed to load movies", uiState.error?.message)
        assertTrue(uiState.movies.isEmpty())
    }

    @Test
    fun `onChangeUpcomingMovieGenre updates selectedGenres and movieGenres correctly`() = runTest {
        viewModel.onChangeUpcomingMovieGenre(1)

        val state = viewModel.state.value

        assertEquals(1, state.selectedGenres)


        state.movieGenres.forEach { genre ->
            if (genre.id == 1) {
                assertTrue(genre.isSelected)
            } else {
                assertFalse(genre.isSelected)
            }
        }

        assertFalse(state.isLoading)
    }

    @Test
    fun `getUpComingMoviesByGenre should throw error`() = runTest {

        coEvery { getUpComingMoviesUseCase() } throws NotFoundException("Not Found")


        viewModel.onChangeUpcomingMovieGenre(1)


        advanceUntilIdle()


        val errorMessage = viewModel.state.value.upcomingMoviesUiState.errorMessage
        assertEquals("Not Found", errorMessage)


        assertFalse(viewModel.state.value.upcomingMoviesUiState.isLoading)
    }

    private fun fakeMovieEntity(
        title: String = "Movie",
        id: Long = Random.nextLong(),
        rating: Double = 5.0,
        genreIds: List<Int> = listOf(1)
    ) = Movie(
        id = id,
        title = title,
        rating = rating,
        releaseDate = "2025-01-01",
        posterURL = "https://example.com/poster.jpg",
        screenShot = "https://example.com/screenshot.jpg",
        description = "A description",
        genres = genreIds.map { Genre(it, "Genre$it") },
        duration = 120,
        hasVideo = true,
        companyProductions = emptyList(),
        originCountry = "US",
        galleryUrl = emptyList(),
        reviews = emptyList(),
        isFavourite = false
    )

    private fun fakeTVShowEntity(
        title: String = "TV Show",
        id: Long = Random.nextLong(),
        rating: Double = 7.0,
        genreIds: List<Int> = listOf(10)
    ) = TVShow(
        id = id,
        title = title,
        rating = rating,
        releaseDate = "2025-01-01",
        posterURL = "https://example.com/poster.jpg",
        screenShot = "https://example.com/screenshot.jpg",
        description = "A description",
        genres = genreIds.map { Genre(it, "Genre$it") },
        duration = 60,
        companyProductions = emptyList(),
        originCountry = "US",
        numberOfSeasons = 3,
        hasVideo = false,
        galleryUrl = emptyList()
    )
}
