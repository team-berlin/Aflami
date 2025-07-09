package com.berlin.aflami.screens.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

@Composable
fun WorldTourHeader(
    modifier: Modifier=Modifier
){

    Row (
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Theme.color.surfaceHigh)
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_left),
                contentDescription = "arrow back from world tour search",
                tint = Theme.color.textColors.title
            )
        }
        Text(
            text = stringResource(R.string.world_tour),
            style = Theme.textStyle.title.large,
            color = Theme.color.textColors.title,
            modifier = Modifier.weight(1f)
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun WorldTourHeaderPreview() {
    WorldTourHeader()

}