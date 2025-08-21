package com.berlin.aflami.screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.component.CategoryCard
import com.berlin.ui.R

@Composable
fun WatchHistoryRatingSection(
    modifier: Modifier = Modifier,
    onWatchHistoryClick: () -> Unit,
    onMyRatingClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CategoryCard(
            title = stringResource(R.string.watch_history),
            image = painterResource(R.drawable.watch_history),
            modifier = Modifier.weight(1f)
        ){
            onWatchHistoryClick()

        }
        CategoryCard (
            title = stringResource(R.string.my_rating),
            image = painterResource(R.drawable.my_rating),
            modifier = Modifier.weight(1f)
        ){
            onMyRatingClick()
        }
    }
}