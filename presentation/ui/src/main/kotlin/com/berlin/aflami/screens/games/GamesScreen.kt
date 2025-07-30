package com.berlin.aflami.screens.games

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController


@Composable
fun GamesScreen(navController: NavController){
    GamesContent()
}

@Composable
fun GamesContent(){

    Text(text = "Games Screen")
}