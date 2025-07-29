package com.berlin.aflami.viewmodel.searchcountry

interface SearchByCountryInteractionListener {
    fun onCountryNameChanged(countryName: CharSequence)
    fun onCountryClicked()
    fun onDismissDropDown()
    fun onBackClicked()
    fun onMovieClicked(movieId: Long)
}