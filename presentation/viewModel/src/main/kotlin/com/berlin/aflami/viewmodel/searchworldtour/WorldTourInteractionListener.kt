package com.berlin.aflami.viewmodel.searchworldtour

interface WorldTourInteractionListener {
    fun onCountryNameChanged(countryName: CharSequence)
    fun onCountrySelected()

    fun onDismissDropDown()
    fun onCountrySelected(countryName: String)
}