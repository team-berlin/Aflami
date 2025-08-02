package com.berlin.aflami.viewmodel.searchcountry

sealed class SearchByCountryScreenEffect {
    object NavigatedBack : SearchByCountryScreenEffect()
    data class NavigatedToMovieDetailsScreen(val movieId: Long, val mediaType: String) :
        SearchByCountryScreenEffect()
}