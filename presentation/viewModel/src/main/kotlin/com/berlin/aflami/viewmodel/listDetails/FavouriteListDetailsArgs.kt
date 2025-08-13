package com.berlin.aflami.viewmodel.listDetails

import androidx.lifecycle.SavedStateHandle
import com.berlin.aflami.viewmodel.util.FAVOURITE_LIST_ID
import com.berlin.aflami.viewmodel.util.FAVOURITE_LIST_TITLE
import javax.inject.Inject

class FavouriteListDetailsArgs @Inject constructor(
    savedStateHandle: SavedStateHandle,
) {
    val favouriteListId = savedStateHandle.get<Int>(FAVOURITE_LIST_ID)
    val favouriteListTitle = savedStateHandle.get<String>(FAVOURITE_LIST_TITLE)
}