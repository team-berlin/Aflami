package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.repository.datasource.local.dto.RecentlyWatchedMovieEntity
import com.berlin.repository.datasource.local.dto.RecentlyWatchedTvShowEntity

@Dao
interface ContinueWatchingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addContinueWatchingMovie(movieEntity: RecentlyWatchedMovieEntity)

    @Query(
        """SELECT * FROM Movie_Continue_Watching 
        LIMIT :pageSize OFFSET :skip"""
    )
    suspend fun getContinueWatchingMovies(
        pageSize: Int,
        skip: Int
    ): List<RecentlyWatchedMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addContinueWatchingTVShow(tvShowEntity: RecentlyWatchedTvShowEntity )

    @Query(
        """SELECT * FROM TVShow_Continue_Watching 
        LIMIT :pageSize OFFSET :skip"""
    )    suspend fun getContinueWatchingTVShows(
        pageSize: Int,
        skip: Int
    ): List<RecentlyWatchedTvShowEntity>
}