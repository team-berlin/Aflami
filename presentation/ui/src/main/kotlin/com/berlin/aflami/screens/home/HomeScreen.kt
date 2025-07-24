package com.berlin.aflami.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.berlin.aflami.screens.home.component.ContinueWatchingSection
import com.berlin.aflami.viewmodel.home.HomeInteractionListener
import com.berlin.aflami.viewmodel.home.HomeScreenEffect
import com.berlin.aflami.viewmodel.home.HomeViewModel
import com.berlin.aflami.viewmodel.home.uistate.HomeUiState
import com.berlin.ui.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel= koinViewModel(),
    onEffect:(HomeScreenEffect) -> Unit
){
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getContinueWatching()
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
    listener:HomeInteractionListener
){

    Column (
        modifier = Modifier.padding(top = 6.dp)
    ){
        ContinueWatchingSection(
            onShowAllContinueWatchingClick = {
                listener.onShowAllContinueWatchingClicked()
            },
            state = state.mediaContinueWatching,
            sectionTitleId = R.string.continue_watching
        )
    }





}