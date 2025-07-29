package com.berlin.aflami.screens.profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController


@Composable
fun ProfileScreen(navController: NavController){
    ProfileContent()

}

@Composable
fun ProfileContent(){

    Text(text = "Profile Screen")
}