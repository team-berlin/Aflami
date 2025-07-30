package com.berlin.aflami.viewmodel.searchactor

import androidx.compose.ui.text.input.TextFieldValue
import com.berlin.aflami.viewmodel.shareduistate.MediaType


interface SearchByActorInteractionListener {
    fun onMovieClicked(movieId: Long, mediaType: MediaType)
    fun onActorNameChanged(actorName: TextFieldValue)
    fun onBackClicked()
}