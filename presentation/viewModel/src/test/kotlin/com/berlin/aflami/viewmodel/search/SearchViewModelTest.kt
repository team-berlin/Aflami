package com.berlin.aflami.viewmodel.search


import android.util.Log
import com.berlin.aflami.viewmodel.mapper.toUIState
import com.berlin.aflami.viewmodel.search_actor.GenreType
import com.berlin.aflami.viewmodel.uistate.MediaUiState
import com.berlin.aflami.viewmodel.uistate.TVShowUiState
import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.Before
import org.junit.Test
import usecase.GetSearchMoviesUseCase
import usecase.GetSearchTvShowsUseCase

class SearchViewModelTest {
    private lateinit var viewModel: SearchViewModel
    private lateinit var searchMoviesUseCase: GetSearchMoviesUseCase
    private lateinit var searchTvShowsUseCase: GetSearchTvShowsUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        searchMoviesUseCase = mockk()
        searchTvShowsUseCase = mockk()
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0


        mockkStatic(Dispatchers::class)
        every { Dispatchers.IO } returns testDispatcher

        viewModel = SearchViewModel(searchMoviesUseCase, searchTvShowsUseCase)
    }

    @Test
    fun `onFocusChanged true should set searchUIState to Searching Init`() = runTest {
        viewModel.onFocusChanged(true)
        assertThat(viewModel.searchUIState.value).isEqualTo(SearchUiState.Searching.Init)
    }

    @Test
    fun `updateRating should update selected rating in filterUiState`() = runTest {
        viewModel.updateRating(7.5f)
        advanceUntilIdle()
        assertThat(viewModel.filterUiState.value.selectedRating).isEqualTo(7.5f)
    }

    @Test
    fun `toggleGenre should update genre in filterUiState`() = runTest {
        viewModel.toggleGenre(GenreType.ACTION)
        advanceUntilIdle()
        assertThat(viewModel.filterUiState.value.selectedGenre.type).isEqualTo(GenreType.ACTION)
    }

    @Test
    fun `onSearchClick should update UI state to Success when movies are returned`() = runTest {
        val movie = Movie(1, "Movie 2", 8.0, LocalDate(2021, 1, 1), emptyList(), "img")

        coEvery { searchMoviesUseCase(any(), any()) } returns listOf(movie)

        viewModel.updateSearchQuery("test")
        advanceUntilIdle()

        val state = viewModel.searchUIState.value
        assertThat(state).isInstanceOf(SearchUiState.Searching.Success::class.java)

        val successState = state as SearchUiState.Searching.Success
        assertThat(successState.data).containsExactly(
            MediaUiState(
                id = 1,
                title = "Movie 2",
                rating = "8.0",
                releaseYear = "2021",
                genre = emptyList(),
                poster = "img"
            )
        )
    }

    @Test
    fun `onSearchClick blank query should set state to Searching Init`() = runTest {
        viewModel.onSearchClick("")
        assertThat(viewModel.searchUIState.value).isEqualTo(SearchUiState.Searching.Init)
    }

    @Test
    fun `onSearchClick error should update error state`() = runTest {
        val query = "fail"
        val exceptionMessage = "Network error"
        coEvery { searchMoviesUseCase(query, any()) } throws RuntimeException(exceptionMessage)

        viewModel.updateSearchQuery(query)
        advanceUntilIdle()

        val state = viewModel.searchUIState.value as SearchUiState.Searching.Error
        assertThat(state.errorMessage).isEqualTo(exceptionMessage)
    }

    @Test
    fun `onFilterIconClicked should show dialog`() = runTest {
        viewModel.onFilterIconClicked()
        assertThat(viewModel.filterDialogState.value).isTrue()
    }

    @Test
    fun `onDismiss should hide dialog`() = runTest {
        viewModel.onDismiss()
        assertThat(viewModel.filterDialogState.value).isFalse()
    }

    @Test
    fun `clearFilters should reset filterUiState`() = runTest {
        viewModel.updateRating(9.0f)
        viewModel.toggleGenre(GenreType.HORROR)
        viewModel.clearFilters()
        advanceUntilIdle()

        val state = viewModel.filterUiState.value
        assertThat(state.selectedRating).isEqualTo(1f)
        assertThat(state.selectedGenre.type).isEqualTo(GenreType.ALL)
    }

    @Test
    fun `clearSearchState should reset query and uiState`() = runTest {
        viewModel.clearSearchState()
        assertThat(viewModel.searchUIState.value).isEqualTo(SearchUiState.Init)
        assertThat(viewModel.queryFlow.value).isEmpty()
    }
    @Test
    fun `onSearchClick should call searchMedia with TV_SHOW when tab index is 1`() = runTest {
        // Given
        val domainTvShow = TVShow(1, "Breaking Bad", 8.5, LocalDate(2008, 1, 20), listOf(18), "bb.jpg")
        val expectedUi = MediaUiState(
            id = 1,
            title = "Breaking Bad",
            rating = "8.5",
            releaseYear = LocalDate(2008, 1, 20).toString(),
            genre = listOf(18),
            poster = "bb.jpg"
        )

        coEvery { searchTvShowsUseCase(any(), any()) } returns listOf(domainTvShow)

        viewModel.onTabChange(1)
        viewModel.updateSearchQuery("breaking bad")
        advanceUntilIdle()

        // Then
        val state = viewModel.searchUIState.value
        assertThat(state).isInstanceOf(SearchUiState.Searching.Success::class.java)

        val successState = state as SearchUiState.Searching.Success
        assertThat(successState.data).containsExactly(expectedUi)
    }
}
