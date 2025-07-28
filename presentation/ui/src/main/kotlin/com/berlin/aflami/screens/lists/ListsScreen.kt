package com.berlin.aflami.screens.lists

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController


@Composable
fun ListsScreen(navController: NavController){

    ListsContent()
}

@Composable
fun ListsContent(){

    Text(text = "Lists Screen")
}