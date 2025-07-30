package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.repository.datasource.local.dto.ContinueWatchingMovieEntity
import com.berlin.repository.datasource.local.dto.ContinueWatchingTVShowEntity

@Dao
interface ContinueWatchingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addContinueWatchingMovie(movieEntity: ContinueWatchingMovieEntity)

    @Query(
        """SELECT * FROM Movie_Continue_Watching 
        LIMIT :pageSize OFFSET :skip"""
    )
    suspend fun getContinueWatchingMovies(
        pageSize: Int,
        skip: Int
    ): List<ContinueWatchingMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addContinueWatchingTVShow(tvShowEntity: ContinueWatchingTVShowEntity )

    @Query(
        """SELECT * FROM TVShow_Continue_Watching 
        LIMIT :pageSize OFFSET :skip"""
    )    suspend fun getContinueWatchingTVShows(
        pageSize: Int,
        skip: Int
    ): List<ContinueWatchingTVShowEntity>
}