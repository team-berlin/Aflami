package com.berlin.aflami.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.color.ExtraColors.blueLinearGradient
import com.berlin.aflami.ui.color.ExtraColors.darkPurpleLinearGradient
import com.berlin.designsystem.R

@Composable
fun SearchSuggestionHub(
    modifier: Modifier = Modifier,
    onSearchByCountryClick: () -> Unit,
    onSearchByActorClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SearchSuggestionItem(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            title = stringResource(R.string.world_tour),
            subtitle = stringResource(R.string.explore_world_cinema),
            contentDescription = stringResource(R.string.icon_cd),
            gradientBackground = darkPurpleLinearGradient,
            painter = painterResource(R.drawable.news_img),
            onClick = onSearchByCountryClick
        )

        SearchSuggestionItem(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            title = stringResource(R.string.find_by_actor),
            subtitle = stringResource(R.string.search_by_favorite_actor),
            contentDescription = stringResource(R.string.icon_cd),
            gradientBackground = blueLinearGradient,
            painter = painterResource(R.drawable.find_by_actor),
            onClick = onSearchByActorClick
        )
    }
}

@ThemeAndLocalePreviews
@Composable
private fun SearchSuggestionHubPreview() {
    SearchSuggestionHub(Modifier, {}, {})
}