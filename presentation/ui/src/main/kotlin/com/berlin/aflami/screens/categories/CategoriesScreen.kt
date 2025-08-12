package com.berlin.aflami.screens.categories

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells.Adaptive
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.berlin.aflami.component.CategoryCard
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.TabBar
import com.berlin.aflami.component.TabBarItem
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.navigation.MoviesByCategoryDestination
import com.berlin.aflami.navigation.TVShowsByCategoryDestination
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.categories.categories.CategoriesInteractionListener
import com.berlin.aflami.viewmodel.categories.categories.CategoriesScreenEffect
import com.berlin.aflami.viewmodel.categories.categories.CategoriesScreenUiState
import com.berlin.aflami.viewmodel.categories.categories.CategoriesScreenViewModel
import com.berlin.aflami.viewmodel.search.TabOption
import com.berlin.aflami.viewmodel.shareduistate.GenreUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.ui.R


@Composable
fun CategoryScreen(
    viewModel: CategoriesScreenViewModel = hiltViewModel(),

    ) {
    val navController = Theme.navController
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { newEffect ->
            categoriesReceiveEffect(navController = navController, effect = newEffect)
        }
    }

    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = state.isLoading
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            text = stringResource(R.string.loading)
        )
    }

    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = !state.isLoading
    ) {
        CategoriesContent(
            state = state, listener = viewModel
        )
    }
}

private fun categoriesReceiveEffect(navController: NavController, effect: CategoriesScreenEffect) {
    when (effect) {
        is CategoriesScreenEffect.NavigateToMediaScreen -> {
            when (effect.mediaType) {
                MediaType.MOVIE -> {
                    navController.navigate(
                        MoviesByCategoryDestination(
                            categoryId = effect.mediaId,
                        )
                    )
                }

                MediaType.TV_SHOW -> {
                    navController.navigate(
                        TVShowsByCategoryDestination(
                            categoryId = effect.mediaId,
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun CategoriesContent(
    state: CategoriesScreenUiState, listener: CategoriesInteractionListener,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
    ) {
        TopBar(
            modifier = Modifier
                .statusBarsPadding()
                .padding(vertical = 8.dp), title = {
                Text(
                    text = stringResource(R.string.catgories),
                    style = Theme.textStyle.title.large,
                    color = Theme.color.textColors.title,
                )
            }
        )

        TabBar(
            selectedTabIndex = state.selectedTabOption.index,
            containerColor = Theme.color.surface,
            items = listOf(
                TabBarItem(
                    text = stringResource(com.berlin.designsystem.R.string.movies),
                    isSelected = state.selectedTabOption == TabOption.MOVIES,
                ), TabBarItem(
                    text = stringResource(com.berlin.designsystem.R.string.tv_shows),
                    isSelected = state.selectedTabOption == TabOption.TV_SHOWS,
                )
            ),
            onTabChange = {
                listener.onTabOptionClicked(
                    when (it) {
                        0 -> TabOption.MOVIES
                        1 -> TabOption.TV_SHOWS
                        else -> throw IllegalArgumentException("Invalid tab index")
                    }
                )
            },
        )
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize(),
                    text = stringResource(R.string.loading)
                )
            }
        }
        when (state.selectedTabOption) {
            TabOption.MOVIES -> {
                ResultGrid(
                    categories = state.moviesGenres,
                    onCategoryCardClicked = listener::onCategoryCardClicked,
                    mediaType = MediaType.MOVIE
                )
            }

            TabOption.TV_SHOWS -> {
                Log.d("WOWTEST", "CategoriesContent: ${state.tvShowGenres}")
                ResultGrid(
                    categories = state.tvShowGenres,
                    onCategoryCardClicked = listener::onCategoryCardClicked,
                    mediaType = MediaType.TV_SHOW
                )
            }
        }
    }

}

@Composable
private fun ResultGrid(
    categories: List<GenreUiState>,
    modifier: Modifier = Modifier,
    onCategoryCardClicked: (Long, MediaType) -> Unit,
    mediaType: MediaType,
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize(),
        columns = Adaptive(minSize = 160.dp),
        contentPadding = PaddingValues(
            start = 16.dp, end = 16.dp, top = 8.dp, bottom = 56.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = categories,
        ) { genre ->
            CategoryCard(
                modifier = Modifier.height(71.dp),
                onClick = {
                    onCategoryCardClicked(
                        genre.id.toLong(),
                        mediaType
                    )
                },
                text = genre.name.replace(Regex("\\s*&\\s*|\\s+"), " &\n"),
                image = if (mediaType == MediaType.MOVIE) painterResource(getMovieCategoryIcon(genre.id))
                else painterResource(getTvShowCategoryIcon(genre.id)),
            )
        }
    }
}
