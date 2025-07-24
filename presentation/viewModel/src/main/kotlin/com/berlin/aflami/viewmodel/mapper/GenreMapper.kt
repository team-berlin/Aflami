//package com.berlin.aflami.viewmodel.mapper
//
//import com.berlin.aflami.viewmodel.search.GenreType
//import com.berlin.aflami.viewmodel.search.GenreUiState
//import com.berlin.aflami.viewmodel.search.Selectable
//
//fun List<GenreUiState>.selectByGenre(genre: GenreType): List<GenreUiState> {
//    return this.map { genreUiState ->
//        genreUiState.copy(
//            genres = Selectable(
//                type = genreUiState.genres.type, isSelected = genreUiState.genres.type == genre
//            )
//        )
//    }
//}