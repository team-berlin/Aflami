package com.berlin.aflami.screens.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

@Composable
fun FindByActorCard(modifier: Modifier) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            modifier = Modifier.size(width = 76.dp, height = 80.dp),
            painter = painterResource(R.drawable.find_by_actor),
            contentDescription = ""
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(R.string.find_by_actor),
            style = Theme.textStyle.title.medium,
            color = Theme.color.textColors.title
        )
        Text(
            text = stringResource(R.string.find_by_actor_quotation),
            style = Theme.textStyle.title.small,
            color = Theme.color.textColors.body,
            textAlign = TextAlign.Center
        )
    }
}
