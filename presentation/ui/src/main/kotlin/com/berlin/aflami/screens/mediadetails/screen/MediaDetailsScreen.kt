package com.berlin.aflami.screens.mediadetails.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.berlin.aflami.screens.mediadetails.components.MediaCast
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.mediadetails.MediaDetailsViewmodel
import com.berlin.aflami.viewmodel.mediadetails.MediaInteractionListener
import com.berlin.aflami.viewmodel.uistate.MediaCastUiState
import com.berlin.aflami.viewmodel.uistate.MediaDetailsUiState
import com.berlin.ui.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun MediaDetailsScreen(
    viewModel: MediaDetailsViewmodel = koinViewModel(),
    navController: NavController
) {
    val mediaCastState by viewModel.uiState.collectAsState()

    MediaDetailsContent(
        detailsState = mediaCastState,
        listener = viewModel,
        navController = navController
    )
}

@Composable
fun MediaDetailsContent(
    detailsState: MediaDetailsUiState,
    listener: MediaInteractionListener,
    navController: NavController
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.cast),
                style = Theme.textStyle.headline.small,
                color = Theme.color.textColors.title
            )
            Text(
                text = stringResource(R.string.all),
                style = Theme.textStyle.label.medium,
                color = Theme.color.primary
            )
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(detailsState.castState.size) {
                MediaCast(
                    name = detailsState.castState[it].name,
                    poster = detailsState.castState[it].poster
                )
            }

        }
    }
}