package com.berlin.aflami.viewmodel.searchcountry

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.test
import com.berlin.aflami.viewmodel.util.getCountryIsoCode
import com.berlin.entity.Movie
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import usecase.movie.SearchMoviesByCountryUseCase

class SearchByCountryViewModelTest {

    private lateinit var viewModel: SearchByCountryScreenViewModel
    private lateinit var searchByCountryUseCase: SearchMoviesByCountryUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        searchByCountryUseCase = mockk()
        viewModel = SearchByCountryScreenViewModel(searchByCountryUseCase)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onCountryNameChanged updates state with filtered results`() = runTest {
        val input = "Egy"

        viewModel.onCountryNameChanged(TextFieldValue(input))
        advanceUntilIdle()

        val state = viewModel.state.value
        assertThat(state.countryName.text).isEqualTo(input)
        assertThat(state.filteredCountries).isNotEmpty()
    }

    @Test
    fun `should hide dropdown when query is empty`() = runTest {
        viewModel.onCountryNameChanged(TextFieldValue(""))

        val state = viewModel.state.value
        assertThat(state.countryName.text).isEmpty()
        assertThat(state.filteredCountries.isEmpty() || !state.dropDownExpanded).isTrue()
    }

    @Test
    fun `should hide dropdown when onDismissDropDown called`() = runTest {
        viewModel.onDismissDropDown()
        assertThat(viewModel.state.value.dropDownExpanded).isFalse()
    }

    @Test
    fun `should emit NavigatedBack effect when onBackClicked called`() = runTest {
        viewModel.effect.test {
            viewModel.onBackClicked()
            assertThat(awaitItem()).isEqualTo(SearchByCountryScreenEffect.NavigatedBack)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onMovieClicked sends NavigateToMovieDetails effect`() = runTest {
        viewModel.effect.test {
            viewModel.onMovieClicked(42L)
            assertThat(awaitItem()).isEqualTo(
                SearchByCountryScreenEffect.NavigatedToMovieDetailsScreen(42L, "MOVIE")
            )
        }
    }

    @Test
    fun `should call usecase when onCountryClicked clicked and updates state`() = runTest {
        val isoCode = getCountryIsoCode("Egypt") ?: "EG"
        val dummyMovies = listOf(movie)

        coEvery { searchByCountryUseCase.invoke(isoCode, any()) } returns dummyMovies

        viewModel.onCountryNameChanged(TextFieldValue("Egypt"))
        viewModel.onCountryClicked("Egypt")

        advanceUntilIdle()

        val state = viewModel.state.value
        assertThat(state.isLoading).isTrue()
        assertThat(state.isCountrySelected).isTrue()
        assertThat(state.dropDownExpanded).isFalse()
        assertThat(state.moviesOfCountryFlow).isNotNull()
    }
}

private val movie = Movie(
    id = 1,
    title = "Test Movie",
    rating = 8.5,
    releaseDate = LocalDate.parse("2023-01-01").toString(),
    posterURL = "poster.jpg",
    genres = emptyList(),
    screenShot = "",
    description = "",
    duration = 120,
    hasVideo = false,
    companyProductions = emptyList(),
    originCountry = "",
    galleryUrl = emptyList(),
    reviews = emptyList(),
    isFavourite = false
)