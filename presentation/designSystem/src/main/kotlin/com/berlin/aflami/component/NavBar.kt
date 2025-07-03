package com.berlin.aflami.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

data class BottomNavItem(
    val icon: Painter,
    val route: String,
    @StringRes val labelRes: Int
)

@Composable
fun NavBar(
    navDestinations: List<BottomNavItem>,
    currentRoute: String,
    modifier: Modifier = Modifier,
    onNavDestinationClicked: (String) -> Unit,
    backgroundColor: Color = Theme.color.surface,
    inactiveContentColor: Color = Theme.color.textColors.hint,
    indicatorColor: Color = Theme.color.primaryVariant,
    selectedContentColor: Color = Theme.color.primary,
    selectedLabel: Color = Theme.color.textColors.body,
    indicatorWidth: Dp = 56.dp,
    indicatorHeight: Dp = 32.dp,
    indicatorCornerRadius: Dp = 16.dp,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        navDestinations.forEach { item ->
            val isSelected = currentRoute == item.route
            val backgroundColor = if (isSelected) indicatorColor else Color.Unspecified
            val iconTint = if (isSelected) selectedContentColor else inactiveContentColor
            val labelColor = if (isSelected) selectedLabel else inactiveContentColor
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        enabled = true,
                        onClick = { onNavDestinationClicked(item.route) }
                    )
                    .padding(vertical = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(
                            width = indicatorWidth,
                            height = indicatorHeight
                        )
                        .background(
                            color = backgroundColor,
                            shape = RoundedCornerShape(indicatorCornerRadius)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = item.icon,
                        contentDescription = stringResource(id = item.labelRes),
                        tint = iconTint,
                        modifier = Modifier.size(24.dp)
                    )
                }


                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    text = stringResource(id = item.labelRes),
                    fontSize = 12.sp,
                    color = labelColor,
                    textAlign = TextAlign.Center,
                    style = Theme.textStyle.label.small
                )
            }
        }
    }
}

@ThemeAndLocalePreviews
@Composable
private fun NavBarPreview() {
    AflamiTheme {
        val items = listOf(
            BottomNavItem(
                icon = painterResource(id = R.drawable.home),
                route = "home",
                labelRes = R.string.label_home
            ),
            BottomNavItem(
                icon = painterResource(id = R.drawable.lists),
                route = "lists",
                labelRes = R.string.label_lists
            ),
            BottomNavItem(
                icon = painterResource(id = R.drawable.categories),
                route = "categories",
                labelRes = R.string.label_categories
            ),
            BottomNavItem(
                icon = painterResource(id = R.drawable.play),
                route = "lets_play",
                labelRes = R.string.label_lets_play
            ),
            BottomNavItem(
                icon = painterResource(id = R.drawable.profile),
                route = "profile",
                labelRes = R.string.label_profile
            )
        )

        val selectedRoute = remember { mutableStateOf("home") }

        NavBar(
            navDestinations = items,
            currentRoute = selectedRoute.value,
            onNavDestinationClicked = { selectedRoute.value = it }
        )
    }
}
