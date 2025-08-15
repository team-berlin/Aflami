package com.berlin.repository.datasource.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_points")
data class UserPointsEntity(
    @PrimaryKey val id: Int,
    val points: Int
)

