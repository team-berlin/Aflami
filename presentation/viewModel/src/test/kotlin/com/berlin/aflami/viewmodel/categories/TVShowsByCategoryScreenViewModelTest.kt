package com.berlin.aflami.viewmodel.categories

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.berlin.aflami.viewmodel.categories.movie.MediaByCategoryArgs
import com.berlin.aflami.viewmodel.categories.movie.MediaByCategoryScreenEffect
import com.berlin.aflami.viewmodel.categories.tvshow.TVShowByCategoryScreenViewModel
import com.berlin.aflami.viewmodel.search.GenreUiState
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
import usecase.tvshow.GetTVShowGenresUseCase
import usecase.tvshow.GetTVShowsByCategoryUseCase

class TVShowsByCategoryScreenViewModelTest {
    private val getMovieByGenresUseCase: GetTVShowsByCategoryUseCase = mockk()
    private val getMoviesGenreUseCase: GetTVShowGenresUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    val fakeHandle = SavedStateHandle(
        mapOf(
            "categoryId" to 10L,
        )
    )

    private lateinit var viewModel: TVShowByCategoryScreenViewModel
    private  var mediaByCategoryArg=MediaByCategoryArgs(
        fakeHandle
    )


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel= TVShowByCategoryScreenViewModel(
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
        viewModel.loadGenresTVShow()
        advanceUntilIdle()
        viewModel.state.test {
            val newState = awaitItem()
            assertEquals(2, newState.tvShowGenres.size)
            assertTrue(newState.tvShowGenres.first().isSelected) // categoryId = 10
        }
    }

    @Test
    fun `onCategoryCardClicked updates selectedCategoryId and selection flags`() = runTest {
        val uiGenres = listOf(
            GenreUiState(id = 1, name = "Action", isSelected = false),
            GenreUiState(id = 2, name = "Drama", isSelected = false)
        )

        // Seed initial genres using public function
        viewModel.updateScreenWithNewTVShowGenres(uiGenres)

        viewModel.state.test {
            skipItems(1) // skip the seeded state emission
            viewModel.onCategoryCardClicked(2)
            val updated = awaitItem()
            assertEquals(2, updated.selectedCategoryId)
            assertTrue(updated.tvShowGenres[1].isSelected)
            assertFalse(updated.tvShowGenres[0].isSelected)
        }
    }

    @Test
    fun `getMoviesByCategory updates state with PagingData`() = runTest {
        coEvery { getMovieByGenresUseCase(any(),any()) } returns listOf()

        viewModel.state.test {
            skipItems(1)
            viewModel.loadGenresTVShow()
            val newState = awaitItem()
            assertNotNull(newState.tvShowsPagingDataFlow)
        }
    }
}

