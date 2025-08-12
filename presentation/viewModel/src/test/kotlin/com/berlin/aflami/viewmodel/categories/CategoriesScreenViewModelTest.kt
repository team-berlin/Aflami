package com.berlin.aflami.viewmodel.categories

import app.cash.turbine.test
import com.berlin.aflami.viewmodel.categories.categories.CategoriesScreenEffect
import com.berlin.aflami.viewmodel.categories.categories.CategoriesScreenViewModel
import com.berlin.aflami.viewmodel.search.TabOption
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.toGenreUiState
import com.berlin.entity.Genre
import com.berlin.exception.NotFoundException
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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import usecase.movie.GetMovieGenresUseCase
import usecase.tvshow.GetTVShowGenresUseCase

class CategoriesScreenViewModelTest {
    private val getMovieGenresUseCase: GetMovieGenresUseCase = mockk()
    private val getTVShowGenresUseCase: GetTVShowGenresUseCase = mockk()
    private lateinit var viewModel: CategoriesScreenViewModel
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load movies and tv genres successfully`() = runTest {
        // Given
        val movieGenres = listOf(Genre(id = 1, name = "Action"))
        val tvGenres = listOf(Genre(id = 2, name = "Drama"))

        coEvery { getMovieGenresUseCase() } returns movieGenres
        coEvery { getTVShowGenresUseCase() } returns tvGenres

        // When
        viewModel =
            CategoriesScreenViewModel(getMovieGenresUseCase, getTVShowGenresUseCase, testDispatcher)

        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertEquals(movieGenres.map { it.toGenreUiState() }, state.moviesGenres)
        assertEquals(tvGenres.map { it.toGenreUiState() }, state.tvShowGenres)
        assertFalse(state.isLoading)
        assertNull(state.errorMessage)
    }

    @Test
    fun `init should handle error when loading movie genres`() = runTest {
        coEvery { getMovieGenresUseCase() } throws NotFoundException("Not Found")
        coEvery { getTVShowGenresUseCase() } returns emptyList()

        viewModel = CategoriesScreenViewModel(
            getMovieGenresUseCase, getTVShowGenresUseCase, testDispatcher
        )

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals("Not Found", state.errorMessage)
        assertFalse(state.isLoading)
    }

    @Test
    fun `onCategoryCardClicked should send navigation effect`() = runTest {
        coEvery { getMovieGenresUseCase() } returns emptyList()
        coEvery { getTVShowGenresUseCase() } returns emptyList()

        viewModel = CategoriesScreenViewModel(
            getMovieGenresUseCase, getTVShowGenresUseCase, testDispatcher
        )

        viewModel.onCategoryCardClicked(42, MediaType.MOVIE)

        viewModel.effect.test {
            assertEquals(
                CategoriesScreenEffect.NavigateToMediaScreen(
                    42, MediaType.MOVIE
                ), awaitItem()
            )
        }
    }

    @Test
    fun `onTabOptionClicked should update selected tab`() = runTest {
        coEvery { getMovieGenresUseCase() } returns emptyList()
        coEvery { getTVShowGenresUseCase() } returns emptyList()

        viewModel = CategoriesScreenViewModel(
            getMovieGenresUseCase, getTVShowGenresUseCase, testDispatcher
        )

        val tabOption = TabOption.MOVIES
        viewModel.onTabOptionClicked(tabOption)

        val state = viewModel.state.value
        assertEquals(tabOption, state.selectedTabOption)
        assertFalse(state.isLoading)
    }
}
