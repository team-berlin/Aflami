package com.berlin.repository.datasource.local.model

import androidx.room.Entity
import com.berlin.entity.MediaTypeEntity
import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import kotlinx.datetime.LocalDate

@Entity(
    tableName = "search_caching",
    primaryKeys = ["query", "id"]
)
data class SearchCaching(
    val query: String,
    val type: String,
    val time: Long,
    val id: Long,
    val title: String,
    val rating: Double,
    val releaseYear: String,
    val description: String,
    //val genre: List<Int>,
    val poster: String,
    val mediaType: String,
)

fun SearchCaching.toDomain(): MediaTypeEntity {
    return MediaTypeEntity(
        id = id,
        title = title,
        mediaType = type,
        rating = rating,
        releaseYear = LocalDate.parse(releaseYear),
        genre = emptyList(),
        poster = poster
    )
}

fun MediaTypeEntity.toLocal(query: String, type: String): SearchCaching {
    return SearchCaching(
        query = poster,
        type = type,
        mediaType = mediaType,
        time = System.currentTimeMillis(),
        id = id,
        title = title,
        rating = rating,
        releaseYear = releaseYear.toString(),
        description = "",
        // genre = emptyList(),
        poster = poster
    )
}

fun Movie.toLocal(query: String): SearchCaching {
    return SearchCaching(
        query = query,
        type = "movie",
        time = System.currentTimeMillis(),
        id = id,
        title = title,
        rating = rating,
        releaseYear = releaseYear.toString(),
        description = "",
        // genre = emptyList(),
        poster = poster,
        mediaType = "movie"
    )
}

fun TVShow.toLocal(query: String): SearchCaching {
    return SearchCaching(
        query = query,
        type = "tv",
        time = System.currentTimeMillis(),
        id = id,
        title = title,
        rating = rating,
        releaseYear = releaseYear.toString(),
        description = "",
        //  genre = emptyList(),
        poster = poster,
        mediaType = "tv"
    )
}