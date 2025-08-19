package com.berlin.repository.datasource.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.berlin.repository.util.Constants.GENRE_PREFERENCES_TABLE

@Entity(tableName = GENRE_PREFERENCES_TABLE)
data class CategoriesPreferencesEntity(
    @PrimaryKey
    val categoryId: Int,
    val count: Int
)
