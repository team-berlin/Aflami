package com.berlin.aflami.viewmodel.categories

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import app.cash.turbine.test
import com.berlin.aflami.viewmodel.categories.movie.MediaByCategoryArgs
import com.berlin.aflami.viewmodel.categories.movie.MediaByCategoryScreenEffect
import com.berlin.aflami.viewmodel.categories.movie.MoviesByCategoryScreenViewModel
import com.berlin.aflami.viewmodel.search.GenreUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import com.berlin.entity.Genre
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import usecase.movie.GetMovieGenresUseCase
import usecase.movie.GetMoviesByCategoryUseCase

class MoviesByCategoryScreenViewModelTest {
    private val getMovieByGenresUseCase: GetMoviesByCategoryUseCase = mockk()
    private val getMoviesGenreUseCase: GetMovieGenresUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    val fakeHandle = SavedStateHandle(
        mapOf(
            "categoryId" to 10L,
        )
    )

    private lateinit var viewModel: MoviesByCategoryScreenViewModel
    private  var mediaByCategoryArg=MediaByCategoryArgs(
        fakeHandle
    )


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        viewModel= MoviesByCategoryScreenViewModel(
            getMovieByGenresUseCase,
            getMoviesGenreUseCase,
            testDispatcher,
            mediaByCategoryArg
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onBackClicked sends NavigateBack effect`() = runTest {

        viewModel.effect.test {
            viewModel.onBackClicked()
            assertEquals(MediaByCategoryScreenEffect.NavigateBack, awaitItem())
        }
    }

    @Test
    fun `onMediaCardClicked sends NavigateToMediaDetails effect`() = runTest {

        viewModel.effect.test {
            viewModel.onMediaCardClicked(42L)
            assertEquals(
                MediaByCategoryScreenEffect.NavigateToMediaDetails(42L),
                awaitItem()
            )
        }
    }

    @Test
    fun `loadGenresMovies updates state with genres`() = runTest {
        val domainGenres = listOf(
            Genre(id = 10, name = "Action"),
            Genre(id = 20, name = "Drama")
        )
        coEvery { getMoviesGenreUseCase() } returns domainGenres
        viewModel.loadGenresMovies()
        advanceUntilIdle()
        viewModel.state.test {
            val newState = awaitItem()
            assertEquals(2, newState.moviesGenres.size)
            assertTrue(newState.moviesGenres.first().isSelected) // categoryId = 10
        }
    }

    @Test
    fun `onCategoryCardClicked updates selectedCategoryId and selection flags`() = runTest {
        val uiGenres = listOf(
            GenreUiState(id = 1, name = "Action", isSelected = false),
            GenreUiState(id = 2, name = "Drama", isSelected = false)
        )

        // Seed initial genres using public function
        viewModel.updateScreenWithNewMovieGenres(uiGenres)

        viewModel.state.test {
            skipItems(1) // skip the seeded state emission
            viewModel.onCategoryCardClicked(2)
            val updated = awaitItem()
            assertEquals(2, updated.selectedCategoryId)
            assertTrue(updated.moviesGenres[1].isSelected)
            assertFalse(updated.moviesGenres[0].isSelected)
        }
    }

    @Test
    fun `getMoviesByCategory updates state with PagingData`() = runTest {
        coEvery { getMovieByGenresUseCase(any(),any()) } returns listOf()

        viewModel.state.test {
            skipItems(1)
            viewModel.loadGenresMovies()
            val newState = awaitItem()
            assertNotNull(newState.moviesPagingDataFlow)
        }
    }
}

