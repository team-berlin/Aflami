package com.berlin.aflami.viewmodel.searchactor

import androidx.compose.ui.text.input.TextFieldValue
import com.berlin.aflami.viewmodel.shareduistate.MediaType

interface SearchByActorInteractionListener {
    fun onBackClicked()
    fun onMediaCardClicked(movieId: Long, mediaType: MediaType)
    fun onActorNameChanged(actorName: TextFieldValue)
}