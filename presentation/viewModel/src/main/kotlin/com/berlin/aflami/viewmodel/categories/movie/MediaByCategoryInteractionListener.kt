package com.berlin.aflami.viewmodel.categories.movie

interface MediaByCategoryInteractionListener {
    fun onBackClicked()
    fun onMediaCardClicked(mediaId: Long)
    fun onCategoryCardClicked(category: Long)
}
