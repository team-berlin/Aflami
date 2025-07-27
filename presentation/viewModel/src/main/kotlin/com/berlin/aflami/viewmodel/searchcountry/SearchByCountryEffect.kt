package com.berlin.aflami.viewmodel.searchcountry

sealed class SearchByCountryEffect {
    object NavigatedBack : SearchByCountryEffect()
    data class NavigatedToMovieDetailsScreen(val movieId: Int, val mediaType: String): SearchByCountryEffect()
}