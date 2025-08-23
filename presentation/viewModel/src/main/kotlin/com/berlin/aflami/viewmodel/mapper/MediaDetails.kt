package com.berlin.aflami.viewmodel.mapper

import android.icu.text.DecimalFormat
import com.berlin.aflami.viewmodel.details.common.CompanyProductionUiState
import com.berlin.aflami.viewmodel.details.common.ReviewUiState
import com.berlin.aflami.viewmodel.details.series.EpisodeUiState
import com.berlin.aflami.viewmodel.shareduistate.ActorUiState
import com.berlin.entity.Actor
import com.berlin.entity.CompanyProduction
import com.berlin.entity.Episode
import com.berlin.entity.Review
import kotlinx.datetime.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun CompanyProduction.toCompanyProductionUiState() = CompanyProductionUiState(
    id = id.toString(),
    image = posterURL,
    name = name,
    country = originCountry
)



fun Episode.toEpisodeUiState(): EpisodeUiState {
    val formattedDate = try {
        this.airDate.let {
            val parsedDate = LocalDate.parse(it)
            val formatter = DateTimeFormatter.ofPattern("yyyy MMM dd", Locale.US)
            java.time.LocalDate.of(parsedDate.year, parsedDate.monthNumber, parsedDate.dayOfMonth)
                .format(formatter)
        }
    } catch (e: Exception) {
        null
    }

    return EpisodeUiState(
        id = this.episodeId,
        airDate = formattedDate,
        episodeNumber = this.episodeNumber,
        episodeType = this.episodeType,
        name = this.name,
        overview = this.description,
        runtime = this.duration.toString(),
        voteAverage = DecimalFormat("#.#").format(rating).toString(),
        stillPath = this.stillPath,
        trailer = this.trailer
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