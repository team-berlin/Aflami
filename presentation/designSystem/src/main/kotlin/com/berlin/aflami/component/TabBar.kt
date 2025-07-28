package com.berlin.aflami.component

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berlin.aflami.animation.animatedConditionalColor
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

data class TabBarItem(
    val text: String, val isSelected: Boolean
)

@Composable
fun TabBar(
    items: List<TabBarItem>,
    onTabChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Theme.color.surfaceHigh,
    selectedTabIndex: Int = 0
) {
    val borderColor = Theme.color.stroke

    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier.fillMaxWidth(),
        divider = {
            HorizontalDivider(thickness = 1.dp, color = borderColor)
        },
        indicator = @Composable { tabPositions ->
            val currentTabPosition = tabPositions[selectedTabIndex]
            Box(
                modifier = Modifier
                    .tabIndicatorOffset(currentTabPosition)
                    .height(5.dp)
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(Theme.color.secondary),
            )
        },
        containerColor = containerColor,
    ) {
        items.forEachIndexed { index, status ->
            val isSelected = selectedTabIndex == index
            val titleColor = animatedConditionalColor(
                isActive = isSelected,
                activeColor = Theme.color.textColors.title,
                inactiveColor = Theme.color.textColors.hint
            )
            val titleStyle =
                if (isSelected) Theme.textStyle.title.medium else Theme.textStyle.title.small
            Tab(
                selected = isSelected,
                onClick = {
                    onTabChange(index)
                },
            ) {
                Text(
                    text = status.text,
                    style = titleStyle,
                    color = titleColor,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .animateContentSize(),
                )
            }
        }
    }
}


@Preview(
    showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, backgroundColor = 0xFF0D090B
)
@Preview(
    showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun TabBarPreview() {
    AflamiTheme {
        TabBar(
            items = listOf(
                TabBarItem(
                    text = stringResource(R.string.movies), isSelected = true
                ), TabBarItem(
                    text = stringResource(R.string.tv_shows), isSelected = false
                )
            ),
            onTabChange = {},

            )
    }
}
