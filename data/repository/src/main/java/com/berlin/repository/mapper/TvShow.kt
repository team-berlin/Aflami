package com.berlin.repository.mapper

import com.berlin.entity.Episode
import com.berlin.entity.Review
import com.berlin.entity.Season
import com.berlin.entity.TVShow
import com.berlin.repository.datasource.local.dto.RecentlyWatchedTvShowEntity
import com.berlin.repository.datasource.local.dto.TVShowEntity
import com.berlin.repository.datasource.remote.dto.SeasonDto
import com.berlin.repository.datasource.remote.dto.TVShowDetailsDto
import com.berlin.repository.datasource.remote.dto.details.EpisodeDto
import com.berlin.repository.datasource.remote.dto.details.SeasonEpisodesDto


fun TVShowDetailsDto.toDomain(
    reviews: List<Review>,
    galleryImages: List<String> = emptyList(),
    episodes: List<Episode>,
    hasVideo: Boolean,
): TVShow {
    return TVShow(
        id = this.id?.toLong() ?: 0L,
        title = this.name ?: "",
        rating = this.voteAverage ?: 0.0,
        posterURL = this.posterPath ?: "",
        releaseDate = this.firstAirDate ?: "",
        genres = this.genres?.map { it.toDomain() } ?: emptyList(),
        screenShot = this.posterPath ?: "",
        description = this.overview ?: "Description not available",
        duration = this.episodeRunTime?.firstOrNull() ?: 0,
        hasVideo = hasVideo,
        productionCompanies = this.productionCompanies?.map {
            it.toDomain()
        } ?: emptyList(),
        originCountry = this.originCountry?.firstOrNull() ?: "",
        seasons = this.seasonDtos?.map { it.toDomain(episodes) } ?: emptyList(),
        galleryUrl = galleryImages,
        reviews = reviews)
}

fun TVShow.toLocal(): TVShowEntity {
    return TVShowEntity(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseDate = this.releaseDate,
        genres = emptyList(),
        posterURL = this.posterURL,
        screenShot = this.screenShot,
        description = this.description,
        duration = this.duration,
        hasVideo = this.hasVideo,
        productionCompanies = emptyList(),
        originCountry = this.originCountry,
        galleryUrl = this.galleryUrl,
        reviews = emptyList(),
        seasons = emptyList()
    )
}
fun TVShowEntity.toDomain(): TVShow{
    return TVShow(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseDate = this.releaseDate,
        genres = this.genres,
        posterURL = this.posterURL,
        screenShot = this.screenShot,
        description = this.description,
        duration = this.duration,
        hasVideo = this.hasVideo,
        productionCompanies = this.productionCompanies,
        originCountry = this.originCountry,
        galleryUrl = this.galleryUrl,
        seasons = emptyList(),
        reviews = emptyList()
    )

}


fun RecentlyWatchedTvShowEntity.toDomain(): TVShow {
    return TVShow(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseDate = this.releaseDate,
        genres = this.genres,
        posterURL = this.posterURL,
        screenShot = this.screenShot,
        description = this.description,
        duration = this.duration,
        hasVideo = this.hasVideo,
        productionCompanies = this.productionCompanies,
        originCountry = this.originCountry,
        galleryUrl = this.galleryUrl,
        seasons = this.seasons,
        reviews = this.reviews,
    )
}
fun TVShow.toLocalEntity(): RecentlyWatchedTvShowEntity {
    return RecentlyWatchedTvShowEntity(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseDate = this.releaseDate,
        genres = this.genres,
        posterURL = this.posterURL,
        screenShot = this.screenShot,
        description = this.description,
        duration = this.duration,
        hasVideo = this.hasVideo,
        productionCompanies = this.productionCompanies,
        originCountry = this.originCountry,
        galleryUrl = this.galleryUrl,
        seasons = this.seasons,
        reviews = this.reviews,
    )
}



fun SeasonDto.toDomain(
    episodes: List<Episode>
) = Season(
    id = this.id?.toLong() ?: 0L,
    episodeCount = this.episodeCount ?: 0,
    episodes = episodes,
    name = this.name ?: "",
    description = this.overview ?: "",
    posterURL = this.posterPath ?: "",
    seasonNumber = this.seasonNumber ?: 0
)
val t= SeasonEpisodesDto
fun EpisodeDto.toDomain(): Episode {
    return Episode(
        id = this.id?.toLong() ?: 0L,
        name = this.name ?: "",
        overview = this.overview ?: "",
        airDate = this.airDate ?: "",
        episodeNumber = this.episodeNumber ?: 0,
        seasonNumber = this.seasonNumber ?: 0,
        stillPath = this.stillPath ?: "",
        voteAverage = this.voteAverage ?: 0.0
    )
}

