package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.mediadetails.uistate.CompanyProductionUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.EpisodesSeasonUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.EpisodesUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.MediaCastUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.MediaDetailsUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.ReviewUiState
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.entity.Episode
import com.berlin.entity.Actor
import com.berlin.entity.ProductionCompany
import com.berlin.entity.Review
import com.berlin.entity.TvShowDetails

fun MovieDetails.toUiState(
    isFavorite: Boolean = false,
    isOverviewExpanded: Boolean = false,
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
    mediaType = MediaType.MOVIE,
    hasVideo = hasVideo ?: false,
    originalCountry = originCountry,
    duration = duration
)

fun TvShowDetails.toUiState(
    isFavorite: Boolean = false,
    isOverviewExpanded: Boolean = false,
) = MediaDetailsUiState(
    id = id,
    title = title,
    overview = overview ?: "",
    posterUrl = posterUrl,
    backdropUrl = backdropUrl,
    genres = genres.map { it.name },
    releaseYear = releaseDate ?: "",
    rating = rating,
    runtime = "",
    numberOfSeasons = numberOfSeasons,
    isFavorite = isFavorite,
    isOverviewExpanded = isOverviewExpanded,
    mediaType = MediaType.TVSHOW,
    originalCountry = originCountry,
)

fun ProductionCompany.toUiState() = CompanyProductionUiState(
    id = id.toString(),
    image = poster,
    name = name,
    country = originCountry ?: ""
)

fun Episode.toUiState(): EpisodesUiState {
    return EpisodesUiState(
        airDate = this.airDate ?: "",
        episodeNumber = this.episodeNumber ?: 0,
        episodeType = this.episodeType ?: "",
        id = this.episodeId ?: 0,
        name = this.name ?: "",
        overview = this.description ?: "",
        runtime =
            this.duration ?: "",
        voteAverage = this.rating ?: 0.0,
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

fun Review.toUiState(): ReviewUiState {
    return ReviewUiState(
        id = id,
        name = name,
        userName = userName,
        avatarImage = avatarImage,
        rating = rating,
        content = content,
        date = date.substringBefore("T")
    )
}

fun Actor.toUiState(): MediaCastUiState {
    return MediaCastUiState(
        name = name,
        poster = poster
    )
}
