package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.repository.datasource.local.dto.HomeSection
import com.berlin.repository.datasource.local.dto.HomeTVShowEntity

@Dao
interface HomeTVShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTVShows(tvShows: List<HomeTVShowEntity>)

    @Query("SELECT * FROM HOME_TV_SHOW WHERE homeSection = :homeSection")
    suspend fun getTVShowsBySection(homeSection: HomeSection): List<HomeTVShowEntity>

    @Query("DELETE FROM HOME_TV_SHOW  WHERE homeSection = :homeSection")
    suspend fun clearHomeScreenTVShows(homeSection: HomeSection)

    @Query("DELETE FROM HOME_TV_SHOW")
    suspend fun clearAllTVShows()
}