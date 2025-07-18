package com.berlin.repository.datasource.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories_preferences")
data class CategoriesPreferencesEntity(
    @PrimaryKey
    val categoryId: Int,
    val count: Int
)
