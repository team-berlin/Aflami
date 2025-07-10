package com.berlin.aflami.component

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berlin.aflami.animation.animatedConditionalColor
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

data class TabBarItem(
    val text: String,
    val isSelected: Boolean
)

@Composable
fun TabBar(
    items: List<TabBarItem>,
    onTabChange: (TabBarItem) -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Theme.color.surfaceHigh
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val borderColor = Theme.color.stroke

    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier =
            modifier
                .fillMaxWidth()
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    drawLine(
                        color = borderColor,
                        start = Offset(
                            0f,
                            size.height - strokeWidth / 2
                        ),
                        end = Offset(
                            size.width,
                            size.height - strokeWidth / 2
                        ),
                        strokeWidth = strokeWidth,
                    )
                },
        indicator = @Composable { tabPositions ->
            val currentTabPosition = tabPositions[selectedTabIndex]
            Box(
                modifier =
                    Modifier
                        .tabIndicatorOffset(currentTabPosition)
                        .height(4.dp)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
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
                    selectedTabIndex = index
                    onTabChange(status)
                },
            ) {
                Text(
                    text = status.text,
                    style = titleStyle,
                    color = titleColor,
                    modifier =
                        Modifier
                            .padding(vertical = 16.dp)
                            .animateContentSize(),
                )
            }
        }
    }
}


@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF0D090B
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
                    text = stringResource(R.string.movies),
                    isSelected = true
                ),
                TabBarItem(
                    text = stringResource(R.string.tv_shows),
                    isSelected = false
                )
            ),
            onTabChange = {},

            )
    }
}
