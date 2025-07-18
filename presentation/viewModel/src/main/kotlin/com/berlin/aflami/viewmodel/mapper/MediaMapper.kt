package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.mediadetails.CompanyProductionItem
import com.berlin.aflami.viewmodel.uistate.MediaType
import com.berlin.aflami.viewmodel.uistate.MediaDetailsUiState
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import com.berlin.aflami.viewmodel.uistate.SeasonUiState
import com.berlin.entity.Movie
import com.berlin.entity.MovieDetails
import com.berlin.entity.ProductionCompanyEntity
import com.berlin.entity.SeasonEntity
import com.berlin.entity.TvShowDetails

fun Movie.toUIState(): MovieUIState {
    return MovieUIState(
        id = id,
        title = title,
        rating = rating.toString().take(3),
        releaseYear = releaseYear.year.toString(),
        genre = genre,
        poster = poster
    )
}

fun MovieDetails.toUiState(
    isFavorite: Boolean = false,
    isOverviewExpanded: Boolean = false
) = MediaDetailsUiState(
    id = id,
    title = title,
    overview = overview ?: "",
    posterUrl = posterUrl,
    backdropUrl = backdropUrl,
    genres = genres.map { it.name },
    releaseYear = releaseDate ?: "",
    rating = rating,
    runtime = runtime?.let { "${it / 60}h ${it % 60}m" } ?: "",
    seasons = emptyList(),
    isFavorite = isFavorite,
    isOverviewExpanded = isOverviewExpanded,
    mediaType = MediaType.MOVIE
)

fun TvShowDetails.toUiState(
    isFavorite: Boolean = false,
    isOverviewExpanded: Boolean = false
) = MediaDetailsUiState(
    id = id,
    title = title,
    overview = overview ?: "",
    posterUrl = posterUrl,
    backdropUrl = backdropUrl,
    genres = genres.map { it.name },
    releaseYear = releaseDate?.take(4) ?: "",
    rating = rating,
    runtime = if (seasons.isNotEmpty()) "${seasons.size} seasons" else "",
    seasons = seasons.map { it.toUiState() },
    isFavorite = isFavorite,
    isOverviewExpanded = isOverviewExpanded,
    mediaType = MediaType.TV_SHOW
)

fun SeasonEntity.toUiState() = SeasonUiState(
    id = id,
    seasonTitle = name,
    numberOfEpisodes = episodeCount,
    posterUrl = posterUrl ?: "",
    seasonNumber = seasonNumber,
    airDate = airDate ?: ""
)

fun ProductionCompanyEntity.toUiState() = CompanyProductionItem(
    id = id.toString(),
    image = poster,
    name = name,
    country = originCountry ?: ""
)

