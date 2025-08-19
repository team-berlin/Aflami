package com.berlin.aflami.screens.search.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.component.TabBar
import com.berlin.aflami.component.TabBarItem
import com.berlin.aflami.component.TextField
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.navigation.MovieDetailsDestination
import com.berlin.aflami.navigation.SearchByActorDestination
import com.berlin.aflami.navigation.SearchByCountryDestination
import com.berlin.aflami.navigation.TVShowDetailsDestination
import com.berlin.aflami.screens.NoInternetConnectionPlaceholder
import com.berlin.aflami.screens.search.components.CountryTourExploring
import com.berlin.aflami.screens.search.components.NoDataSearch
import com.berlin.aflami.screens.search.components.SearchData
import com.berlin.aflami.screens.search.components.SearchSuggestionHub
import com.berlin.aflami.screens.search.getMovieGenreIcon
import com.berlin.aflami.screens.search.getMovieGenreName
import com.berlin.aflami.screens.search.getTvShowGenreIcon
import com.berlin.aflami.screens.search.getTvShowGenreName
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.search.FilterInteractionListener
import com.berlin.aflami.viewmodel.search.SearchScreenEffect
import com.berlin.aflami.viewmodel.search.SearchScreenInteractionListener
import com.berlin.aflami.viewmodel.search.SearchUiState
import com.berlin.aflami.viewmodel.search.SearchViewModel
import com.berlin.aflami.viewmodel.search.TabOption
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import com.berlin.aflami.viewmodel.shareduistate.TVShowUiState
import com.berlin.designsystem.R

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val navController = Theme.navController
    val state by viewModel.state.collectAsStateWithLifecycle()
    val recentSearchState by viewModel.recentSearchState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            onReceiveSearchEffect(effect = effect, navController = navController)
        }
    }

    AnimatedVisibility(
        enter = EnterTransition.None,
        exit = ExitTransition.None,
        visible = !state.isLoading
    ) {
        SearchScreenContent(
            state = state,
            listenerSearch = viewModel,
            filterSearch = viewModel,
            recentSearchState = recentSearchState,
            onDeleteItem = viewModel::deleteQueryFromHistory,
            onClearAll = viewModel::clearSearchHistory,
            onItemClick = viewModel::onItemClicked
        )
    }
}

private fun onReceiveSearchEffect(navController: NavController, effect: SearchScreenEffect) {
    when (effect) {
        is SearchScreenEffect.NavigatedBack -> navController.popBackStack()
        is SearchScreenEffect.NavigatedToMovieDetailsScreen -> {
            navController.navigate(MovieDetailsDestination(movieId = effect.id))
        }

        is SearchScreenEffect.NavigatedToTVShowDetailsScreen -> {
            navController.navigate(
                TVShowDetailsDestination(
                    tvShowId = effect.id
                )
            )

        }

        is SearchScreenEffect.NavigateToActorSearchScreen -> {
            navController.navigate(SearchByActorDestination)
        }

        is SearchScreenEffect.NavigateToWorldSearchScreen -> {
            navController.navigate(SearchByCountryDestination)
        }
    }
}

@Composable
private fun SearchScreenContent(
    state: SearchUiState,
    listenerSearch: SearchScreenInteractionListener,
    filterSearch: FilterInteractionListener,
    recentSearchState: List<String>,
    onItemClick: (TextFieldValue) -> Unit,
    onDeleteItem: (String) -> Unit,
    onClearAll: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val moviesSearchResult = state.movies.collectAsLazyPagingItems()
    val tvShowsSearchResult = state.tvShows.collectAsLazyPagingItems()

    val isSearchEmpty = state.searchQuery.text.isBlank()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
            .statusBarsPadding()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) {
                focusManager.clearFocus()
            }
            .focusable(),
    ) {
        Column {
            TopBar(
                modifier = Modifier.padding(vertical = 8.dp),
                title = {
                    Text(
                        text = stringResource(R.string.search),
                        style = Theme.textStyle.title.large,
                        color = Theme.color.textColors.title
                    )
                },
                leadingIcon = {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Theme.color.surfaceHigh)
                            .clickable { listenerSearch.onBackClicked() }
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(com.berlin.ui.R.drawable.arrow_left),
                            contentDescription = stringResource(com.berlin.ui.R.string.arrow_back),
                            tint = Theme.color.textColors.title
                        )
                    }
                }
            )

            TextField(
                text = state.searchQuery,
                modifier = Modifier
                    .padding( horizontal = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Theme.color.surfaceHigh),
                hintText = stringResource(R.string.search_hint_text),
                maxLines = 1,
                borderColor = Theme.color.stroke,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() },
                    onSearch = { listenerSearch.onSearchActionClicked() }
                ),
                onValueChange = listenerSearch::onSearchQueryChanged,
                trailingIcon = R.drawable.filter_vertical,
                onTrailingIconClicked = listenerSearch::onFilterButtonClicked
            )

            when {
                isSearchEmpty -> {
                    SearchEmptyContent(
                        recentSearchState = recentSearchState,
                        onDeleteItem = onDeleteItem,
                        onItemClick = onItemClick,
                        onClearAll = onClearAll,
                        listenerSearch = listenerSearch
                    )
                }

                else -> {
                    SearchResultTabs(
                        state = state,
                        movies = moviesSearchResult,
                        tvShows = tvShowsSearchResult,
                        listenerSearch = listenerSearch
                    )
                }
            }

            if (state.isDialogVisible) {
                FilterDialogContent(state, filterSearch)
            }
        }
    }
}

@Composable
private fun SearchEmptyContent(
    recentSearchState: List<String>,
    onDeleteItem: (String) -> Unit,
    onItemClick: (TextFieldValue) -> Unit,
    onClearAll: () -> Unit,
    listenerSearch: SearchScreenInteractionListener
) {
    Text(
        stringResource(R.string.search_suggestions_hub),
        color = Theme.color.textColors.title,
        style = Theme.textStyle.title.medium,
        modifier = Modifier.padding(top = 8.dp, bottom = 12.dp, start = 16.dp)
    )
    SearchSuggestionHub(
        Modifier.padding(horizontal = 16.dp),
        onSearchByActorClick = { listenerSearch.onActorSearchCardClicked() },
        onSearchByCountryClick = { listenerSearch.onWorldSearchCardClicked() },
    )
    if (recentSearchState.isNotEmpty()) {
        SearchData(
            recentSearch = recentSearchState,
            onDeleteItem = onDeleteItem,
            onItemClick = onItemClick,
            onClearAll = onClearAll,
        )
    } else {
        NoDataSearch()
    }
}

@Composable
private fun SearchResultTabs(
    state: SearchUiState,
    movies: LazyPagingItems<MovieUiState>,
    tvShows: LazyPagingItems<TVShowUiState>,
    listenerSearch: SearchScreenInteractionListener
) {
    TabBar(
        selectedTabIndex = state.selectedTabOption.index,
        containerColor = Theme.color.surface,
        items = listOf(
            TabBarItem(
                text = stringResource(R.string.movies),
                isSelected = state.selectedTabOption == TabOption.MOVIES,
            ),
            TabBarItem(
                text = stringResource(R.string.tv_shows),
                isSelected = state.selectedTabOption == TabOption.TV_SHOWS,
            )
        ),
        onTabChange = {
            listenerSearch.onTabOptionClicked(
                when (it) {
                    0 -> TabOption.MOVIES
                    1 -> TabOption.TV_SHOWS
                    else -> throw IllegalArgumentException("Invalid tab index")
                }
            )
        },
    )

    when (state.selectedTabOption) {
        TabOption.MOVIES -> MediaGrid(movies, MediaType.MOVIE, listenerSearch)
        TabOption.TV_SHOWS -> MediaGrid(tvShows, MediaType.TV_SHOW, listenerSearch)
    }
}

@Composable
private fun <T : Any> MediaGrid(
    items: LazyPagingItems<T>,
    mediaType: MediaType,
    listenerSearch: SearchScreenInteractionListener
) {
    val isLoading = items.loadState.refresh is LoadState.Loading
    val hasError = items.loadState.refresh is LoadState.Error

    when {
        isLoading -> CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            text = stringResource(com.berlin.ui.R.string.loading)
        )
        hasError -> NoInternetConnectionPlaceholder(onClick = { items.retry() })
        items.itemCount == 0 -> ErrorContent()
        else -> LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Adaptive(minSize = 160.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items.itemCount) { index ->
                val media = items[index]
                when (mediaType) {
                    MediaType.MOVIE -> (media as? MovieUiState)?.let {
                        MediaCard(
                            modifier = Modifier.height(222.dp),
                            onClick = { listenerSearch.onMoviesCardClicked(it.id) },
                            mediaImg = it.posterUrl,
                            title = it.title,
                            typeOfMedia = MediaType.MOVIE.name,
                            date = it.releaseDate,
                            rating = it.rating
                        )
                    }

                    MediaType.TV_SHOW -> (media as? TVShowUiState)?.let {
                        MediaCard(
                            modifier = Modifier.height(222.dp),
                            onClick = { listenerSearch.onTVShowsCardClicked(it.id) },
                            mediaImg = it.posterUrl,
                            title = it.title,
                            typeOfMedia = MediaType.TV_SHOW.name,
                            date = it.releaseDate,
                            rating = it.rating
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterDialogContent(
    state: SearchUiState,
    filterSearch: FilterInteractionListener
) {
    when (state.selectedTabOption) {
        TabOption.MOVIES -> FilterDialog(
            state = state.filterItemUiState.filterMovieSelected,
            filterListener = filterSearch,
            getIcon = ::getMovieGenreIcon,
            getGenreName = ::getMovieGenreName
        )

        TabOption.TV_SHOWS -> FilterDialog(
            state = state.filterItemUiState.filterTvShowSelected,
            filterListener = filterSearch,
            getIcon = ::getTvShowGenreIcon,
            getGenreName = ::getTvShowGenreName
        )
    }
}

@Composable
fun ErrorContent() {
    CountryTourExploring(
        modifier = Modifier.fillMaxSize(),
        image = painterResource(com.berlin.ui.R.drawable.no_search_result),
        titleId = com.berlin.ui.R.string.no_search_result,
        messageId = com.berlin.ui.R.string.please_try_with_another_keyword
    )
}
