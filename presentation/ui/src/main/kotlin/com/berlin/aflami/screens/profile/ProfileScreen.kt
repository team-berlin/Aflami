package com.berlin.aflami.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.berlin.aflami.ui.theme.Theme


@Composable
fun ProfileScreen(navController: NavController){
    ProfileContent()

}

@Composable
fun ProfileContent(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
    )
    { Text(text = "Profile Screen") }
}