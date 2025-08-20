package com.berlin.aflami.viewmodel.home

import androidx.lifecycle.SavedStateHandle
import com.berlin.aflami.viewmodel.util.LOGIN_STATUS
import javax.inject.Inject

class HomeArgs @Inject constructor(
    savedStateHandle: SavedStateHandle,
) {
    val isLoggedIn: Boolean? = savedStateHandle.get<Boolean>(LOGIN_STATUS)
}