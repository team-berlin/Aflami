package com.berlin.repository.datasource.remote.dto.movie

import com.berlin.repository.datasource.remote.dto.CollectionDto
import com.berlin.repository.datasource.remote.dto.GenreDto
import com.berlin.repository.datasource.remote.dto.ProductionCompanyDto
import com.berlin.repository.datasource.remote.dto.SpokenLanguageDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDto(
    @SerialName("adult")
    val adult: Boolean? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("belongs_to_collection")
    val belongsToCollectionDto: CollectionDto? = null,
    @SerialName("budget")
    val budget: Int? = null,
    @SerialName("genre_ids")
    val genresId: List<Int>? = null,
    @SerialName("genres")
    val genres: List<GenreDto>? = null,
    @SerialName("homepage")
    val homepage: String? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("imdb_id")
    val imdbId: String? = null,
    @SerialName("origin_country")
    val originCountry: List<String>? = null,
    @SerialName("original_language")
    val originalLanguage: String? = null,
    @SerialName("original_title")
    val originalTitle: String? = null,
    @SerialName("overview")
    val overview: String? = null,
    @SerialName("popularity")
    val popularity: Double? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompanyDto>? = null,
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("revenue")
    val revenue: Long? = null,
    @SerialName("runtime")
    val runtime: Int? = null,
    @SerialName("spoken_languages")
    val spokenLanguageDtos: List<SpokenLanguageDto>? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("tagline")
    val tagline: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("video")
    val video: Boolean? = null,
    @SerialName("vote_average")
    val voteAverage: Double? = null,
    @SerialName("vote_count")
    val voteCount: Int? = null,
    @SerialName("media_type")
    val mediaType: String? = null,
)