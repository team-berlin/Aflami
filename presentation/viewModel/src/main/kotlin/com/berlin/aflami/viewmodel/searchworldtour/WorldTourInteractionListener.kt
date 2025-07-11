package com.berlin.aflami.viewmodel.searchworldtour

interface WorldTourInteractionListener {
    fun onBackClick()
    fun onCountryNameChanged(countryName: CharSequence)
    fun onCountrySelected()
    fun onMovieClick(id: Int)
    fun onDismissDropDown()
    fun onCountrySelected(countryName: String)
}