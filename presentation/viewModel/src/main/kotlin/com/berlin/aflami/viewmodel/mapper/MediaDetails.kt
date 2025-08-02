package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.mediadetails.uistate.CompanyProductionUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.EpisodesSeasonUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.EpisodesUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.MediaCastUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.ReviewUiState
import com.berlin.entity.Actor
import com.berlin.entity.Episode
import com.berlin.entity.ProductionCompany
import com.berlin.entity.Review

fun ProductionCompany.toUiState() = CompanyProductionUiState(
    id = id.toString(),
    image = posterURL,
    name = name,
    country = originCountry
)

fun Episode.toUiState(): EpisodesUiState {
    return EpisodesUiState(
        id = this.episodeId,
        airDate = this.airDate,
        episodeNumber = this.episodeNumber,
        episodeType = this.episodeType,
        name = this.name,
        overview = this.description,
        duration = this.duration.toString(),
        rating = this.rating,
        stillPath = this.stillPath
    )
}

fun EpisodesSeason.toUiState(): EpisodesSeasonUiState {
    return EpisodesSeasonUiState(
        idSeason = this.idSeason,
        name = this.name,
        episodes = this.episodes?.map { episode -> episode?.toUiState() } ?: emptyList(),
        seasonNumber = this.seasonNumber,
        posterPath = this.posterPath
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
        poster = posterURL
    )
}
