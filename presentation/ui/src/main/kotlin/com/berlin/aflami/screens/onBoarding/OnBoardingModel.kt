package com.berlin.aflami.screens.onBoarding

import com.berlin.ui.R


data class OnBoardingModel(
    val image: Int,
    val title: Int,
    val description: Int
)
val onBoardingList = listOf(
    OnBoardingModel(
        image = R.drawable.onboarding_page1,
        title = R.string.movies_that_feel_you,
        description = R.string.page1_descerption
    ), OnBoardingModel(
        image = R.drawable.onboarding_page2,
        title = R.string.build_your_watchlist_show_love,
        description = R.string.page2_description
    ), OnBoardingModel(
        image = R.drawable.onboarding_page3,
        title = R.string.your_movie_journal,
        description = R.string.page3_description
    ), OnBoardingModel(
        image = R.drawable.onboarding_page4,
        title = R.string.guess_play,
        description = R.string.page4_description
    )
)