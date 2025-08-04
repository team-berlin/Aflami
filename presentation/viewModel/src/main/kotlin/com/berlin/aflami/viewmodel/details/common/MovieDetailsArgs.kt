package com.berlin.aflami.viewmodel.details.common

import androidx.lifecycle.SavedStateHandle
import com.berlin.aflami.viewmodel.util.MOVIE_ID
import javax.inject.Inject

class MovieDetailsArgs @Inject constructor(
    savedStateHandle: SavedStateHandle,
) {
    val movieId: Long? = savedStateHandle.get<Long>(MOVIE_ID)
}