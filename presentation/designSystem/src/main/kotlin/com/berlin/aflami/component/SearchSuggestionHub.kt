package com.berlin.aflami.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.color.ExtraColors.blueLinearGradient
import com.berlin.aflami.ui.color.ExtraColors.darkPurpleLinearGradient
import com.berlin.designsystem.R

@Composable
fun SearchSuggestionHub() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SearchSuggestionItem(
            modifier = Modifier.weight(1f),
            title = stringResource(R.string.world_tour),
            subtitle = stringResource(R.string.explore_world_cinema),
            gradientBackground = darkPurpleLinearGradient,
            painter = painterResource(R.drawable.news_img),
            contentDescription = "world tour"
        )
        SearchSuggestionItem(
            modifier = Modifier.weight(1f),
            title = stringResource(R.string.find_by_actor),
            subtitle = stringResource(R.string.search_by_favorite_actor),
            gradientBackground = blueLinearGradient,
            painter = painterResource(R.drawable.find_by_actor),
            contentDescription = "Find by actor"

        )
    }
}


@ThemeAndLocalePreviews
@Composable
private fun SearchSuggestionHubPreview() {
    SearchSuggestionHub()
}