package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.berlin.repository.datasource.local.dto.UserPointsEntity

@Dao
interface UserPointsDao {

    @Query("SELECT points FROM user_points WHERE id = :userID")
    suspend fun getPoints(userID: Int): Int?

    @Upsert
    suspend fun upsertPoints(userPoints: UserPointsEntity)
    
}
