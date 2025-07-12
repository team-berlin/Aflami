package com.berlin.aflami.viewmodel.searchworldtour

interface WorldTourInteractionListener {
    fun onBackClicked()
    fun onCountryNameChanged(countryName: CharSequence)
    fun onCountrySelected()
    fun onMovieClicked(id: Int)
    fun onDismissDropDown()
    fun onCountrySelected(countryName: String)
}