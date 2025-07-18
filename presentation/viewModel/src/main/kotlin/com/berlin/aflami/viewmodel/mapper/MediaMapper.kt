package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.mediadetails.CompanyProductionItem
import com.berlin.aflami.viewmodel.uistate.EpisodesSeasonUiState
import com.berlin.aflami.viewmodel.uistate.EpisodesUiState
import com.berlin.aflami.viewmodel.uistate.MediaType
import com.berlin.aflami.viewmodel.uistate.MediaDetailsUiState
import com.berlin.aflami.viewmodel.uistate.MovieUIState
import com.berlin.entity.Episodes
import com.berlin.entity.EpisodesSeason
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
    numberOfSeasons = null,
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
    runtime = "",
    numberOfSeasons=numberOfSeasons,
    isFavorite = isFavorite,
    isOverviewExpanded = isOverviewExpanded,
    mediaType = MediaType.TV_SHOW,
)


fun ProductionCompanyEntity.toUiState() = CompanyProductionItem(
    id = id.toString(),
    image = poster,
    name = name,
    country = originCountry ?: ""
)

fun Episodes.toUiState(): EpisodesUiState {
    return EpisodesUiState(
        airDate = this.airDate ?: "",
        episodeNumber = this.episodeNumber ?: 0,
        episodeType = this.episodeType ?: "",
        id = this.id ?: 0,
        name = this.name ?: "",
        overview = this.overview ?: "",
        runtime = this.runtime ?: 0,
        voteAverage = this.voteAverage ?: 0.0,
        stillPath = this.stillPath ?: ""
    )
}

fun EpisodesSeason.toUiState(): EpisodesSeasonUiState {
    return EpisodesSeasonUiState(
        idSeason = this.idSeason ?: 0,
        name = this.name ?: "",
        episodes = this.episodes?.map { episode -> episode?.toUiState() } ?: emptyList(),
        seasonNumber = this.seasonNumber ?: 0,
        posterPath = this.posterPath ?: ""
    )
}

