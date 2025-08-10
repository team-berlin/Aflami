package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.MediaByCategoryDestination
import com.berlin.aflami.navigation.NavigationBarDestinations.CategoriesScreen
import com.berlin.aflami.screens.categories.CategoryScreen
import com.berlin.aflami.screens.categories.MediaByCategoryScreen

fun NavGraphBuilder.mediaByCategoryRoute()=
    composable<MediaByCategoryDestination>
    {
        MediaByCategoryScreen()
    }

