package com.berlin.aflami.screens.home

import androidx.compose.runtime.Composable
import com.berlin.aflami.viewmodel.home.HomeScreenEffect
import com.berlin.aflami.viewmodel.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel= koinViewModel(),
    onEffect: (HomeScreenEffect) -> Unit
){

}


@Composable
fun HomeContent(){

}