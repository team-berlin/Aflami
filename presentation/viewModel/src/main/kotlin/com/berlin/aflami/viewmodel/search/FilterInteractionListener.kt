package com.berlin.aflami.viewmodel.search

interface FilterInteractionListener {
    fun onCancelButtonClicked()
    fun onRatingStarChanged(ratingIndex: Float)
    fun onFilterGenreChanged(genreId: Int)
    fun onApplyButtonClicked()
    fun onClearButtonClicked()
}