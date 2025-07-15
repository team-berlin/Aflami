package com.berlin.aflami.screens.mediadetails.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.berlin.aflami.viewmodel.uistate.MediaDetailsScreenUiState
import com.berlin.ui.R
import com.example.navigation.Destination
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
    )
    LaunchedEffect(viewModel.castDetailsNavigationState) {
        viewModel.castDetailsNavigationState.collect {
            navController.navigate(Destination.CastScreen.route)
        }
    }


}

@Composable
fun MediaDetailsContent(
    detailsState: MediaDetailsScreenUiState,
    listener: MediaInteractionListener,
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
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
                    color = Theme.color.primary,
                    modifier = Modifier
                        .clickable {
                            listener.onShowCastClicked(505)
                        }
                )
            }

            LazyRow(
                contentPadding = PaddingValues(start = 16.dp, end = 8.dp),
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(detailsState.mediaCast.size) {
                    MediaCast(
                        modifier = Modifier.size(78.dp),
                        name = detailsState.mediaCast[it].name,
                        poster = detailsState.mediaCast[it].poster
                    )
                }

            }
        }
    }
}