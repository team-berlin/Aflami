package com.berlin.aflami.viewmodel.searchcountry

import androidx.compose.ui.text.input.TextFieldValue

interface SearchByCountryScreenInteractionListener {
    fun onBackClicked()
    fun onCountryNameChanged(countryName: TextFieldValue)
    fun onCountryClicked(countryName:String)
    fun onDismissDropDown()
    fun onMovieClicked(movieId: Long)
}