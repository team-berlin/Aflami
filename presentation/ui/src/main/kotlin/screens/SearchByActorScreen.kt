package com.berlin.aflami.screens.search.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.component.TextField
import com.berlin.aflami.component.ThemeAndLocalePreviews
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.screens.search.components.FindByActorCard
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.search_by_actor.SearchByActorUiState
import com.berlin.aflami.viewmodel.search_by_actor.SearchByActorViewModel
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import com.berlin.designsystem.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchByActorScreen(padding: PaddingValues) {

    val searchByActorViewModel: SearchByActorViewModel = koinViewModel()
    val searchByActorUiState by searchByActorViewModel.searchByActorUiState.collectAsState()

    SearchByActorScreenContent(
        padding, searchByActorUiState,
        searchByActorViewModel,
        //searchByActorViewModel::onImageSuccess
    )

}

@Composable
fun SearchByActorScreenContent(
    padding: PaddingValues,
    searchByActorUiState: SearchByActorUiState,
    searchByNameListener: SearchByActorViewModel,
    //onImageSuccess: ()->Unit,

    ) {

    Column(
        Modifier
            .padding(padding)
            .fillMaxSize()
            .background(Theme.color.surface)
    ) {
        TopBar(modifier = Modifier.padding(vertical = 8.dp), title = {
            Text(
                text = stringResource(R.string.search_by_actor),
                style = Theme.textStyle.title.large,
                color = Theme.color.textColors.title
            )
        }, leadingIcon = {
            Box(
                Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Theme.color.surfaceHigh), contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    tint = Theme.color.textColors.title,
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = stringResource(R.string.icon_cd)
                )
            }
        })
        Box(
            Modifier.padding(horizontal = 16.dp)
        ) {
            TextField(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = searchByActorUiState.actorName,
                onValueChange = searchByNameListener::onActorNameChanged,
                isEnabled = true,
                maxLines = 1,
                borderColor = Theme.color.stroke,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = {
                    searchByNameListener.onSearchClick()
                }),
                hintText = stringResource(R.string.actor_name_hint),
            )
        }

        MoviesList(
            searchByActorUiState.media,
            //onSuccess = onImageSuccess,
           // searchByActorUiState.isImageProcessing
        )

        if (searchByActorUiState.media.isEmpty()) {
            FindByActorCard(
                modifier = Modifier.padding(top = 144.dp)
            )
        }
    }
}


@Composable
fun MoviesList(
    listOfShows: List<MovieUIState>,
//    onSuccess: () -> Unit,
//    imageProcessing: Boolean
) {
    LazyVerticalGrid(
        GridCells.Adaptive(minSize = 160.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(listOfShows) { show ->
            MediaCard(
                modifier = Modifier.size(width = 160.dp, height = 222.dp),
                mediaImg = show.poster,
                title = show.title,
                typeOfMedia = show.mediaType,
                date = show.releaseYear,
                rating = show.rating,
            )
        }
    }
}


@ThemeAndLocalePreviews
@Composable
fun SearchByActorScreenContentPreview() {
    AflamiTheme {
        SearchByActorScreen(PaddingValues())
    }
}