package com.berlin.repository.datasource.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.berlin.repository.util.Constants.USER_PROFILE_TABLE

@Entity(tableName = USER_PROFILE_TABLE)
data class UserProfileEntity(
    @PrimaryKey val id: Int,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String?,

    @ColumnInfo(name = "include_adult")
    val includeAdult: Boolean,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long
)