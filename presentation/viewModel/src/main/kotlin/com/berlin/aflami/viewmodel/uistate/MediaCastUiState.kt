package com.berlin.aflami.viewmodel.uistate

data class MediaCastUiState(
    val mediaId:Long =0L,
    val name:String="",
    val poster:String=""
)
data class MediaDetailsUiState(
    val castState:List<MediaCastUiState> = emptyList()
)