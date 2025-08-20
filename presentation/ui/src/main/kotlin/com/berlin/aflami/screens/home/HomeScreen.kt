package com.berlin.aflami.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.berlin.aflami.component.BlurredPosterBackground
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.GenersChip
import com.berlin.aflami.component.HomeBar
import com.berlin.aflami.component.SectionTitle
import com.berlin.aflami.component.SnackBar
import com.berlin.aflami.component.SnackBarStatus
import com.berlin.aflami.navigation.ContinueWatchingDestination
import com.berlin.aflami.navigation.MovieDetailsDestination
import com.berlin.aflami.navigation.SearchDestination
import com.berlin.aflami.navigation.TVShowDetailsDestination
import com.berlin.aflami.navigation.TopRatingMediaDestination
import com.berlin.aflami.screens.NoInternetConnectionPlaceholder
import com.berlin.aflami.screens.home.component.MoodPickerDialog
import com.berlin.aflami.screens.home.sections.ContinueWatchingHomeSections
import com.berlin.aflami.screens.home.sections.MoodPickerSection
import com.berlin.aflami.screens.home.sections.PosterSlider
import com.berlin.aflami.screens.home.sections.TopRatingHomeSections
import com.berlin.aflami.screens.home.sections.UpcomingMoviesSection
import com.berlin.aflami.screens.search.getMovieGenreName
import com.berlin.aflami.screens.search.getTvShowGenreName
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.home.HomeScreenEffect
import com.berlin.aflami.viewmodel.home.HomeScreenInteractionListener
import com.berlin.aflami.viewmodel.home.HomeScreenState
import com.berlin.aflami.viewmodel.home.HomeScreenViewModel
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.ui.R
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val homeScreenState by viewModel.state.collectAsStateWithLifecycle()
    val navController = Theme.navController

    LaunchedEffect(Unit) {

        launch {
            viewModel.effect.collect { homeScreenEffect ->
                onReceiveHomeScreenEffect(navController, homeScreenEffect)
            }
        }

        viewModel.getContinueWatchingMedia()
    }

    AnimatedVisibility(
        enter = EnterTransition.None,
        exit = ExitTransition.None,
        visible = homeScreenState.isLoading
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(), text = stringResource(R.string.loading)
        )
    }
    AnimatedVisibility(
        enter = EnterTransition.None,
        exit = ExitTransition.None,
        visible = homeScreenState.error != null
    ) {
        NoInternetConnectionPlaceholder()
    }

    AnimatedVisibility(
        enter = EnterTransition.None,
        exit = ExitTransition.None,
        visible = !homeScreenState.isLoading
    ) {
        HomeContent(
            homeScreenState = homeScreenState, homeScreenInteractionListener = viewModel
        )
    }
}

@Composable
private fun AnimatedSnackBar(
    message: String, isSnackBarVisible: Boolean, modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isSnackBarVisible, enter = slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight }, animationSpec = spring(
                stiffness = Spring.StiffnessLow, dampingRatio = Spring.DampingRatioMediumBouncy
            )
        ) + fadeIn(),

        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> -fullHeight }, animationSpec = spring(
                stiffness = Spring.StiffnessMedium, dampingRatio = Spring.DampingRatioNoBouncy
            )
        ) + fadeOut()
    ) {
        SnackBar(
            isVisible = isSnackBarVisible,
            status = SnackBarStatus.SUCCESS,
            text = message,
            modifier = modifier,
            iconPainter = painterResource(id = com.berlin.designsystem.R.drawable.success),
        )
    }
}

private fun onReceiveHomeScreenEffect(
    navController: NavController,
    homeScreenEffect: HomeScreenEffect,
) {
    when (homeScreenEffect) {
        is HomeScreenEffect.NavigateToContinueWatchingScreen -> {
            navController.navigate(
                ContinueWatchingDestination
            )
        }

        is HomeScreenEffect.NavigateToSearchScreen -> {
            navController.navigate(
                SearchDestination
            )
        }

        is HomeScreenEffect.NavigateToTopRatingScreen -> {
            navController.navigate(
                TopRatingMediaDestination
            )
        }

        HomeScreenEffect.NavigateToMoodPickerDialog -> TODO()


        is HomeScreenEffect.NavigateToMovieDetailsScreen -> {
            navController.navigate(
                MovieDetailsDestination(homeScreenEffect.movieId)
            )
        }

        is HomeScreenEffect.NavigateToTVShowDetailsScreen -> {
            navController.navigate(
                TVShowDetailsDestination(homeScreenEffect.tvShowId)
            )
        }

    }
}

@Composable
private fun HomeContent(
    homeScreenState: HomeScreenState,
    homeScreenInteractionListener: HomeScreenInteractionListener,
) {
    val listState = rememberLazyListState()
    val appBarFadeHeightPx = with(LocalDensity.current) { 50.dp.roundToPx() }


    val appBarAlpha by remember {
        derivedStateOf {
            val offset =
                if (listState.firstVisibleItemIndex == 0) listState.firstVisibleItemScrollOffset else appBarFadeHeightPx
            (offset / appBarFadeHeightPx.toFloat()).coerceIn(0f, 1f)
        }
    }

    val animatedAppBarAlpha by animateFloatAsState(appBarAlpha)
    val appBarBgColor = Theme.color.surface.copy(alpha = animatedAppBarAlpha)

    val pagerState = rememberPagerState(
        initialPage = 1, pageCount = { homeScreenState.popularMediaUiState.popularMedia.size })
    AnimatedVisibility(
        enter = EnterTransition.None,
        exit = ExitTransition.None,
        visible = homeScreenState.isLoading
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(), text = stringResource(R.string.loading)
        )
    }
    val continueWatchingMediaList: List<MediaUiState> =
        homeScreenState.continueWatchingUiState.continueWatchingMediaList
    val topRatedMediaList: List<MediaUiState> = homeScreenState.topRatedMediaUiState.topRatedMedia
    val popularMedia =
        homeScreenState.popularMediaUiState.popularMedia.getOrNull(pagerState.currentPage)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
    ) {
        AnimatedVisibility(
            enter = EnterTransition.None,
            exit = ExitTransition.None,
            visible = homeScreenState.isLoading.not()
        ) {
            LazyColumn(
                modifier = Modifier.padding(bottom = 64.dp), state = listState
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Theme.color.surface)
                            .padding(bottom = 6.dp)
                    ) {
                        Box {
                            BlurredPosterBackground(
                                imageUrl = popularMedia?.poster ?: "",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(390.dp)
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .statusBarsPadding()
                                    .padding(top = 56.dp)
                            ) {
                                SectionTitle(
                                    title = stringResource(com.berlin.designsystem.R.string.popular),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp),
                                    icon = {
                                        Icon(
                                            modifier = Modifier
                                                .padding(start = 8.dp)
                                                .size(20.dp),
                                            painter = painterResource(com.berlin.designsystem.R.drawable.trending),
                                            tint = Theme.color.secondary,
                                            contentDescription = stringResource(com.berlin.designsystem.R.string.trending)
                                        )
                                    })

                                PosterSlider(
                                    modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
                                    mediaList = homeScreenState.popularMediaUiState.popularMedia,
                                    pagerState = pagerState,
                                    onMovieItemClicked = {
                                        homeScreenInteractionListener.onMovieCardClicked(
                                            it
                                        )
                                    },
                                    onTVShowItemClicked = {
                                        homeScreenInteractionListener.onTVShowCardClicked(
                                            it
                                        )
                                    })

                                popularMedia?.let { media ->
                                    Text(
                                        media.title,
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)
                                            .padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
                                        style = Theme.textStyle.title.small,
                                        color = Theme.color.textColors.title,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    LazyRow(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .align(Alignment.CenterHorizontally),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        items(items = media.genre) { genreId ->
                                            when (media.mediaType) {
                                                MediaType.MOVIE -> homeScreenState.movieGenres.forEach { movieGenre ->
                                                    if (movieGenre.id == genreId) Box(
                                                        modifier = Modifier.padding(
                                                            horizontal = 4.dp
                                                        )
                                                    ) {
                                                        GenersChip(
                                                            label = stringResource(
                                                                getMovieGenreName(movieGenre.id)
                                                            )
                                                        )
                                                    }
                                                }

                                                MediaType.TV_SHOW -> homeScreenState.tVShowGenres.forEach { tVShowGenre ->
                                                    if (tVShowGenre.id == genreId) Box(
                                                        modifier = Modifier.padding(
                                                            horizontal = 4.dp
                                                        )
                                                    ) {
                                                        GenersChip(
                                                            label = stringResource(
                                                                getTvShowGenreName(
                                                                    tVShowGenre.id
                                                                )
                                                            )
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
                if (continueWatchingMediaList.isNotEmpty()) {
                    item {
                        ContinueWatchingHomeSections(
                            modifier = Modifier
                                .background(Theme.color.surface)
                                .padding(bottom = 24.dp),
                            seeAllOnClick = {
                                homeScreenInteractionListener.onShowAllContinueWatchingClicked()
                            },
                            state = continueWatchingMediaList.take(10),
                            sectionTitleId = R.string.continue_watching,
                            onMovieItemClicked = {
                                homeScreenInteractionListener.onMovieCardClicked(
                                    it
                                )
                            },
                            onTVShowItemClicked = {
                                homeScreenInteractionListener.onTVShowCardClicked(
                                    it
                                )
                            })
                    }
                }
                if (topRatedMediaList.isNotEmpty()) {
                    item {
                        TopRatingHomeSections(
                            modifier = Modifier
                                .background(Theme.color.surface)
                                .padding(bottom = 24.dp)
                                .background(Theme.color.surface),
                            seeAllOnClick = { homeScreenInteractionListener.onShowAllTopRatingClicked() },
                            state = topRatedMediaList,
                            sectionTitleId = R.string.top_rating,
                            onMovieItemClicked = {
                                homeScreenInteractionListener.onMovieCardClicked(
                                    it
                                )
                            },
                            onTVShowItemClicked = {
                                homeScreenInteractionListener.onTVShowCardClicked(
                                    it
                                )
                            })
                    }
                }
                item {
                    MoodPickerSection(
                        modifier = Modifier.background(Theme.color.surface),
                        homeScreenState,
                        homeScreenInteractionListener
                    )
                }
                item {
                    UpcomingMoviesSection(
                        movies = homeScreenState.upcomingMoviesUiState.upcomingMovies,
                        genres = homeScreenState.movieGenres,
                        onMovieClick = {
                            homeScreenInteractionListener.onUpcomingMoviesCardClicked(
                                it
                            )
                        },
                        onGenreClick = { homeScreenInteractionListener.onChangeUpcomingMovieGenre(it) },
                        modifier = Modifier.background(Theme.color.surface)
                    )
                }
            }
        }

        AnimatedVisibility(homeScreenState.moodPickerUiState.openMovieDialog) {
            with(homeScreenState.moodPickerUiState.selectedMovie) {
                MoodPickerDialog(
                    mediaImg = posterUrl,
                    title = title,
                    typeOfMedia = MediaType.MOVIE.name,
                    date = releaseDate,
                    rate = rating,
                    onDismiss = { homeScreenInteractionListener.onDismissMoodPickerDialog() },
                    onClickViewDetails = { homeScreenInteractionListener.onClickViewDetails() },
                    onClickGetAnotherMovie = { homeScreenInteractionListener.onClickGetAnotherMovie() },
                )
            }

        }
        HomeBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(appBarBgColor)
                .statusBarsPadding(),
            onSearchClicked = {
                homeScreenInteractionListener.onSearchClicked()
            },
            containerColor = Color.Unspecified
        )
        AnimatedSnackBar(
            message = stringResource(R.string.log_in_successful),
            modifier = Modifier
                .statusBarsPadding()
                .align(Alignment.TopCenter)
                .padding(top = 16.dp),
            isSnackBarVisible = homeScreenState.showSuccessSnackBar
        )
    }
}