package com.berlin.aflami.viewmodel.searchcountry

import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.util.getCountryIsoCode
import com.berlin.entity.Movie
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import usecase.SearchByCountryUseCase

class SearchByCountryViewModelTest {

    private lateinit var viewModel: SearchByCountryViewModel
    private lateinit var searchByCountryUseCase: SearchByCountryUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        searchByCountryUseCase = mockk()
        viewModel = SearchByCountryViewModel(searchByCountryUseCase)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onCountryNameChanged updates state with filtered results`() = runTest {
        val input = "Egy"
        viewModel.onCountryNameChanged(input)

        advanceUntilIdle()

        val state = viewModel.state.value

        assertThat(state.query).isEqualTo(input)
        assertThat(state.filteredCountries).isNotEmpty()
    }

    @Test
    fun `should hide dropdown when query is empty`() = runTest {
        viewModel.onCountryNameChanged("")

        val state = viewModel.state.value
        assertThat(state.query).isEmpty()
        assertThat(state.filteredCountries.isEmpty() || state.dropDownExpanded.not()).isTrue()
    }

    @Test
    fun `should hide dropdown when onDismissDropDown called`() = runTest {
        viewModel.onDismissDropDown()
        assertThat(viewModel.state.value.dropDownExpanded).isFalse()
    }

    @Test
    fun `should emits NavigatedBack effect when onBackClicked called`() = runTest {
        val effects = mutableListOf<SearchByCountryEffect>()
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.effect.collect { effects.add(it) }
        }

        viewModel.onBackClicked()
        advanceUntilIdle()

        assertThat(effects.contains(SearchByCountryEffect.NavigatedBack)).isTrue()
        job.cancel()
    }

    @Test
    fun `should emits NavigatedToMovieDetailsScreen effect when onMovieClicked called`() = runTest {
        val effects = mutableListOf<SearchByCountryEffect>()
        val movieId = 101
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.effect.collect { effects.add(it) }
        }

        viewModel.onMovieClicked(movieId)
        advanceUntilIdle()

        assertThat(effects.contains(SearchByCountryEffect.NavigatedToMovieDetailsScreen(movieId))).isTrue()
        job.cancel()
    }

    @Test
    fun `should call usecase when onCountryClicked clicked and updates state`() = runTest {
        val isoCode = getCountryIsoCode("Egypt") ?: "EG"
        val dummyMovies = listOf(movie)

        coEvery { searchByCountryUseCase.invoke(isoCode, any()) } returns dummyMovies

        viewModel.onCountryNameChanged("Egypt")
        viewModel.onCountryClicked()

        advanceUntilIdle()

        val state = viewModel.state.value
        assertThat(state.isLoading).isTrue()
        assertThat(state.isCountrySelected).isTrue()
        assertThat(state.dropDownExpanded).isFalse()
        assertThat(state.movies).isNotNull()
    }

    private val movie = Movie(
        id = 1,
        title = "Test Movie",
        rating = 8.5,
        releaseDate = LocalDate.parse("2023-01-01"),
        poster = "poster.jpg",
        genres = emptyList(),
    )

    private val movieUIState = movie.toUIState()
}