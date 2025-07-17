package com.berlin.aflami.screens.mediadetails.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.berlin.aflami.screens.mediadetails.components.MoreLikeThisScreen
import com.berlin.aflami.screens.search.components.ErrorMessage
import com.berlin.aflami.screens.search.components.Loading
import com.berlin.aflami.viewmodel.mediadetails.MediaDetailsViewmodel
import com.berlin.aflami.viewmodel.mediadetails.SimilarMediaUiState
import com.berlin.aflami.viewmodel.uistate.MediaType
import org.koin.androidx.compose.koinViewModel

@Composable
fun MediaDetailsScreen(
    navController: NavController,
    viewModel: MediaDetailsViewmodel=koinViewModel()
) {
    val similarMediaState by viewModel.similarMedia.collectAsState()

    MediaDetailsContent(
        navController = navController,
        similarMediaState=similarMediaState,
        viewModel = viewModel,
        mediaId = 571,
        mediaType = MediaType.MOVIE
    )
}
@Composable
fun MediaDetailsContent(
    navController: NavController,
    viewModel: MediaDetailsViewmodel,
    mediaId: Long,
    mediaType: MediaType,
    similarMediaState:SimilarMediaUiState
) {

    Column(
        modifier = Modifier) {
        //i will delete it after merge
        Button(
            onClick = {
                viewModel.onShowMoreMediaLikeThisClicked(mediaId, mediaType)
            }
        ) {
            Text("Show More Like This")
        }

        when (similarMediaState) {
            is SimilarMediaUiState.Loading -> {
                Loading(Modifier)
            }
            is SimilarMediaUiState.Success -> {
                val similarMedia = (similarMediaState).data
                MoreLikeThisScreen(
                    mediaList = similarMedia,
                    mediaType = mediaType
                )
            }
            is SimilarMediaUiState.Error -> {
                val errorMessage = (similarMediaState).errorMessage
                ErrorMessage(Modifier, errorMessage)

            }
            is SimilarMediaUiState.Empty -> {
                val message = (similarMediaState ).message
                Text(text = message)
            }
        }
    }
}
