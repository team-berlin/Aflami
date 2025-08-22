package com.berlin.aflami.component

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berlin.aflami.animation.animatedConditionalColor
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme

data class TabBarItem(
    val text: String,
    val isSelected: Boolean
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

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(containerColor)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            items.forEachIndexed { index, item ->
                val isSelected = selectedTabIndex == index
                val titleColor = animatedConditionalColor(
                    isActive = isSelected,
                    activeColor = Theme.color.textColors.title,
                    inactiveColor = Theme.color.textColors.hint
                )
                val titleStyle =
                    if (isSelected) Theme.textStyle.title.medium else Theme.textStyle.title.small

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { if (!isSelected) onTabChange(index) }
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.text,
                        style = titleStyle,
                        color = titleColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.animateContentSize()
                    )
                }
            }
        }

        Column {

            BoxWithConstraints {
                val tabWidth = maxWidth / items.size
                val indicatorOffset by animateDpAsState(
                    targetValue = tabWidth * selectedTabIndex,
                    label = "indicator"
                )

                Box(
                    modifier = Modifier
                        .offset(x = indicatorOffset)
                        .width(tabWidth)
                        .height(4.dp)
                        .padding(horizontal = 24.dp)
                        .align(Alignment.BottomStart)
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .background(Theme.color.secondary)
                )
            }
            HorizontalDivider(thickness = 1.dp, color = borderColor)

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
private fun CustomTabBarPreview() {
    var selectedIndex by remember { mutableStateOf(0) }

    AflamiTheme {
        TabBar(
            items = listOf(
                TabBarItem(text = "الأفلام", isSelected = selectedIndex == 0),
                TabBarItem(text = "البرامج التلفزيونية", isSelected = selectedIndex == 1),
            ),
            selectedTabIndex = selectedIndex,
            onTabChange = { selectedIndex = it }
        )
    }
}

