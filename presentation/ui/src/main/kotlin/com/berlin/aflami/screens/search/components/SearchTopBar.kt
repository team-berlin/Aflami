package com.berlin.aflami.screens.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.TopBar
import com.berlin.aflami.ui.theme.Theme
import com.berlin.ui.R

@Composable
fun SearchTopBar(
    title: String,
    onBackClick: () -> Unit
) {
    TopBar(
        modifier = Modifier.padding(vertical = 8.dp),
        title = {
            Text(
                text = title,
                style = Theme.textStyle.title.large,
                color = Theme.color.textColors.title,
            )
        },
        leadingIcon = {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Theme.color.surfaceHigh)
                    .clickable { onBackClick() }
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = "arrow back from world tour search",
                    tint = Theme.color.textColors.title
                )
            }
        }
    )
}