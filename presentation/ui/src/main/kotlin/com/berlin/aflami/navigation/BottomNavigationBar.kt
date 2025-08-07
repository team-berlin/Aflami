package com.berlin.aflami.navigation


import android.util.Log
import androidx.annotation.DrawableRes
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

data class BottomNavigationItem(
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
    val route: NavigationBarDestinations,
)
val bottomNavList: List<BottomNavigationItem> = listOf<BottomNavigationItem>(
    BottomNavigationItem(
        icon = R.drawable.home,
        label = R.string.label_home,
        route = NavigationBarDestinations.HomeScreen
    ),
    BottomNavigationItem(
        icon = R.drawable.lists,
        label = R.string.label_lists,
        route = NavigationBarDestinations.ListScreen
    ),
    BottomNavigationItem(
        icon = R.drawable.categories,
        label = R.string.label_categories,
        route = NavigationBarDestinations.CategoriesScreen
    ),
    BottomNavigationItem(
        icon = R.drawable.letsplay,
        label = R.string.label_lets_play,
        route = NavigationBarDestinations.GamesScreen
    ),
    BottomNavigationItem(
        icon = R.drawable.profile,
        label = R.string.label_profile,
        route = NavigationBarDestinations.ProfileScreen
    )
)

@Composable
fun NavBar(
    navDestinations: List<BottomNavigationItem>,
    currentRoute: NavigationBarDestinations,
    onNavDestinationClicked: (NavigationBarDestinations) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Theme.color.surface,
    inactiveContentColor: Color = Theme.color.textColors.hint,
    indicatorColor: Color = Theme.color.primaryVariant,
    selectedContentColor: Color = Theme.color.primary,
    selectedLabelColor: Color = Theme.color.textColors.body,
    indicatorWidth: Dp = 56.dp,
    indicatorHeight: Dp = 32.dp,
    indicatorCornerRadius: Dp = 16.dp,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider(
            thickness = 0.5.dp,
            color = Theme.color.stroke
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            navDestinations.forEach { item ->
                val isSelected = currentRoute == item.route

                val backgroundColor = if (isSelected) indicatorColor else Color.Unspecified
                val iconTint = if (isSelected) selectedContentColor else inactiveContentColor
                val labelColor = if (isSelected) selectedLabelColor else inactiveContentColor
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            enabled = true,
                            onClick = { if (!isSelected) onNavDestinationClicked(item.route) }
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
                            painter = painterResource(item.icon),
                            contentDescription = stringResource(item.label),
                            tint = iconTint,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        text = stringResource(item.label),
                        color = labelColor,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = Theme.textStyle.label.small
                    )

                }
            }
        }
    }
}