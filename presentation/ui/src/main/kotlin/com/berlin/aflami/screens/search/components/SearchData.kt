package com.berlin.aflami.screens.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

@Composable
fun SearchData(
    modifier: Modifier = Modifier,
    recentSearch: List<String>,
    onItemClick: (String) -> Unit,
    onDeleteItem: (String) -> Unit,
    onClearAll: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 12.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(R.string.recent_searches),
            color = Theme.color.textColors.title,
            style = Theme.textStyle.title.medium,
            modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)

        )
        Text(
            stringResource(R.string.clear_all),
            color = Theme.color.primary,
            style = Theme.textStyle.label.medium,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 12.dp, start = 16.dp)
                .clickable { onClearAll() }
        )
    }
    LazyColumn() {
        items(recentSearch) { currentQuery ->
            SearchItem(
                text = currentQuery,
                onTextClick = { onItemClick(currentQuery) },
                onDeleteClick = { onDeleteItem(currentQuery) }
            )
        }
    }
}
