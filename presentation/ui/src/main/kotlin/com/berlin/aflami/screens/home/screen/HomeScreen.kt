package com.berlin.aflami.screens.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.berlin.aflami.component.BlurredPosterBackground
import com.berlin.aflami.component.GenersChip
import com.berlin.aflami.component.HomeBar
import com.berlin.aflami.component.MoviesPosterSlider
import com.berlin.aflami.component.SectionTitle
import com.berlin.aflami.component.moviesLists
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.home.HomeViewModel
import com.berlin.aflami.viewmodel.search.SearchViewModel
import com.berlin.aflami.viewmodel.uistate.MediaUiState
import com.berlin.designsystem.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    HomeContent(
        mediaList = state.popularMedia,
    )
}

@Composable
private fun HomeContent(
    mediaList: List<MediaUiState>,
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { mediaList.size })

    val currentMedia = mediaList.getOrNull(pagerState.currentPage)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
    ) {
        Box() {
            BlurredPosterBackground(
                imageUrl = currentMedia?.poster ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(390.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
            ) {

                HomeBar(
                    modifier = Modifier.fillMaxWidth(),
                    onSearchClicked = {}
                )

                SectionTitle(
                    title = stringResource(R.string.popular),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    icon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .size(20.dp),
                            painter = painterResource(R.drawable.trending),
                            tint = Theme.color.secondary,
                            contentDescription = stringResource(R.string.trending)
                        )
                    }
                )

                MoviesPosterSlider(
                    modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
                    poster = currentMedia?.poster ?: com.berlin.ui.R.drawable.place_holder.toString(),
                    rating = currentMedia?.rating ?: "0.0",
                    pagerState = pagerState,
                    onClick = { }
                )

                currentMedia?.let { media ->
                    Text(
                        media.title,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 8.dp),
                        style = Theme.textStyle.title.small,
                        color = Theme.color.textColors.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row() {
                        media.genre.forEach { genre ->
                            Box(modifier = Modifier.padding(end = 4.dp)) {
                                GenersChip(label = genre.toString())
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navController = TODO(),
        viewModel = TODO()
    )
}