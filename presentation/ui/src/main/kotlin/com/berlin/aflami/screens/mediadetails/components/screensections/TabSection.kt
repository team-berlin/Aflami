package com.berlin.aflami.screens.mediadetails.components.screensections

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.Chips
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.screens.mediadetails.components.CompanyProductionSection
import com.berlin.aflami.screens.mediadetails.components.GallerySection
import com.berlin.aflami.screens.mediadetails.components.MovieDetailsMoreLikeThisSection
import com.berlin.aflami.screens.mediadetails.components.ReviewsSection
import com.berlin.aflami.screens.mediadetails.components.SeasonsSection
import com.berlin.aflami.screens.mediadetails.components.TvShowMoreLikeThisSection
import com.berlin.aflami.screens.mediadetails.components.getMovieDetailsTabsIcon
import com.berlin.aflami.screens.mediadetails.components.getTVShowDetailsTabsIcon
import com.berlin.aflami.screens.mediadetails.components.movieDetailsTabsMapper
import com.berlin.aflami.screens.mediadetails.components.tvShowDetailsTabsMapper
import com.berlin.aflami.screens.mediadetails.screen.getDisplayMessage
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.details.movie.MoviesRowSectionUiState
import com.berlin.aflami.viewmodel.details.movie.MoviesTabContent
import com.berlin.aflami.viewmodel.details.movie.MovieDetailsTabs
import com.berlin.aflami.viewmodel.details.series.TVShowDetailsTabs
import com.berlin.aflami.viewmodel.details.series.TVShowRowSectionUiState
import com.berlin.aflami.viewmodel.details.series.TVShowTabContent

@Composable
fun MovieTabSection(
    movieDetailsTabs: MovieDetailsTabs,
    onChipClick: (MovieDetailsTabs) -> Unit,
    rowState: MoviesRowSectionUiState,
    isReviewExpanded: (String) -> Boolean,
    onToggleReviewExpand: (String) -> Unit,
    onMovieCardClicked: (Long) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .padding(bottom = 12.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(MovieDetailsTabs.entries, key = { it.name }) { tab ->
            Chips(
                title = stringResource(movieDetailsTabsMapper(tab)),
                icon = painterResource(getMovieDetailsTabsIcon(tab)),
                isSelected = tab == movieDetailsTabs,
                onClick = { onChipClick(tab) }
            )
        }
    }

    Crossfade(targetState = rowState) { moviesRowSectionUiState ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
        ) {
            when (moviesRowSectionUiState) {
                is MoviesRowSectionUiState.NoDataFound,
                    -> {
                    Box(
                        Modifier.padding(top = 32.dp, bottom = 82.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.fillMaxSize(),
                            text = moviesRowSectionUiState.getDisplayMessage(),
                            style = Theme.textStyle.label.large,
                            color = Theme.color.textColors.body,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                is MoviesRowSectionUiState.Loading -> Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier =Modifier.fillMaxSize()
                    )
                }

                is MoviesRowSectionUiState.Success -> {
                    when (val tab = moviesRowSectionUiState.content) {
                        is MoviesTabContent.MoreLikeThis -> MovieDetailsMoreLikeThisSection(
                            mediaList = tab.moreMoviesLikeThis,
                            onMediaClick = onMovieCardClicked
                        )

                        is MoviesTabContent.Reviews -> ReviewsSection(
                            reviews = tab.movieReviews,
                            isExpanded = isReviewExpanded,
                            onToggleExpand = onToggleReviewExpand
                        )

                        is MoviesTabContent.Gallery -> GallerySection(mediaImages = tab.images)

                        is MoviesTabContent.CompanyProduction -> CompanyProductionSection(
                            companyProductions = tab.companyProductionsList
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TVShowTabSection(
    tvShowDetailsTabs: TVShowDetailsTabs ,
    onChipClick: (TVShowDetailsTabs) -> Unit,
    rowState: TVShowRowSectionUiState,
    isReviewExpanded: (String) -> Boolean,
    onToggleReviewExpand: (String) -> Unit,
    onTVShowCardClicked: (Long) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .padding(bottom = 12.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(TVShowDetailsTabs.entries, key = { it.name }) { tab ->
            Chips(
                title = stringResource(tvShowDetailsTabsMapper(tab)),
                icon = painterResource(getTVShowDetailsTabsIcon(tab)),
                isSelected = tab == tvShowDetailsTabs,
                onClick = { onChipClick(tab) }
            )
        }
    }

    Crossfade(targetState = rowState) { tvShowRowSectionUiState ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
        ) {
            when (tvShowRowSectionUiState) {
                is TVShowRowSectionUiState.Error,
                is TVShowRowSectionUiState.NoDataFound,
                    -> {
                    Box(
                        Modifier.padding(top = 32.dp, bottom = 82.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.fillMaxSize(),
                            text = tvShowRowSectionUiState.getDisplayMessage(),
                            style = Theme.textStyle.label.large,
                            color = Theme.color.textColors.body,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                is TVShowRowSectionUiState.Loading -> Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                is TVShowRowSectionUiState.Success -> {
                    when (val tab = tvShowRowSectionUiState.content) {
                        is TVShowTabContent.MoreLikeThis -> TvShowMoreLikeThisSection(
                            mediaList = tab.items,
                            onMediaClick = onTVShowCardClicked
                        )

                        is TVShowTabContent.Reviews -> ReviewsSection(
                            reviews = tab.reviews,
                            isExpanded = isReviewExpanded,
                            onToggleExpand = onToggleReviewExpand
                        )

                        is TVShowTabContent.Gallery -> GallerySection(mediaImages = tab.images)

                        is TVShowTabContent.CompanyProduction -> CompanyProductionSection(
                            companyProductions = tab.companyProductionStates
                        )

                        is TVShowTabContent.Season -> SeasonsSection(seasonsMap = tab.seasonToEpisodesMap)

                    }
                }
            }
        }
    }
}