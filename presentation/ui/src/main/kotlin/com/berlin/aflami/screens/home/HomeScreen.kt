package com.berlin.aflami.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.BlurredPosterBackground
import com.berlin.aflami.component.GenersChip
import com.berlin.aflami.component.HomeBar
import com.berlin.aflami.component.MovieCard
import com.berlin.aflami.component.SectionTitle
import com.berlin.aflami.screens.home.component.HomeSections
import com.berlin.aflami.screens.home.screen.MoviesPosterSlider
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.home.HomeInteractionListener
import com.berlin.aflami.viewmodel.home.HomeScreenEffect
import com.berlin.aflami.viewmodel.home.HomeViewModel
import com.berlin.aflami.viewmodel.home.uistate.HomeUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.ui.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel= koinViewModel(),
    onEffect:(HomeScreenEffect) -> Unit
){
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getContinueWatchingMedia()
        viewModel.effect.collect{
            onEffect(it)
        }
    }
    HomeContent(
        state=state,
        listener = viewModel
    )

}


@Composable
fun HomeContent(
    state:HomeUiState,
//    mediaList = state.popularMedia,
    listener:HomeInteractionListener
){

    Column (
        modifier = Modifier.padding(top = 6.dp)
    ){
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
                        }
                    )

                    MoviesPosterSlider(
                        modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
                        mediaList = mediaList,
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
                        Row{
                            media.genre.forEach { genre ->
                                Box(
                                    modifier = Modifier
                                        .padding(end = 4.dp)
                                        .align(Alignment.CenterVertically)
                                ) {
                                    GenersChip(label = genre.toString())
                                }
                            }
                        }
                    }
                }
            }
        }
        HomeSections(
            onShowAllContinueWatchingClick = {
                listener.onShowAllContinueWatchingClicked()
            },
            state = state.mediaContinueWatching,
            sectionTitleId = R.string.continue_watching
        )
    }





}

@Composable
fun MoviesPosterSlider(
    modifier: Modifier = Modifier,
    mediaList: List<MediaUiState>,
    onClick: (MediaUiState) -> Unit = {},
    pagerState: PagerState,
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = 244.dp
    val contentPadding = (screenWidth - itemWidth) / 2
    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fixed(itemWidth),
        contentPadding = PaddingValues(horizontal = contentPadding),
        modifier = modifier.fillMaxWidth()
    ) { pageIndex ->
        val mediaItem = mediaList.getOrNull(pageIndex)
        mediaItem?.let {
            MovieCard(
                isCentered = pageIndex == pagerState.currentPage,
                onClick = { onClick(it) },
                rating = it.rating,
                posterImageUrl = it.poster
            )
        }
    }
}
