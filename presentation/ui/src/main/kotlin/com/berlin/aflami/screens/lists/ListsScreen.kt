package com.berlin.aflami.screens.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.berlin.aflami.ui.theme.Theme


@Composable
fun ListsScreen(){

    ListsContent()
}

@Composable
@Preview(showSystemUi = true)
fun ListsContent(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
    )
    { Text(text = "Lists Screen") }
}