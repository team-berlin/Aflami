package com.berlin.repository.datasource.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.berlin.repository.util.Constants.USER_POINTS_TABLE


@Entity(tableName = USER_POINTS_TABLE)
data class UserPointsEntity(
    @PrimaryKey val id: Int,
    val points: Int
)

