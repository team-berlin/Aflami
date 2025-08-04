package com.berlin.aflami.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.berlin.aflami.navigation.OnBoardingDestination
import com.berlin.aflami.onboarding.OnBoardingScreen

fun NavGraphBuilder.onBoarding(){
    composable<OnBoardingDestination>{
        OnBoardingScreen()
    }
}