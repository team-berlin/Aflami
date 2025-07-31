package com.berlin.aflami.screens.search.search

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.component.SearchSuggestionHub
import com.berlin.aflami.component.TabBar
import com.berlin.aflami.component.TabBarItem
import com.berlin.aflami.component.TextField
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.navigation.MediaDetailsDestination
import com.berlin.aflami.navigation.SearchByActorDestination
import com.berlin.aflami.navigation.SearchByCountryDestination
import com.berlin.aflami.screens.search.components.CountryTourExploring
import com.berlin.aflami.screens.search.components.ErrorMessage
import com.berlin.aflami.screens.search.components.Loading
import com.berlin.aflami.screens.search.components.NoDataSearch
import com.berlin.aflami.screens.search.components.SearchData
import com.berlin.aflami.screens.search.getMovieGenreIcon
import com.berlin.aflami.screens.search.getTvShowGenreIcon
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.search.FilterInteractionListener
import com.berlin.aflami.viewmodel.search.SearchInteractionListener
import com.berlin.aflami.viewmodel.search.SearchUiEffect
import com.berlin.aflami.viewmodel.search.SearchUiState
import com.berlin.aflami.viewmodel.search.SearchViewModel
import com.berlin.aflami.viewmodel.search.TabOption
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.designsystem.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
) {
    val navController = Theme.navController
    val state by viewModel.state.collectAsStateWithLifecycle()
    val recentSearchState = viewModel.recentSearchState.collectAsState()


    SearchScreenContent(
        state = state,
        listenerSearch = viewModel,
        filterSearch = viewModel,
        recentSearchState = recentSearchState.value,
        onDeleteItem = viewModel::deleteQueryFromHistory,
        onClearAll = viewModel::clearSearchHistory,
        onItemClick = viewModel::onItemClicked
    )
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            onReceiveSearchEffect(effect = effect, navController = navController)
        }
    }
}

private fun onReceiveSearchEffect(
    navController: NavController,
    effect: SearchUiEffect,
) {
    when (effect) {
        is SearchUiEffect.NavigatedBack -> navController.popBackStack()

        is SearchUiEffect.NavigatedToMovieDetailsScreen -> {

            navController.navigate(
                MediaDetailsDestination(
                    mediaId = effect.id,
                    mediaType = MediaType.valueOf("MOVIE"),
                )
            )
        }

        is SearchUiEffect.NavigateToActorSearch -> {
            navController.navigate(
                SearchByActorDestination
            )
        }

        is SearchUiEffect.NavigateToWorldSearch -> {
            navController.navigate(
                SearchByCountryDestination
            )
        }
    }

}

@Composable
private fun SearchScreenContent(
    state: SearchUiState,
    listenerSearch: SearchInteractionListener,
    filterSearch: FilterInteractionListener,
    recentSearchState: List<String>,
    onItemClick: (TextFieldValue) -> Unit,
    onDeleteItem: (String) -> Unit,
    onClearAll: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
            .statusBarsPadding()
            .clickable(
                indication = null, interactionSource = remember { MutableInteractionSource() }) {
                focusManager.clearFocus()
            }
            .focusable(),
    ) {
        Column {
            TopBar(modifier = Modifier.padding(vertical = 8.dp), title = {
                Text(
                    text = stringResource(R.string.search),
                    style = Theme.textStyle.title.large,
                    color = Theme.color.textColors.title
                )
            }, leadingIcon = {
                Box(
                    Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Theme.color.surfaceHigh)
                        .clickable {
                            listenerSearch.onBackClicked()
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(R.drawable.arrow_left),
                        contentDescription = stringResource(R.string.icon_cd),
                        tint = Theme.color.textColors.title
                    )
                }
            })

            val keyboardController = LocalSoftwareKeyboardController.current
            TextField(
                text = state.searchQuery,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Theme.color.surfaceHigh),

                hintText = stringResource(R.string.search_hint_text),
                isEnabled = true,
                maxLines = 1,
                borderColor = Theme.color.stroke,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() },
                    onSearch = {
                        listenerSearch.onSearchActionClicked()
                    }),
                onValueChange = listenerSearch::onSearchQueryChanged,
                trailingIcon = R.drawable.filter_vertical,
                onTrailingIconClicked = listenerSearch::onFilterButtonClicked
            )

            when {
                state.searchQuery.text.isBlank() -> {
                    Text(
                        stringResource(R.string.search_suggestions_hub),
                        color = Theme.color.textColors.title,
                        style = Theme.textStyle.title.medium,
                        modifier = Modifier.padding(top = 8.dp, bottom = 12.dp, start = 16.dp)
                    )
                    SearchSuggestionHub(
                        Modifier.padding(horizontal = 16.dp),
                        onSearchByActorClick = {
                            listenerSearch.onActorSearchCardClicked()
                        },
                        onSearchByCountryClick = {
                            listenerSearch.onWorldSearchCardClicked()
                        },
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

                state.searchQuery.text.isNotBlank() -> {
                    TabBar(
                        selectedTabIndex = state.selectedTabOption.index,
                        containerColor = Theme.color.surface,
                        items = listOf(
                            TabBarItem(
                                text = stringResource(R.string.movies),
                                isSelected = state.selectedTabOption == TabOption.MOVIES,
                            ), TabBarItem(
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

                    when {
                        state.searchQuery.text.isBlank() -> {
                            NoDataSearch()
                        }

                        state.isLoading -> {
                            Loading(Modifier)
                        }

                        state.errorMessage != null -> {
                            ErrorMessage(Modifier, state.errorMessage.toString())
                        }

                        else -> {
                            val movies = state.movies.collectAsLazyPagingItems()
                            val moviesLoadState = movies.loadState
                            val tvShows = state.tvShows.collectAsLazyPagingItems()
                            val tvShowsLoadState = tvShows.loadState
                            when (state.selectedTabOption) {

                                TabOption.MOVIES -> {

                                    val isEmpty =
                                        movies.itemCount == 0 && moviesLoadState.refresh is LoadState.NotLoading && moviesLoadState.append is LoadState.NotLoading
                                    if (isEmpty) {
                                        CountryTourExploring(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .align(Alignment.CenterHorizontally),
                                            painterResource(com.berlin.ui.R.drawable.no_search_result),
                                            com.berlin.ui.R.string.no_search_result,
                                            com.berlin.ui.R.string.please_try_with_another_keyword
                                        )
                                    } else if (LoadState.Loading == moviesLoadState.refresh) {
                                        Loading()
                                    } else {
                                        Box(modifier = Modifier.fillMaxSize()) {

                                            LazyVerticalGrid(
                                                modifier = Modifier.fillMaxSize(),
                                                columns = GridCells.Adaptive(minSize = 160.dp),
                                                contentPadding = PaddingValues(
                                                    start = 16.dp, end = 16.dp, top = 8.dp
                                                ),
                                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                items(
                                                    count = movies.itemCount
                                                ) { index ->
                                                    val movie = movies[index]
                                                    if (movie != null) {
                                                        MediaCard(
                                                            modifier = Modifier.height(222.dp),
                                                            onClick = {
                                                                listenerSearch.onCardClicked(
                                                                    id = movie.id
                                                                )
                                                            },
                                                            mediaImg = movie.poster,
                                                            title = movie.title,
                                                            typeOfMedia = stringResource(R.string.movies),
                                                            date = movie.releaseYear,
                                                            rating = movie.rating
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                TabOption.TV_SHOWS -> {

                                    val isEmpty =
                                        tvShows.itemCount == 0 && tvShowsLoadState.refresh is LoadState.NotLoading && tvShowsLoadState.append is LoadState.NotLoading
                                    if (isEmpty) {
                                        CountryTourExploring(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .align(Alignment.CenterHorizontally),
                                            painterResource(com.berlin.ui.R.drawable.no_search_result),
                                            com.berlin.ui.R.string.no_search_result,
                                            com.berlin.ui.R.string.please_try_with_another_keyword
                                        )
                                    } else {
                                        LazyVerticalGrid(
                                            modifier = Modifier.fillMaxSize(),
                                            columns = GridCells.Adaptive(minSize = 160.dp),
                                            contentPadding = PaddingValues(
                                                start = 16.dp, end = 16.dp, top = 8.dp
                                            ),
                                            verticalArrangement = Arrangement.spacedBy(8.dp),
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            items(
                                                count = tvShows.itemCount
                                            ) { index ->
                                                val tvShows = tvShows[index]
                                                if (tvShows != null) {

                                                    MediaCard(
                                                        modifier = Modifier.height(222.dp),
                                                        mediaImg = tvShows.poster,
                                                        title = tvShows.title,
                                                        onClick = {
                                                            listenerSearch.onCardClicked(
                                                                tvShows.id
                                                            )
                                                        },
                                                        typeOfMedia = stringResource(R.string.tv_shows),
                                                        date = tvShows.releaseYear,
                                                        rating = tvShows.rating
                                                    )

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (state.isDialogVisible) {
                when (state.selectedTabOption) {
                    TabOption.MOVIES -> {
                        FilterDialog(
                            state = state.filterItemUiState.filterMovieSelected,
                            filterListener = filterSearch,
                            getIcon = ::getMovieGenreIcon,
                        )
                    }

                    TabOption.TV_SHOWS -> {
                        FilterDialog(
                            state = state.filterItemUiState.filterTvShowSelected,
                            filterListener = filterSearch,
                            getIcon = ::getTvShowGenreIcon,
                        )
                    }
                }
            }
        }
    }
}
