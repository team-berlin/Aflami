package com.berlin.aflami.viewmodel.searchcountry

import androidx.compose.ui.text.input.TextFieldValue

interface SearchByCountryInteractionListener {
    fun onCountryNameChanged(countryName: TextFieldValue)
    fun onCountryClicked()
    fun onDismissDropDown()
    fun onBackClicked()
    fun onMovieClicked(movieId: Long)
}