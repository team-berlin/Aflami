package com.berlin.aflami.screens.games

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.berlin.aflami.ui.theme.Theme


@Composable
fun GamesScreen(){
    GamesContent()
}

@Composable
fun GamesContent(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
    ){
        Text(text = "Games Screen")
    }
}