package com.berlin.aflami.screens.categories

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController


@Composable
fun CategoriesScreen(navController: NavController){
    CategoriesContent()

}

@Composable
fun CategoriesContent(){

    Text(text = "Categories Screen")
}