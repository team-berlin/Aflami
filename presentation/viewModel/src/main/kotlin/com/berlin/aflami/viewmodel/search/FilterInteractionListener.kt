package com.berlin.aflami.viewmodel.search

interface FilterInteractionListener {
    fun onCancelButtonClicked()
    fun onRatingStarChanged(ratingIndex: Float)
    fun onGenreButtonChanged(genreType: GenreType)
    fun onApplyButtonClicked()
    fun onClearButtonClicked()
}