package com.berlin.aflami.viewmodel.mapper

import com.berlin.aflami.viewmodel.mediadetails.details.common.CompanyProductionUiState
import com.berlin.aflami.viewmodel.mediadetails.details.common.ReviewUiState
import com.berlin.aflami.viewmodel.mediadetails.details.series.EpisodeUiState
import com.berlin.aflami.viewmodel.shareduistate.ActorUiState
import com.berlin.entity.Actor
import com.berlin.entity.CompanyProduction
import com.berlin.entity.Episode
import com.berlin.entity.Review

fun CompanyProduction.toCompanyProductionUiState() = CompanyProductionUiState(
    id = id.toString(),
    image = posterURL,
    name = name,
    country = originCountry
)

fun Episode.toEpisodeUiState(): EpisodeUiState {
    return EpisodeUiState(
        id = this.episodeId,
        airDate = this.airDate,
        episodeNumber = this.episodeNumber,
        episodeType = this.episodeType,
        name = this.name,
        overview = this.description,
        runtime = this.duration.toString(),
        voteAverage = this.rating,
        stillPath = this.stillPath
    )
}


fun Review.toReviewUiState(): ReviewUiState {
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

fun Actor.toActorUiState(): ActorUiState {
    return ActorUiState(
        name = name,
        poster = posterURL
    )
}