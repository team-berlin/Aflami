package com.berlin.aflami.screens.mediadetails.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.screens.mediadetails.components.MoviesCastGrid
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.mediadetails.MediaDetailsViewmodel
import com.berlin.aflami.viewmodel.uistate.MediaDetailsScreenUiState
import com.berlin.ui.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun CastScreen(
    navController: NavController,
    viewModel: MediaDetailsViewmodel = koinViewModel()
) {
    val castState by viewModel.uiState.collectAsState()
    CastContent(
        navController = navController,
        castState = castState
    )

}

@Composable
fun CastContent(
    navController: NavController,
    castState: MediaDetailsScreenUiState
) {
    Column {
        TopBar(
            modifier = Modifier.padding(vertical = 8.dp),
            title = {
                Text(
                    text = stringResource(R.string.cast),
                    style = Theme.textStyle.title.large,
                    color = Theme.color.textColors.title
                )
            },
            leadingIcon = {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Theme.color.surfaceHigh)
                        .clickable {
                            navController.popBackStack()
                        }
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_left),
                        contentDescription = stringResource(R.string.arrow_back),
                        tint = Theme.color.textColors.title
                    )
                }
            }
        )
        if (castState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Theme.color.primary,
                )
            }
        } else {
            MoviesCastGrid(
                mediaCast = castState.mediaCast

            )
        }
    }


}