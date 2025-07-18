package com.berlin.aflami.viewmodel.searchcountry

import android.util.Log
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import com.berlin.entity.Movie
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
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
import usecase.SearchByCountryUseCase

class SearchByCountryViewModelTest {

    private lateinit var viewModel: SearchByCountryViewModel
    private lateinit var searchByCountryUseCase: SearchByCountryUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        mockkStatic(Dispatchers::class)
        every { Dispatchers.IO } returns testDispatcher

        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0

        searchByCountryUseCase = mockk()
        viewModel = SearchByCountryViewModel(searchByCountryUseCase)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `onCountryNameChanged should update countryName and dropdown state`() = runTest {
        val country = "Egy"
        viewModel.onCountryNameChanged(country)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.countryName).isEqualTo("Egy")
        assertThat(state.dropDownExpanded).isTrue()
        assertThat(state.filteredCountries.keys.any {
            it.startsWith(
                "Egy",
                ignoreCase = true
            )
        }).isTrue()
    }

    @Test
    fun `onCountrySelected(name) should update country name only`() = runTest {
        viewModel.onCountrySelected("Egypt")
        val state = viewModel.uiState.value
        assertThat(state.countryName).isEqualTo("Egypt")
    }

    @Test
    fun `onCountrySelected() should update state with movies on success`() = runTest {
        val countryName = "Egypt"
        val countryCode = "EG"
        val movie = Movie(1, "Movie 2", 8.0, LocalDate(2021, 1, 1), emptyList(), "img")
        val movieUI = MovieUIState(1, "Movie 2", "8.0", "2021", emptyList(), "img")
        viewModel.onCountryNameChanged(countryName)
        viewModel.countriesWithCodeMap = mapOf(countryName to countryCode)

        coEvery { searchByCountryUseCase(countryCode, any()) } returns listOf(movie)

        viewModel.onCountrySelected()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.movies).containsExactly(movieUI)
    }

    @Test
    fun `onCountrySelected() should show error if country name is invalid`() = runTest {
        viewModel.onCountryNameChanged("Unknownland")
        viewModel.countriesWithCodeMap = emptyMap()

        viewModel.onCountrySelected()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.error).isEqualTo("Invalid country name")
    }

    @Test
    fun `onCountrySelected() should show error on failure`() = runTest {
        val countryName = "Egypt"
        val countryCode = "EG"
        val errorMessage = "Network error"

        viewModel.onCountryNameChanged(countryName)
        viewModel.countriesWithCodeMap = mapOf(countryName to countryCode)

        coEvery { searchByCountryUseCase(countryCode, any()) } throws RuntimeException(errorMessage)

        viewModel.onCountrySelected()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.error).isEqualTo(errorMessage)
        assertThat(state.movies).isEmpty()
    }

    @Test
    fun `onDismissDropDown should hide dropdown`() = runTest {
        viewModel.onDismissDropDown()
        val state = viewModel.uiState.value
        assertThat(state.dropDownExpanded).isFalse()
    }
}
