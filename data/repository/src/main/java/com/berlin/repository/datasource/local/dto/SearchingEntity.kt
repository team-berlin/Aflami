package com.berlin.repository.datasource.local.dto

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.berlin.entity.Genre
import com.berlin.entity.CompanyProduction
import com.berlin.entity.Review
import com.berlin.entity.Season

@Entity(tableName = "search_cache")
data class SearchingEntity(
    @PrimaryKey val query: String,
    val type: String,
    val timeStamp: Long = System.currentTimeMillis(),
    val queryType: QueryType
)

@Entity(
    tableName = "movie_search_cache", foreignKeys = [ForeignKey(
        entity = SearchingEntity::class,
        parentColumns = ["query"],
        childColumns = ["query"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class MovieEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val rating: Double,
    val releaseDate: String,
    val posterURL: String,
    val screenShot: String,
    val description: String,
    val genres: List<Genre>,
    val duration: Int,
    val hasVideo: Boolean,
    val productionCompanies: List<CompanyProduction>,
    val originCountry: String,
    val galleryUrl:List<String>,
    val reviews: List<Review>,
)

@Entity(
    tableName = "tv_show_search_cache", foreignKeys = [ForeignKey(
        entity = SearchingEntity::class,
        parentColumns = ["query"],
        childColumns = ["query"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class TVShowEntity(
    @PrimaryKey val id: Long,
    val title:String,
    val rating: Double,
    val posterURL:String,
    val releaseDate: String,
    val screenShot: String,
    val description: String,
    val genres: List<Genre>,
    val duration: Int,
    val hasVideo: Boolean,
    val productionCompanies: List<CompanyProduction>,
    val originCountry: String,
    val seasons: List<Season>,
    val galleryUrl:List<String>,
    val reviews: List<Review>,
)

enum class QueryType {
    ACTOR, COUNTRY, TV, MOVIE, HISTORY
}