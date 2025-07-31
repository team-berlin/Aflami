package com.berlin.aflami.screens.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.berlin.aflami.ui.theme.Theme


@Composable
fun ListsScreen(){

    ListsContent()
}

@Composable
fun ListsContent(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
    )
    { Text(text = "Lists Screen") }
}