package com.berlin.aflami.viewmodel.listDetails

import androidx.lifecycle.SavedStateHandle
import com.berlin.aflami.viewmodel.util.FAVOURITE_LIST_ID
import javax.inject.Inject

class ListDetailsArgs @Inject constructor(
    savedStateHandle: SavedStateHandle,
) {
    val favouriteListId = savedStateHandle.get<Int>(FAVOURITE_LIST_ID)
}