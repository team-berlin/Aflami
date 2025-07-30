package com.berlin.repository.mapper

import com.berlin.entity.Season
import com.berlin.entity.Episode
import com.berlin.entity.TVShow
import com.berlin.entity.TvShowDetails
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.remote.dto.Season
import com.berlin.repository.datasource.remote.dto.TVShowDetailsDto
import com.berlin.repository.datasource.remote.dto.TVShowDto
import com.berlin.repository.datasource.remote.dto.details.EpisodeDto
import com.berlin.repository.datasource.remote.dto.details.EpisodesSeasonDto
import kotlinx.datetime.toLocalDate
import java.time.Instant

fun SearchingEntity.toTVShow(): TVShow {
    return TVShow(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseYear = this.releaseYear.toLocalDate(),
        genre = this.genre,
        poster = this.poster,
    )
}

fun TVShowDto.toLocal(query: String, type: String, page: Int, mediaType: String): SearchingEntity {
    return SearchingEntity(
        query = query,
        type = type,
        time = Instant.now().epochSecond,
        id = this.id?.toLong() ?: 0L,
        title = this.name ?: "",
        rating = this.voteAverage ?: 0.0,
        releaseYear = (this.firstAirDate ?: "").toLocalDate().toString(),
        genre = this.genreIds?.filterNotNull() ?: emptyList(),
        poster = "$POSTER_PREFIX${this.posterPath.orEmpty()}",
        mediaType = mediaType,
        page = page,
    )
}

fun TVShowDto.toTVShow(): TVShow {
    return TVShow(
        id = this.id?.toLong() ?: 0L,
        title = this.name ?: "",
        rating = this.voteAverage ?: 0.0,
        releaseYear = stringToLocalDate(
            dateString = this.firstAirDate.toString()
        ),
        genre = this.genreIds?.filterNotNull() ?: emptyList(),
        poster = "$POSTER_PREFIX${this.posterPath.orEmpty()}"
    )
}

fun TVShowDetailsDto.toDomain(): TvShowDetails {
    return TvShowDetails(
        id = this.id?.toLong() ?: 0L,
        title = this.name.orEmpty(),
        overview = this.overview.orEmpty(),
        posterUrl = "$POSTER_PREFIX${this.posterPath.orEmpty()}",
        backdropUrl = "$BACKDROP_PREFIX${this.backdropPath.orEmpty()}",
        releaseDate = this.firstAirDate.orEmpty(),
        rating = this.voteAverage ?: 0.0,
        runtime = this.episodeRunTime?.firstOrNull() ?: 0,
        genres = this.genres?.map { it.toEntity() } ?: emptyList(),
        seasons = this.seasons?.map { it.toEntity() } ?: emptyList(),
        originCountry = this.originCountry?.get(0),
        numberOfSeasons = this.numberOfSeasons,
        productionCompanies = this.productionCompanies?.map { company ->
            company.toEntity()
        } ?: emptyList()
    )
}

fun TVShowDto.toDomain(mediaType: String): Media {
    return Media(
        id = this.id?.toLong() ?: 0L,
        title = this.name.orEmpty(),
        rating = this.voteAverage ?: 0.0,
        releaseYear = stringToLocalDate(firstAirDate ?: ""),
        mediaType = mediaType,
        genre = this.genreIds?.filterNotNull() ?: emptyList(),
        poster = "$POSTER_PREFIX${this.posterPath.orEmpty()}"
    )
}

fun Season.toEntity() = com.berlin.entity.Season(
    airDate = airDate,
    episodeCount = episodeCount,
    id = id,
    name = name,
    overview = overview,
    posterUrl = posterPath,
    seasonNumber = seasonNumber,
    voteAverage = voteAverage
)

fun EpisodesSeasonDto.toDomain(): EpisodesSeason {
    return EpisodesSeason(
        idSeason = this.id_Season,
        name = this.name,
        episodes = this.episodes?.filterNotNull()?.map { it.toEpisode() },
        seasonNumber = this.seasonNumber,
        posterPath = this.posterPath?.let { POSTER_PREFIX + it }
    )
}
fun EpisodeDto.toEpisode(): Episode {
    return Episode(
        stillPath = this.stillPath,
        airDate = this.airDate,
        episodeNumber = this.episodeNumber,
        episodeType = this.episodeType,
        episodeId = this.id,
        name = this.name,
        description = this.overview,
        duration = this.runtime.formatRuntime(),
        tvShowId = this.showId,
        rating = this.voteAverage
    )
}