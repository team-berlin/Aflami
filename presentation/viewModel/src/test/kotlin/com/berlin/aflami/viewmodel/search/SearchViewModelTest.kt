package com.berlin.aflami.viewmodel.search

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.test
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.entity.Genre
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import usecase.movie.ClearMoviesSearchHistoryUseCase
import usecase.movie.DeleteQueryFromMoviesHistoryUseCase
import usecase.movie.GetMovieGenresUseCase
import usecase.movie.GetRecentMoviesHistoryUseCase
import usecase.movie.GetSearchMoviesUseCase
import usecase.movie.SaveRecentMoviesHistoryUseCase
import usecase.tvshow.ClearTVShowSearchHistoryUseCase
import usecase.tvshow.DeleteQueryFromTVShowsHistoryUseCase
import usecase.tvshow.GetRecentTVShowHistoryUseCase
import usecase.tvshow.GetSearchTVShowsUseCase
import usecase.tvshow.GetTVShowGenresUseCase
import usecase.tvshow.SaveRecentTVShowsHistoryUseCase


@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    // region Mocks
    private val searchMoviesUseCase: GetSearchMoviesUseCase = mockk()
    private val searchTVShowsUseCase: GetSearchTVShowsUseCase = mockk()
    private val recentMoviesHistoryUseCase: GetRecentMoviesHistoryUseCase = mockk()
    private val recentTvShowHistoryUseCase: GetRecentTVShowHistoryUseCase = mockk()
    private val saveRecentMoviesHistoryUseCase: SaveRecentMoviesHistoryUseCase = mockk()
    private val saveRecentTVShowHistoryUseCase: SaveRecentTVShowsHistoryUseCase = mockk()
    private val deleteQueryFromMoviesHistoryUseCase: DeleteQueryFromMoviesHistoryUseCase = mockk()
    private val deleteQueryFromTVShowHistoryUseCase: DeleteQueryFromTVShowsHistoryUseCase = mockk()
    private val clearMoviesSearchHistoryUseCase: ClearMoviesSearchHistoryUseCase = mockk()
    private val clearTvShowSearchHistoryUseCase: ClearTVShowSearchHistoryUseCase = mockk()
    private val getMovieGenresUseCase: GetMovieGenresUseCase = mockk()
    private val getTVShowGenresUseCase: GetTVShowGenresUseCase = mockk()

    // endregion
    private lateinit var searchViewModel: SearchViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        coEvery { getMovieGenresUseCase() } returns emptyList()
        coEvery { getTVShowGenresUseCase() } returns emptyList()
        coEvery { recentMoviesHistoryUseCase() } returns emptyList()
        coEvery { recentTvShowHistoryUseCase() } returns emptyList()

        createViewModel()
    }

    private fun createViewModel() {
        searchViewModel = SearchViewModel(
            searchMoviesUseCase,
            searchTVShowsUseCase,
            recentMoviesHistoryUseCase,
            recentTvShowHistoryUseCase,
            saveRecentMoviesHistoryUseCase,
            saveRecentTVShowHistoryUseCase,
            deleteQueryFromMoviesHistoryUseCase,
            deleteQueryFromTVShowHistoryUseCase,
            clearMoviesSearchHistoryUseCase,
            clearTvShowSearchHistoryUseCase,
            getMovieGenresUseCase,
            getTVShowGenresUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `init block calls necessary functions`() = runTest {
        coVerify(exactly = 1) { recentMoviesHistoryUseCase() }
        coVerify(exactly = 1) { recentTvShowHistoryUseCase() }
        coVerify(exactly = 1) { getMovieGenresUseCase() }
        coVerify(exactly = 1) { getTVShowGenresUseCase() }
    }

    @Test
    fun `loadRecentSearch should load recent searches successfully`() = runTest {
        // Arrange
        coEvery { recentMoviesHistoryUseCase() } returns RECENT_MOVIES_SEARCHES
        coEvery { recentTvShowHistoryUseCase() } returns RECENT_TV_SHOW_SEARCHES

        // Act
        searchViewModel.loadRecentSearches()
        advanceUntilIdle()

        // Assert
        val expected = (RECENT_MOVIES_SEARCHES + RECENT_TV_SHOW_SEARCHES).distinct()
        val state = searchViewModel.state.value.recentSearches
        assertThat(state).isEqualTo(expected)
    }

    @Test
    fun `updateRecentSearchesWithError updates state with error message and sets loading to false`() =
        runTest {
            // Arrange
            val errorMessage = "Test error message"
            val errorUiState = ErrorUiState(errorMessage)

            // Act
            searchViewModel.updateRecentSearchesWithError(errorUiState)
            advanceUntilIdle()

            // Assert
            assertThat(searchViewModel.state.value.errorMessage).isEqualTo(errorMessage)
            assertThat(searchViewModel.state.value.isLoading).isFalse()
        }

    @Test
    fun `updateScreenStateToError updates state with error message and sets loading to false`() =
        runTest {
            // Arrange
            val errorMessage = "Screen error"
            val errorUiState = ErrorUiState(errorMessage)

            // Act
            searchViewModel.updateScreenStateToError(errorUiState)
            advanceUntilIdle()

            // Assert
            assertThat(searchViewModel.state.value.errorMessage).isEqualTo(errorMessage)
            assertThat(searchViewModel.state.value.isLoading).isFalse()
        }

    @Test
    fun `onRecentSearchClicked updates search query`() = runTest {
        val query = "test query"
        searchViewModel.onRecentSearchClicked(query)
        advanceUntilIdle()

        assertThat(searchViewModel.state.value.searchQuery.text).isEqualTo(query)
    }

    @Test
    fun `onRecentSearchCleared should delete query from history`() = runTest {
        // Given
        val query = "test query"
        coEvery { deleteQueryFromMoviesHistoryUseCase(query) } returns Unit
        coEvery { deleteQueryFromTVShowHistoryUseCase(query) } returns Unit

        // When
        searchViewModel.onRecentSearchCleared(query)

        // Then
        coVerify { deleteQueryFromMoviesHistoryUseCase(query) }
        coVerify { deleteQueryFromTVShowHistoryUseCase(query) }
    }

    @Test
    fun `onAllRecentSearchesCleared should clear all history`() = runTest {
        // Given
        coEvery { clearMoviesSearchHistoryUseCase() } returns Unit
        coEvery { clearTvShowSearchHistoryUseCase() } returns Unit

        // When
        searchViewModel.onAllRecentSearchesCleared()

        // Then
        coVerify { clearMoviesSearchHistoryUseCase() }
        coVerify { clearTvShowSearchHistoryUseCase() }
        assertEquals(emptyList<String>(), searchViewModel.state.value.recentSearches)
    }

    @Test
    fun `onTabOptionClicked should update selected tab`() = runTest {
        // Given
        val tabOption = TabOption.TV_SHOWS

        // When
        searchViewModel.onTabOptionClicked(tabOption)

        // Then
        assertEquals(tabOption, searchViewModel.state.value.selectedTabOption)
        assertThat(searchViewModel.state.value.isLoading).isFalse()
    }

    @Test
    fun `onMediaCardClicked should send navigation effect for movie`() = runTest {
        // Given
        val mediaId = 123L
        searchViewModel.onTabOptionClicked(TabOption.MOVIES)

        // When
        searchViewModel.onMediaCardClicked(mediaId)

        // Then
        searchViewModel.effect.test {
            val effect = awaitItem() as SearchScreenEffect.NavigatedToMovieDetailsScreen
            assertEquals(mediaId, effect.id)
            assertEquals(MediaType.MOVIE.name, effect.mediaType)
        }
    }

    @Test
    fun `onMediaCardClicked should send navigation effect for tv show`() = runTest {
        // Given
        val mediaId = 123L
        searchViewModel.onTabOptionClicked(TabOption.TV_SHOWS)

        // When
        searchViewModel.onMediaCardClicked(mediaId)

        // Then
        searchViewModel.effect.test {
            val effect = awaitItem() as SearchScreenEffect.NavigatedToMovieDetailsScreen
            assertEquals(mediaId, effect.id)
            assertEquals(MediaType.TV_SHOW.name, effect.mediaType)
        }
    }

    @Test
    fun `onFilterButtonClicked should show filter dialog`() = runTest {
        // When
        searchViewModel.onFilterButtonClicked()

        // Then
        assertThat(searchViewModel.state.value.isDialogVisible).isTrue()
        assertThat(searchViewModel.state.value.isLoading).isFalse()
    }

    @Test
    fun `onCancelClicked should hide filter dialog`() = runTest {
        // Given
        searchViewModel.onFilterButtonClicked() // Show dialog first

        // When
        searchViewModel.onCancelClicked()

        // Then
        assertThat(searchViewModel.state.value.isDialogVisible).isFalse()
    }

    @Test
    fun `onSearchCleared should clear search query and hide dialog`() = runTest {
        // Given
        searchViewModel.onSearchQueryChanged(TextFieldValue("test"))
        searchViewModel.onFilterButtonClicked()

        // When
        searchViewModel.onSearchCleared()

        // Then
        assertEquals(TextFieldValue(""), searchViewModel.state.value.searchQuery)
        assertThat(searchViewModel.state.value.isLoading).isFalse()
        assertThat(searchViewModel.state.value.isDialogVisible).isFalse()
    }

    @Test
    fun `onRatingStarChanged should update movie rating when movies tab selected`() = runTest {
        // Given
        val rating = 4.5f
        searchViewModel.onTabOptionClicked(TabOption.MOVIES)

        // When
        searchViewModel.onRatingStarChanged(rating)

        // Then
        assertEquals(
            rating,
            searchViewModel.state.value.filterItemUiState.filterMovieSelected.selectedRating
        )
    }

    @Test
    fun `onRatingStarChanged should update tv show rating when tv shows tab selected`() = runTest {
        // Given
        val rating = 3.5f
        searchViewModel.onTabOptionClicked(TabOption.TV_SHOWS)

        // When
        searchViewModel.onRatingStarChanged(rating)

        // Then
        assertEquals(
            rating,
            searchViewModel.state.value.filterItemUiState.filterTvShowSelected.selectedRating
        )
    }

    @Test
    fun `onFilterGenreChanged should update movie genre when movies tab selected`() = runTest {
        // Given
        val genreId = 1
        searchViewModel.onTabOptionClicked(TabOption.MOVIES)

        // When
        searchViewModel.onFilterGenreChanged(genreId)

        // Then
        assertEquals(
            genreId,
            searchViewModel.state.value.filterItemUiState.filterMovieSelected.selectedGenres
        )
        assertThat(searchViewModel.state.value.isLoading).isFalse()
    }

    @Test
    fun `onApplyButtonClicked should apply filter and hide dialog`() = runTest {
        // Given
        searchViewModel.onFilterButtonClicked() // Show dialog first

        // When
        searchViewModel.onApplyButtonClicked()

        // Then
        assertThat(searchViewModel.state.value.isDialogVisible).isFalse()
        assertThat(searchViewModel.state.value.isLoading).isTrue()
    }

    @Test
    fun `onClearButtonClicked should clear movie filters when movies tab selected`() = runTest {
        // Given
        searchViewModel.onTabOptionClicked(TabOption.MOVIES)
        searchViewModel.onRatingStarChanged(4.0f)
        searchViewModel.onFilterGenreChanged(1)

        // When
        searchViewModel.onClearButtonClicked()

        // Then
        val movieFilter = searchViewModel.state.value.filterItemUiState.filterMovieSelected
        assertEquals(0f, movieFilter.selectedRating)
        assertEquals(-1, movieFilter.selectedGenres)
        assertThat(searchViewModel.state.value.isLoading).isFalse()
    }

    @Test
    fun `onClearButtonClicked should clear tv show filters when tv shows tab selected`() = runTest {
        // Given
        searchViewModel.onTabOptionClicked(TabOption.TV_SHOWS)
        searchViewModel.onRatingStarChanged(3.0f)
        searchViewModel.onFilterGenreChanged(2)

        // When
        searchViewModel.onClearButtonClicked()

        // Then
        val tvShowFilter = searchViewModel.state.value.filterItemUiState.filterTvShowSelected
        assertEquals(0f, tvShowFilter.selectedRating)
        assertEquals(-1, tvShowFilter.selectedGenres)
        assertThat(searchViewModel.state.value.isLoading).isFalse()
    }

    @Test
    fun `onBackClicked should send NavigatedBack effect`() = runTest {
        // Arrange
        val job = launch(testDispatcher) {
            searchViewModel.effect.collect { EFFECTS.add(it) }
        }

        // Act
        searchViewModel.onBackClicked()
        advanceUntilIdle()

        // Assert
        assertThat(EFFECTS).contains(SearchScreenEffect.NavigatedBack)
        job.cancel()
    }

    @Test
    fun `onWorldSearchCardClicked should send NavigateToWorldSearchScreen effect`() = runTest {
        // Arrange
        val job = launch(testDispatcher) {
            searchViewModel.effect.collect { EFFECTS.add(it) }
        }

        // Act
        searchViewModel.onWorldSearchCardClicked()
        advanceUntilIdle()

        // Assert
        assertThat(EFFECTS).contains(SearchScreenEffect.NavigateToWorldSearchScreen)
        job.cancel()
    }

    @Test
    fun `onActorSearchCardClicked should send NavigateToActorSearchScreen effect`() = runTest {
        // Arrange
        val job = launch(testDispatcher) {
            searchViewModel.effect.collect { EFFECTS.add(it) }
        }

        // Act
        searchViewModel.onActorSearchCardClicked()
        advanceUntilIdle()

        // Assert
        assertThat(EFFECTS).contains(SearchScreenEffect.NavigateToActorSearchScreen)
        job.cancel()
    }

    @Test
    fun `onItemClicked should update search query and set loading`() = runTest {
        // Given
        val query = TextFieldValue("clicked item")

        // When
        searchViewModel.onItemClicked(query)

        // Then
        assertEquals(query, searchViewModel.state.value.searchQuery)
        assertThat(searchViewModel.state.value.isLoading).isTrue()
    }

    @Test
    fun `should load movie and tv show genres on initialization`() = runTest {
        // Then
        coVerify { getMovieGenresUseCase() }
        coVerify { getTVShowGenresUseCase() }

        val movieGenres =
            searchViewModel.state.value.filterItemUiState.filterMovieSelected.genreUiStates
        val tvShowGenres =
            searchViewModel.state.value.filterItemUiState.filterTvShowSelected.genreUiStates

        assertThat(movieGenres.isNotEmpty()).isTrue()
        assertThat(tvShowGenres.isNotEmpty()).isTrue()

        assertEquals("All", movieGenres.first().name)
        assertEquals("All", tvShowGenres.first().name)
    }

    @Test
    fun `deleteQueryFromHistory calls delete use cases and reloads recent searches`() = runTest {
        // Arrange
        val query = "test query"

        coEvery { deleteQueryFromMoviesHistoryUseCase(query) } just Runs
        coEvery { deleteQueryFromTVShowHistoryUseCase(query) } just Runs
        coEvery { recentMoviesHistoryUseCase() } returns listOf("movie1")
        coEvery { recentTvShowHistoryUseCase() } returns listOf("tvshow1")

        // Act
        searchViewModel.deleteQueryFromHistory(query)
        advanceUntilIdle()

        // Assert
        coVerify(exactly = 1) { deleteQueryFromMoviesHistoryUseCase(query) }
        coVerify(exactly = 1) { deleteQueryFromTVShowHistoryUseCase(query) }
        assertThat(searchViewModel.recentSearchState.value).isEqualTo(listOf("movie1", "tvshow1"))
    }

    @Test
    fun `clearSearchHistory calls clear use cases and reloads recent searches`() = runTest {
        // Arrange
        coEvery { clearMoviesSearchHistoryUseCase() } just Runs
        coEvery { clearTvShowSearchHistoryUseCase() } just Runs
        coEvery { recentMoviesHistoryUseCase() } returns listOf("movie1")
        coEvery { recentTvShowHistoryUseCase() } returns listOf("tvshow1")

        // Act
        searchViewModel.clearSearchHistory()
        advanceUntilIdle()

        // Assert
        coVerify(exactly = 1) { clearMoviesSearchHistoryUseCase() }
        coVerify(exactly = 1) { clearTvShowSearchHistoryUseCase() }
        assertThat(searchViewModel.recentSearchState.value).isEqualTo(listOf("movie1", "tvshow1"))
    }

    @Test
    fun `updateRecentSearchClearedWithError updates state with error message, sets loading and dialog visibility`() =
        runTest {
            // Arrange
            val errorMessage = "Clear error"
            val errorUiState = ErrorUiState(errorMessage)

            // Act
            searchViewModel.updateRecentSearchClearedWithError(errorUiState)
            advanceUntilIdle()

            // Assert
            val state = searchViewModel.state.value
            assertThat(state.errorMessage).isEqualTo(errorMessage)
            assertThat(state.isLoading).isFalse()
            assertThat(state.isDialogVisible).isFalse()
        }

    @Test
    fun `defaultGenreUiStates includes All genre selected by default`() {
        // Arrange
        val genres = emptyList<Genre>()

        // Act
        val result = searchViewModel.defaultGenreUiStates(genres)

        // Assert
        val allGenre = result.first()
        assertThat(allGenre.id).isEqualTo(-1)
        assertThat(allGenre.name).isEqualTo("All")
        assertThat(allGenre.isSelected).isTrue()
    }

    @Test
    fun `defaultGenreUiStates maps genres correctly with isSelected false`() {
        // Arrange
        val genres = listOf(
            Genre(id = 1, name = "Action"),
        )

        // Act
        val result = searchViewModel.defaultGenreUiStates(genres)
        val mappedGenres = result.drop(1)

        // Assert
        assertThat(mappedGenres[0].id).isEqualTo(1)
        assertThat(mappedGenres[0].name).isEqualTo("Action")
        assertThat(mappedGenres[0].isSelected).isFalse()
    }

    @Test
    fun `onSearchActionClicked should call onSearchQueryChanged with current search query`() =
        runTest {
            // Arrange
            val searchQuery = TextFieldValue("test search query")
            searchViewModel.onSearchQueryChanged(searchQuery)

            // Act
            searchViewModel.onSearchActionClicked()
            advanceUntilIdle()

            // Assert
            assertEquals(searchQuery, searchViewModel.state.value.searchQuery)
        }

    companion object {
        val EFFECTS = mutableListOf<SearchScreenEffect>()

        val RECENT_MOVIES_SEARCHES = listOf("movie1", "movie2")
        val RECENT_TV_SHOW_SEARCHES = listOf("movie2", "tvshow1")
    }
}
