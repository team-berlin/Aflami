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
    galleryImages: List<String> = emptyList(),
    hasVideo: Boolean =false,
): TVShow {
    return TVShow(
        id = this.id?.toLong() ?: 0L,
        title = this.name ?: "",
        rating = this.voteAverage ?: 0.0,
        posterURL = "$POSTER_PREFIX${this.posterPath.orEmpty()}",
        releaseDate = this.firstAirDate ?: "",
        genres = this.genres?.map{it.toDomain() }?: genresId?.map { it.toDomainGenre() }?:emptyList(),
        screenShot = this.posterPath ?: "",
        description = this.overview ?: "Description not available",
        duration = this.episodeRunTime?.firstOrNull() ?: 0,
        hasVideo = hasVideo,
        companyProductions = this.productionCompanies?.map {
            it.toDomain()
        } ?: emptyList(),
        originCountry = this.originCountry?.firstOrNull() ?: "",
        galleryUrl = galleryImages,
        numberOfSeasons = this.numberOfSeasons?:0,
    )
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
        companyProductions = this.productionCompanies,
        originCountry = this.originCountry,
        galleryUrl = this.galleryUrl,
        numberOfSeasons = this.seasons.size
    )

}


fun RecentlyWatchedTvShowEntity.toDomain(): TVShow {
    return TVShow(
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
        companyProductions = emptyList(),
        originCountry = this.originCountry,
        galleryUrl = this.galleryUrl,
        numberOfSeasons = seasons.size,
    )
}
fun TVShow.toLocalEntity(): RecentlyWatchedTvShowEntity {
    return RecentlyWatchedTvShowEntity(
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
        seasons = emptyList(),
        reviews = emptyList(),

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

fun EpisodeDto.toDomain(): Episode {
    return Episode(
        airDate = this.airDate ?: "",
        episodeNumber = this.episodeNumber ?: 0,
        episodeType = this.episodeType ?: "",
        episodeId = this.id?.toLong() ?: 0L,
        name = this.name ?: "",
        description = this.overview ?: "",
        duration = this.runtime ?: 0,
        tvShowId = this.showId ?: 0,
        rating = this.voteAverage ?: 0.0,
        stillPath = this.stillPath?:""
    )
}

