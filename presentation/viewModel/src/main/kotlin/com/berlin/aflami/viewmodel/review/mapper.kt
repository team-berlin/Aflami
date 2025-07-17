package com.berlin.aflami.viewmodel.review

import com.berlin.aflami.viewmodel.uistate.ReviewUiState
import com.berlin.entity.Review

fun Review.toUiState(): ReviewUiState {
    return ReviewUiState(
        id = id,
        name = name,
        userName = userName,
        avatarImage = avatarImage,
        rating = rating,
        content = content,
        date = date
    )
}