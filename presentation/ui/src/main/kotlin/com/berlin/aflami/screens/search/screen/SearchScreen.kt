package com.berlin.aflami.screens.search.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.component.SearchSuggestionHub
import com.berlin.aflami.component.TabBar
import com.berlin.aflami.component.TabBarItem
import com.berlin.aflami.component.TextField
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.screens.search.components.CountryTourExploring
import com.berlin.aflami.screens.search.components.ErrorMessage
import com.berlin.aflami.screens.search.components.Loading
import com.berlin.aflami.screens.search.components.NoDataSearch
import com.berlin.aflami.screens.search.components.ResultGridList
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.search.SearchInteractionListener
import com.berlin.aflami.viewmodel.search.SearchUiState
import com.berlin.aflami.viewmodel.search.SearchViewModel
import com.berlin.designsystem.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel()
) {
    val searchState by viewModel.searchUIState.collectAsState()
    val movieState by viewModel.moviesUiState.collectAsState()
    val tvShowState by viewModel.tvShowUiState.collectAsState()

    val selectedTabIndex = viewModel.selectTabIndex

    val textValue = if (selectedTabIndex == 0) movieState.movieName else tvShowState.tvShowName

    SearchScreenContent(
        searchState = searchState,
        listener = viewModel,
        textValue = textValue,
        selectedTabIndex = selectedTabIndex,
        onFocusChanged = viewModel::onFocusChanged,
        onTabChange = viewModel::onTabChange,
        clearSearchState = viewModel::clearSearchState
    )
}

@Composable
private fun SearchScreenContent(
    searchState: SearchUiState,
    listener: SearchInteractionListener,
    selectedTabIndex: Int,
    textValue: String,
    onFocusChanged: (Boolean) -> Unit,
    onTabChange: (Int) -> Unit,
    clearSearchState: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
            .clickable(
                indication = null, interactionSource = remember { MutableInteractionSource() }) {
                clearSearchState()
            },
    ) {
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
                    Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Theme.color.surfaceHigh), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(R.drawable.arrow_left),
                        contentDescription = stringResource(R.string.icon_cd),
                        tint = Theme.color.textColors.title
                    )
                }
            }
        )

        TextField(
            text = textValue,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp),
            innerModifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Theme.color.surfaceHigh)
                .onFocusChanged {
                    onFocusChanged(it.isFocused)
                },
            hintText = stringResource(R.string.search_hint_text),
            isEnabled = true,
            maxLines = 1,
            borderColor = Theme.color.stroke,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = {
                listener::onSearchClick
            }),
            onValueChange = listener::onSearchClick,
            trailingIcon = R.drawable.filter_vertical,
        )

        when (searchState) {
            is SearchUiState.Init -> {
                Text(
                    stringResource(R.string.search_suggestions_hub),
                    color = Theme.color.textColors.title,
                    style = Theme.textStyle.title.medium,
                    modifier = Modifier.padding(top = 8.dp, bottom = 12.dp, start = 16.dp)
                )

                SearchSuggestionHub(
                    Modifier.padding(horizontal = 16.dp),
                    onWorldTourClick = {},
                    onFindActorClick = {}
                )

                NoDataSearch()

            }

            is SearchUiState.Searching -> {
                // Handle success state if needed
                TabBar(
                    containerColor = Theme.color.surface,
                    items = listOf(
                        TabBarItem(
                            text = stringResource(R.string.movies),
                            isSelected = selectedTabIndex == 0
                        ), TabBarItem(
                            text = stringResource(R.string.tv_shows),
                            isSelected = selectedTabIndex == 1
                        )
                    ),
                    onTabChange = {
                        onTabChange(it)
                    },
                )

                when (searchState) {
                    is SearchUiState.Searching.Init -> {
                        NoDataSearch()
                    }

                    is SearchUiState.Searching.Loading -> {
                        Loading(Modifier)
                    }

                    is SearchUiState.Searching.Success -> {
                        ResultGridList(
                            modifier = Modifier.padding(top = 11.dp, bottom = 6.dp),
                            items = searchState.data
                        ) { media ->
                            MediaCard(
                                modifier = Modifier.size(width = 160.dp, height = 222.dp),
                                mediaImg = media.poster,
                                title = media.title,
                                typeOfMedia = if (selectedTabIndex == 0) "Movies" else "Tv Show",
                                date = media.releaseYear,
                                rating = media.rating.toDouble()
                            )
                        }

                        ResultGridList(
                            items = searchState.data
                        ) { media ->
                            MediaCard(
                                modifier = Modifier.size(width = 160.dp, height = 222.dp),
                                mediaImg = media.poster,
                                title = media.title,
                                typeOfMedia = "Movies",
                                date = media.releaseYear,
                                rating = media.rating.toDouble()
                            )
                        }

                        CountryTourExploring(
                            modifier = Modifier,
                            painterResource(com.berlin.ui.R.drawable.no_search_result),
                            com.berlin.ui.R.string.no_search_result,
                            com.berlin.ui.R.string.please_try_with_another_keyword
                        )
                    }

                    is SearchUiState.Searching.Error -> {
                        ErrorMessage(Modifier, searchState.errorMessage)
                    }
                }

            }

            is SearchUiState.NoResult -> {
                // Handle no result state if needed
            }
        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    AflamiTheme {
        SearchScreen()
    }
}