package com.berlin.aflami.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.berlin.designsystem.R

enum class BottomNavItems(
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
    val route:NavBar
) {
    HOME(
        icon = R.drawable.home,
        label = R.string.label_home,
        route = NavBar.HomeScreen
    ),
    LISTS(
        icon = R.drawable.lists,
        label = R.string.label_lists,
        route = NavBar.ListScreen
    ),
    CATEGORIES(
        icon = R.drawable.categories,
        label = R.string.label_categories,
        route = NavBar.CategoriesScreen
    ),
    GAMES(
        icon = R.drawable.letsplay,
        label = R.string.label_lets_play,
        route = NavBar.GamesScreen
    ),
    PROFILE(
        icon = R.drawable.profile,
        label = R.string.label_profile,
        route = NavBar.ProfileScreen
    )
}



