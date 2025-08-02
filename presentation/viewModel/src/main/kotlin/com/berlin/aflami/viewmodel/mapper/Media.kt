package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.shareduistate.MediaType

//fun Media.toUIState(): MediaUiState {
//    return MediaUiState(
//        id = id,
//        title = title,
//        rating = rating.toString().take(3),
//        releaseYear = releaseYear.year.toString(),
//        genre = genre,
//        poster = poster,
//        mediaType = MediaType.valueOf(mediaTypeMapper(mediaType.uppercase()))
//    )
//}
fun mediaTypeMapper(mediatype:String):String{
    return when(mediatype){
        "TVSHOW" -> MediaType.TV_SHOW.name
        "MOVIE" -> MediaType.MOVIE.name
        else -> MediaType.MOVIE.name
    }
}