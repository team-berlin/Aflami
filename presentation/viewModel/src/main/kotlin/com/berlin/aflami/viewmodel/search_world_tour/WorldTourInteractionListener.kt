package com.berlin.aflami.viewmodel.search_world_tour

interface WorldTourInteractionListener {
    fun onBackClick()
    fun onCountryNameChanged(countryName: CharSequence)
    fun onSearchClick()
    fun onClickMovie(id: Int)
}